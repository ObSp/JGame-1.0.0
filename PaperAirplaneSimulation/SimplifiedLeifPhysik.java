package PaperAirplaneSimulation;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;
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

    static double getDrag(Image2D plane) {
        return ExperimentData.planeWeight * 9.81 * Math.sin(plane.Rotation);
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