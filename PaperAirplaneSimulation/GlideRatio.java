package PaperAirplaneSimulation;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.World.Image2D;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.lib.Signal.Signal.Connection;
import JGamePackage.lib.Signal.VoidSignal.VoidConnection;
import PaperAirplaneSimulation.Scene.SceneCreator;

public class GlideRatio {
    static JGame game = new JGame();

    static Image2D plane1;
    static Image2D plane2;

    static Vector2 velocity = new Vector2(0);

    static double drag = 0.0;

    static boolean pauseSim = false;
    static boolean finished = false;

    static double plane1RatioEquation(double xMeters) {
        return -(1/ExperimentData.ref1GlideDistancePerHeightLost) * xMeters + ExperimentData.ref1throwHeight;
    }

    public static void main(String[] args) {
        game.Services.TimeService.WaitTicks(10);
        game.Services.WindowService.SetFullscreen(true);

        SceneCreator.createMap(game);

        ((UIText) game.UINode.GetChild("Header")).Text = "Paper Airplane Aerodynamics - Simplified Model";

        plane1 = (Image2D) game.WorldNode.GetChild("Plane1");
        plane2 = (Image2D) game.WorldNode.GetChild("Plane1");

        UIText start = (UIText) game.UINode.GetChild("Start");

        game.Camera.DepthFactor = 1.1;

        start.Mouse1Down.Wait();

        start.Text = "Pause Sim";
        VoidConnection toggleButtonCon = start.Mouse1Down.Connect(()->{
            pauseSim = !pauseSim;
            start.Text = pauseSim ? "Continue Sim" : "Pause Sim";
        });

        @SuppressWarnings("rawtypes")
        Connection tickConnection = game.Services.TimeService.OnTick.Connect(dt->{
            if (pauseSim) return;

            //PLANE1
            if (plane1RatioEquation(MSAUtil.toMeters(plane1.Position.X)) > 0) {
                double plane1CurX = MSAUtil.toMeters(plane1.Position.X);
                plane1CurX += .05;
                double plane1Y = plane1RatioEquation(plane1CurX);
                plane1.Position = new Vector2(MSAUtil.toPixels(plane1CurX), game.Services.WindowService.GetScreenHeight() - plane1.Size.Y/2 -MSAUtil.toPixels(plane1Y));
            }
        });

        while (!finished) game.Services.TimeService.WaitTicks(1);

        toggleButtonCon.Disconnect();
        tickConnection.Disconnect();

        start.Text = "Reset Sim";
    }
}
