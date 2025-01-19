package PaperAirplaneSimulation;

import java.awt.Color;
import java.text.DecimalFormat;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.UITextInput;
import JGamePackage.JGame.Classes.World.Box2D;
import JGamePackage.JGame.Classes.World.Image2D;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.lib.Signal.Signal.Connection;
import JGamePackage.lib.Signal.VoidSignal.VoidConnection;
import PaperAirplaneSimulation.Scene.InfoPanelCreator;
import PaperAirplaneSimulation.Scene.SceneCreator;

public class FinalProduct {
    static JGame game = new JGame();

    public static void main(String[] args) {
        
        game.Services.WindowService.SetFullscreen(true);

        game.Services.WindowService.BackgroundColor = new Color(208,244,247);

        new RealSimulation(game);
    }
}

class RealSimulation {
    JGame game;

    Image2D plane1;
    
    static double throwHeight = 1.85;
    static double wind = .3; //m/s
    static double viX = 5.38;
    static double viY = 0.948;
    static double planeWeight = 0.008;
    static double wingArea = 0.022;

    Vector2 plane1Velocity;

    Vector2 plane1RealPos;

    UIFrame panel1;

    boolean pauseSim = false;
    boolean finished = false;

    boolean plane1Finished = false;

    double secondsInAir = 0;
    double realFlyingTime = 0;

    static boolean specialCam = false;

    static DecimalFormat format3Digit = new DecimalFormat("#.##");

    private void setSettingsInputText(String name, double value) {
        game.UINode.<UIFrame>GetTypedChild("SettingsFrame").<UIFrame>GetTypedChild(name).GetChildWhichIsA(UITextInput.class).Text = Double.toString(value);
        game.UINode.<UIFrame>GetTypedChild("SettingsFrame").<UIFrame>GetTypedChild(name).GetChildWhichIsA(UITextInput.class).PlaceholderText = Double.toString(value);
    }

    private double getSettingsInputText(String name) {
        return Double.valueOf(game.UINode.<UIFrame>GetTypedChild("SettingsFrame").<UIFrame>GetTypedChild(name).GetChildWhichIsA(UITextInput.class).Text);
    }

    static double getDragCoefficient(Image2D plane, Vector2 velocity) {
        double Fd = planeWeight * 9.81 * Math.sin(plane.Rotation);
        double p = ExperimentData.airDensity;
        double A = wingArea;
        double v = MSAUtil.toMeters(velocity.magnitude());

        double Cd = (2*Fd)/(A * p * (v*v));
        return !Double.isNaN(Cd) ? Cd : 0.0001;
    }

    static double getLiftCoefficient(Image2D plane, Vector2 velocity) {
        double Fl = planeWeight * 9.81 * Math.cos(plane.Rotation);
        double p = ExperimentData.airDensity;
        double A = wingArea;
        double v = MSAUtil.toMeters(velocity.magnitude());

        double Cl = (2*Fl)/(A * p * (v*v));
        return !Double.isNaN(Cl) ? Cl : 0.0001;
    }

    static double getLift(Image2D plane, Vector2 velocity) {
        return .5 * getLiftCoefficient(plane, velocity) * ExperimentData.airDensity * wingArea * Math.pow(MSAUtil.toMeters(velocity.magnitude()), 2);
    }

    static double getDrag(Image2D plane, Vector2 velocity) {
        return .5 * getDragCoefficient(plane, velocity) * ExperimentData.airDensity * wingArea * Math.pow(MSAUtil.toMeters(velocity.magnitude()), 2);
    }

    static double getWeight(Image2D plane, double secondsInAir) { //Weight = mass * acceleration
        return planeWeight * (9.81*1.9); //for stall example, do 9.81*secondsInAir
    }

    static double newtonsToMetersPerSecond(double newtons, double t) {
        double a = newtons/planeWeight;
        double v = a*t;
        return v;
    }

    static double lookAt(Vector2 origin, Vector2 lookAt) {
        double xDiff = lookAt.X-origin.X;
        if (xDiff == 0){ //not sure if this can cause any potential errors, but avoids the arithmetic "tried to divide by zere/NaN" error
            return 0.0;
        }
        double yDiff = lookAt.Y-origin.Y;
        return Math.atan(yDiff/xDiff);
    }

    static String format(double d) {
        return format3Digit.format(d).replaceAll(",", ".");
    }


    public RealSimulation(JGame jgame) {
        game = jgame;

        SceneCreator.createMap(game);
        InfoPanelCreator.createInfoPanel(game);

        @SuppressWarnings("rawtypes")
        Connection camConnection = game.Services.TimeService.OnTick.Connect(dt-> {
            if (specialCam) {
                game.Camera.DepthFactor = .5;
                MSAUtil.positionSpecialCamera(game, plane1.Position, plane1.Position);
            } else {
                game.Camera.DepthFactor = 1.1;
                game.Camera.Position = game.Services.WindowService.GetWindowSize().divide(2);
            }
        });

        setSettingsInputText("ThrowHeight", throwHeight);
        setSettingsInputText("Wind", wind);
        setSettingsInputText("InitialXVel", viX);
        setSettingsInputText("InitialYVel", viY);

        ((UIText) game.UINode.GetChild("Header")).Text = "Paper Airplane Aerodynamics - Final Model";

        plane1 = (Image2D) game.WorldNode.GetChild("Plane1");
        ((Image2D) game.WorldNode.GetChild("Plane2")).Destroy();

        plane1.Position = new Vector2(0, game.Services.WindowService.GetWindowHeight() - game.WorldNode.<Image2D>GetTypedChild("Ground").Size.Y/2 - throwHeight * ExperimentData.meterInPixel);

        panel1 = game.UINode.<UIFrame>GetTypedChild("Panel1");
        game.UINode.<UIFrame>GetTypedChild("Panel2").Destroy();

        plane1RealPos = plane1.Position;

        ((UIImage) game.UINode.GetChild("CamButton")).Mouse1Down.Connect(()-> {
            specialCam = !specialCam;
        });

        
        for (int i = -3; i < 10; i ++) {
            Box2D b = new Box2D();
            b.FillColor = Color.gray;
            b.Size = new Vector2(2, 2000);
            b.Position = new Vector2(MSAUtil.toPixels(i), -500);
            b.ZIndex = 50;
            b.Transparency = .9;
            b.SetParent(game.WorldNode);
        }

        for (int i = -10; i < 20; i ++) {
            Box2D b = new Box2D();
            b.FillColor = Color.gray;
            b.Size = new Vector2(5000, 2);
            b.Position = new Vector2(-1000, MSAUtil.toPixels(i));
            b.ZIndex = 50;
            b.Transparency = .9;
            b.SetParent(game.WorldNode);
        }

        UIText start = (UIText) game.UINode.GetChild("Start");

        game.Camera.DepthFactor = 1.1;

        start.Mouse1Down.Wait();

        start.Text = "Pause Sim";

        VoidConnection toggleButtonCon = start.Mouse1Down.Connect(()->{
            pauseSim = !pauseSim;
            start.Text = pauseSim ? "Continue Sim" : "Pause Sim";
        });

        double startTime = game.Services.TimeService.GetElapsedSeconds();

        throwHeight = getSettingsInputText("ThrowHeight");
        viX = getSettingsInputText("InitialXVel");
        viY = getSettingsInputText("InitialYVel");
        wind = getSettingsInputText("Wind");
   
        plane1Velocity = new Vector2(MSAUtil.toPixels(viX)/11, -MSAUtil.toPixels(viY)/100);
        plane1.Position = new Vector2(0, game.Services.WindowService.GetWindowHeight() - game.WorldNode.<Image2D>GetTypedChild("Ground").Size.Y/2 - throwHeight * ExperimentData.meterInPixel);
        plane1RealPos = plane1.Position;

        @SuppressWarnings("rawtypes")
        Connection tickConnection = game.Services.TimeService.OnTick.Connect(dt->{ //runs 60 times / second
            if (pauseSim) return;
            realFlyingTime += dt;

            plane1.Position = plane1.Position.lerp(plane1RealPos, .25);

            int elapsedTicks = game.Services.TimeService.GetElapsedTicks();

            if (elapsedTicks % 1.5 != 0) return;

            secondsInAir += dt;

            if (!plane1Finished) {
                plane1.Rotation = lookAt(plane1.Position, plane1RealPos.add(plane1Velocity));

                double dragNewtons = getDrag(plane1, plane1Velocity);
                double liftNewtons = getLift(plane1, plane1Velocity);
                double weightNewtons = getWeight(plane1, secondsInAir);

                dragNewtons = Math.abs(dragNewtons);
                liftNewtons = Math.abs(liftNewtons);
                weightNewtons = Math.abs(weightNewtons);   

                double dragVelImpact = newtonsToMetersPerSecond(dragNewtons, secondsInAir) * jgame.SecondsPerTick; //make sure it's not moving in m/tick instead of m/s
                double liftVelImpact = newtonsToMetersPerSecond(liftNewtons, secondsInAir) * jgame.SecondsPerTick;
                double weightVelImpact = newtonsToMetersPerSecond(weightNewtons, secondsInAir) * jgame.SecondsPerTick;

                double dragVelPixels = MSAUtil.toPixels(dragVelImpact);
                double liftVelPixels = MSAUtil.toPixels(liftVelImpact);
                double weightVelPixels = MSAUtil.toPixels(weightVelImpact); 
                
                if (dragVelPixels > plane1Velocity.X)
                    dragVelPixels = plane1Velocity.X;

                plane1Velocity = plane1Velocity.add(-dragVelPixels, -liftVelPixels);
                plane1Velocity = plane1Velocity.add(0, weightVelPixels*1.1);
                plane1Velocity = plane1Velocity.subtract(MSAUtil.toPixels(wind) * jgame.SecondsPerTick, 0);

                plane1RealPos = plane1RealPos.add(plane1Velocity);

                if (MSAUtil.toMeters(game.Services.WindowService.GetWindowHeight() - plane1.Size.Y/2 - plane1RealPos.Y) <= 0 && !plane1Finished) {
                    plane1RealPos = new Vector2(plane1RealPos.X, game.Services.WindowService.GetWindowHeight() - 25);
                    
                    plane1Finished = true;
                    System.out.println("PLANE 1:");
                    System.out.println("TIME: " + String.valueOf(game.Services.TimeService.GetElapsedSeconds() - startTime));
                    System.out.println("DISTANCE:" + String.valueOf(MSAUtil.toMeters(plane1RealPos.X)));
                }
            }

            
            double plane1dragNewtons = getDrag(plane1, plane1Velocity);
            double plane1liftNewtons = getLift(plane1, plane1Velocity);

            plane1dragNewtons = Math.abs(plane1dragNewtons);
            plane1liftNewtons = Math.abs(plane1liftNewtons);

            double plane1height = MSAUtil.toMeters(game.Services.WindowService.GetWindowHeight() - plane1.Size.Y/2 - plane1.Position.Y);

            panel1.<UIText>GetTypedChild("Drag").Text = "Drag: "+format(plane1dragNewtons)+"N";
            panel1.<UIText>GetTypedChild("Lift").Text = "Lift: "+format(plane1liftNewtons)+"N";
            if (!plane1Finished) panel1.<UIText>GetTypedChild("Weight").Text = "Weight: "+format(getWeight(plane1, 0))+"N";
            panel1.<UIText>GetTypedChild("Height").Text = "Height: "+format(plane1height)+"m";
            panel1.<UIText>GetTypedChild("Displacement").Text = "Displacement: "+format(MSAUtil.toMeters(plane1.Position.X))+"m";
            panel1.<UIText>GetTypedChild("Angle").Text = "Angle of Attack(α): "+format(Math.toDegrees(plane1.Rotation))+"°";
            if (!plane1Finished) panel1.<UIText>GetTypedChild("Time").Text = "Time: "+format(realFlyingTime)+"s";

            Box2D track = new Box2D();
            track.Size = new Vector2(25, 5);
            track.Rotation = plane1.Rotation;
            track.FillColor = Color.WHITE;
            track.Position = plane1.Position;
            track.ZIndex = 100;
            track.Transparency = .2;
            track.SetParent(game.WorldNode);

            if (plane1Finished) finished = true;
        });

        while (!finished) game.Services.TimeService.WaitTicks(1);

        toggleButtonCon.Disconnect();

        start.Text = "Reset Sim";

        start.Mouse1Down.Wait();
        tickConnection.Disconnect();
        camConnection.Disconnect();

        setSettingsInputText("ThrowHeight", throwHeight);
        setSettingsInputText("Wind", wind);
        setSettingsInputText("InitialXVel", viX);
        setSettingsInputText("InitialYVel", viY);

        Cleanup();
    }

    public void Cleanup() {
        game.UINode.DestroyChildren();
        game.WorldNode.DestroyChildren();
        
        new RealSimulation(game);
    }
}