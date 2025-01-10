package PaperAirplaneSimulation;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.World.Box2D;
import JGamePackage.JGame.Classes.World.Image2D;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.lib.Signal.Signal.Connection;
import JGamePackage.lib.Signal.VoidSignal.VoidConnection;
import PaperAirplaneSimulation.Scene.SceneCreator;

public class SimplifiedLeifPhysik {
    static JGame game = new JGame();

    public static void main(String[] args) {
        game.Services.TimeService.WaitTicks(10);
        game.Services.WindowService.SetFullscreen(true);

        new ASimulation(game);
    }
}

class ASimulation {
    JGame game;

    Image2D plane1;
    Image2D plane2;

    Vector2 plane1Velocity = new Vector2(90, -1); //new Vector2(MSAUtil.toPixels(ExperimentData.ref1InitialVelocityX), MSAUtil.toPixels(ExperimentData.ref1InitialVelocityY)); //pixels/second
    Vector2 plane2Velocity = new Vector2(0);

    Vector2 plane1RealPos;

    boolean pauseSim = false;
    boolean finished = false;

    boolean plane1Finished = false;
    boolean plane2Finished = false;

    double secondsInAir = 0;

    static boolean specialCam = false;

    static double getDragCoefficient(Image2D plane, Vector2 velocity) {
        double Fd = ExperimentData.planeWeight * 9.81 * Math.sin(plane.Rotation);
        double p = ExperimentData.airDensity;
        double A = ExperimentData.planeWingArea;
        double v = MSAUtil.toMeters(velocity.magnitude());

        double Cd = (2*Fd)/(A * p * (v*v));
        return !Double.isNaN(Cd) ? Cd : 0.0001;
    }

    static double getLiftCoefficient(Image2D plane, Vector2 velocity) {
        double Fl = ExperimentData.planeWeight * 9.81 * Math.cos(plane.Rotation);
        double p = ExperimentData.airDensity;
        double A = ExperimentData.planeWingArea;
        double v = MSAUtil.toMeters(velocity.magnitude());

        double Cl = (2*Fl)/(A * p * (v*v));
        return !Double.isNaN(Cl) ? Cl : 0.0001;
    }

    static double getLift(Image2D plane, Vector2 velocity) {
        return .5 * getLiftCoefficient(plane, velocity) * ExperimentData.airDensity * ExperimentData.planeWingArea * Math.pow(MSAUtil.toMeters(velocity.magnitude()), 2);
    }

    static double getDrag(Image2D plane, Vector2 velocity) {
        return .5 * getDragCoefficient(plane, velocity) * ExperimentData.airDensity * ExperimentData.planeWingArea * Math.pow(MSAUtil.toMeters(velocity.magnitude()), 2);
    }

    static double getWeight(Image2D plane, double secondsInAir) { //Weight = mass * acceleration
        return ExperimentData.planeWeight * (9.81*2);
    }

    static double newtonsToMetersPerSecond(double newtons, double t) {
        double a = newtons/ExperimentData.planeWeight;
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


    public ASimulation(JGame jgame) {
        game = jgame;

        SceneCreator.createMap(game);

        ((UIText) game.UINode.GetChild("Header")).Text = "Paper Airplane Aerodynamics - Simplified Forces Model";

        plane1 = (Image2D) game.WorldNode.GetChild("Plane1");
        plane2 = (Image2D) game.WorldNode.GetChild("Plane2");

        plane1RealPos = plane1.Position;

        ((UIImage) game.UINode.GetChild("CamButton")).Mouse1Down.Connect(()-> {
            specialCam = !specialCam;
        });

        
        for (int i = 0; i < 10; i ++) {
            Box2D b = new Box2D();
            b.FillColor = Color.gray;
            b.Size = new Vector2(2, 2000);
            b.Position = new Vector2(MSAUtil.toPixels(i), -500);
            b.ZIndex = 50;
            b.Transparency = .9;
            b.SetParent(game.WorldNode);
        }

        for (int i = 0; i < 20; i ++) {
            Box2D b = new Box2D();
            b.FillColor = Color.gray;
            b.Size = new Vector2(3000, 2);
            b.Position = new Vector2(-250, MSAUtil.toPixels(i));
            b.ZIndex = 50;
            b.Transparency = .9;
            b.SetParent(game.WorldNode);
        }

        UIText start = (UIText) game.UINode.GetChild("Start");

        game.Camera.DepthFactor = 1.1;

        start.Mouse1Down.Wait();

        start.Text = "Pause Sim";

        pauseSim = true;

        game.Services.TimeService.DelaySeconds(0, ()->{
            pauseSim = false;
        });

        VoidConnection toggleButtonCon = start.Mouse1Down.Connect(()->{
            pauseSim = !pauseSim;
            start.Text = pauseSim ? "Continue Sim" : "Pause Sim";
        });

        double startTime = game.Services.TimeService.GetElapsedSeconds();

        @SuppressWarnings("rawtypes")
        Connection tickConnection = game.Services.TimeService.OnTick.Connect(dt->{ //runs 60 times / second
            if (specialCam) {
                game.Camera.DepthFactor = .5;
                MSAUtil.positionSpecialCamera(game, plane1.Position, plane2.Position);
            } else {
                game.Camera.DepthFactor = 1.1;
                game.Camera.Position = game.Services.WindowService.GetScreenSize().divide(2);
            }

            if (pauseSim) return;

            plane1.Position = plane1.Position.lerp(plane1RealPos, .25);

            int elapsedTicks = game.Services.TimeService.GetElapsedTicks();

            if (elapsedTicks % 1.5 != 0) return;

            secondsInAir += dt;

            if (!plane1Finished) {
                plane1.Rotation = lookAt(plane1.Position, plane1RealPos.add(plane1Velocity));

                double dragNewtons = getDrag(plane1, plane1Velocity);
                double liftNewtons = getLift(plane1, plane1Velocity);
                double weightNewtons = getWeight(plane1, secondsInAir);

                double dragVelImpact = newtonsToMetersPerSecond(dragNewtons, secondsInAir) * jgame.SecondsPerTick; //make sure it's not moving in m/tick instead of m/s
                double liftVelImpact = newtonsToMetersPerSecond(liftNewtons, secondsInAir) * jgame.SecondsPerTick;
                double weightVelImpact = newtonsToMetersPerSecond(weightNewtons, secondsInAir) *jgame.SecondsPerTick;

                double dragVelPixels = MSAUtil.toPixels(dragVelImpact);
                double liftVelPixels = MSAUtil.toPixels(liftVelImpact);
                double weightVelPixels = MSAUtil.toPixels(weightVelImpact);

                dragVelPixels = Math.abs(dragVelPixels);
                liftVelPixels = Math.abs(liftVelPixels);
                weightVelPixels = Math.abs(weightVelPixels);    
                
                
                if (dragVelPixels > plane1Velocity.X)
                    dragVelPixels = plane1Velocity.X;

                plane1Velocity = plane1Velocity.add(-dragVelPixels, -liftVelPixels);
                plane1Velocity = plane1Velocity.add(0, weightVelPixels*.9);
                plane1Velocity = plane1Velocity.add(MSAUtil.toPixels(ExperimentData.ref1Wind) * jgame.SecondsPerTick, 0);

                plane1RealPos = plane1RealPos.add(plane1Velocity.multiply(1));

                if (MSAUtil.toMeters(game.Services.WindowService.GetScreenHeight() - plane1.Size.Y/2 - plane1RealPos.Y) <= 0 && !plane1Finished) {
                    plane1RealPos = new Vector2(plane1RealPos.X, game.Services.WindowService.GetScreenHeight() -25);
                    plane1Finished = true;
                    System.out.println(game.Services.TimeService.GetElapsedSeconds() - startTime);
                    System.out.println(MSAUtil.toMeters(plane1RealPos.X));
                }

                if (plane1Finished) finished = true;
            }

            Box2D track = new Box2D();
            track.Size = new Vector2(25, 10);
            track.Rotation = plane1.Rotation;
            track.FillColor = Color.red;
            track.Position = plane1.Position;
            track.ZIndex = 100;
            //track.SetParent(game.WorldNode);
        });

        while (!finished) game.Services.TimeService.WaitTicks(1);

        toggleButtonCon.Disconnect();

        start.Text = "Reset Sim";

        start.Mouse1Down.Wait();
        tickConnection.Disconnect();

        Cleanup();
    }

    public void Cleanup() {
        game.UINode.DestroyChildren();
        game.WorldNode.DestroyChildren();
        
        new ASimulation(game);
    }
}