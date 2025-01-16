package PaperAirplaneSimulation;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.World.Image2D;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.lib.Signal.Signal.Connection;
import JGamePackage.lib.Signal.VoidSignal.VoidConnection;
import PaperAirplaneSimulation.Scene.SceneCreator;

public class GlideRatio {
    static JGame game = new JGame();

    public static void main(String[] args) {
        game.Services.TimeService.WaitTicks(10);
        game.Services.WindowService.SetFullscreen(true);

        new Simulation(game);
    }
}

class Simulation {
    JGame game;

    Image2D plane1;
    Image2D plane2;

    Vector2 velocity = new Vector2(0);

    double drag = 0.0;

    boolean pauseSim = false;
    boolean finished = false;

    boolean plane1Finished = false;
    boolean plane2Finished = false;

    static boolean specialCam = false;

    double xStep = .1;

    static double plane1RatioEquation(double xMeters) {
        return -(1/ExperimentData.ref1GlideDistancePerHeightLost) * xMeters + ExperimentData.ref1throwHeight;
    }

    static double plane2RatioEquation(double xMeters) {
        return -(1/ExperimentData.ref2GlideDistancePerHeightLost) * xMeters + ExperimentData.ref2throwHeight;
    }

    public Simulation(JGame jgame) {
        game = jgame;

        SceneCreator.createMap(game);

        ((UIText) game.UINode.GetChild("Header")).Text = "Paper Airplane Aerodynamics - Glide Ratio Model";

        plane1 = (Image2D) game.WorldNode.GetChild("Plane1");
        plane2 = (Image2D) game.WorldNode.GetChild("Plane2");

        ((UIImage) game.UINode.GetChild("CamButton")).Mouse1Down.Connect(()-> {
            specialCam = !specialCam;
        });

        UIText start = (UIText) game.UINode.GetChild("Start");

        game.Camera.DepthFactor = 1.1;

        start.Mouse1Down.Wait();

        start.Text = "Pause Sim";

        pauseSim = true;

        game.Services.TimeService.DelaySeconds(3, ()->{
            pauseSim = false;
        });

        VoidConnection toggleButtonCon = start.Mouse1Down.Connect(()->{
            pauseSim = !pauseSim;
            start.Text = pauseSim ? "Continue Sim" : "Pause Sim";
        });

        @SuppressWarnings("rawtypes")
        Connection tickConnection = game.Services.TimeService.OnTick.Connect(dt->{
            if (specialCam) {
                game.Camera.DepthFactor = 0.5;
                MSAUtil.positionSpecialCamera(game, plane1.Position, plane2.Position);
            } else {
                game.Camera.DepthFactor = 1.1;
                game.Camera.Position = game.Services.WindowService.GetWindowSize().divide(2);
            }

            if (plane1Finished && plane2Finished) {
                finished = true;
                return;
            }

            if (pauseSim) return;

            //PLANE1
            if (!plane1Finished) {
                double plane1CurX = MSAUtil.toMeters(plane1.Position.X);
                plane1CurX += xStep * 1.1 * (1 - ExperimentData.ref1Wind);
                double plane1Y = plane1RatioEquation(plane1CurX);
                plane1.Position = new Vector2(MSAUtil.toPixels(plane1CurX), game.Services.WindowService.GetWindowHeight() - plane1.Size.Y/2 -MSAUtil.toPixels(plane1Y));
                plane1.Rotation = Math.atan(ExperimentData.ref1throwHeight/ExperimentData.ref1DistanceX);
                if (plane1Y <= 0) plane1Finished = true;
            }

            if (!plane2Finished) {
                double plane2CurX = MSAUtil.toMeters(plane2.Position.X);
                plane2CurX += xStep  * 1.1 * (1 - ExperimentData.ref2Wind) ;
                double plane2Y = plane2RatioEquation(plane2CurX);
                plane2.Position = new Vector2(MSAUtil.toPixels(plane2CurX), game.Services.WindowService.GetWindowHeight() - plane2.Size.Y/2 -MSAUtil.toPixels(plane2Y));
                plane2.Rotation = Math.atan(ExperimentData.ref2throwHeight/ExperimentData.ref2DistanceX);
                if (plane2Y <= 0) plane2Finished = true;
            }
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
        
        new Simulation(game);
    }
}