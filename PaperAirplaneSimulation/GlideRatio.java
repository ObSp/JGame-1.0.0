package PaperAirplaneSimulation;

import java.awt.Color;

import JGamePackage.JGame.JGame;
<<<<<<< HEAD
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.World.Box2D;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
=======
import JGamePackage.JGame.Classes.World.Box2D;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.lib.Signal.Signal.Connection;
>>>>>>> 3c5f3e8201f520f8b89fe48e4289b050c49e1954

public class GlideRatio {
    static JGame game = new JGame();

    static Box2D plane;

<<<<<<< HEAD
    static Vector2 velocity = new Vector2(0);

    static double drag = 0.0;

    public static void main(String[] args) {
        UIText header = new UIText();
        header.Text = "Paper Airplane Aerodynamics - Simplified Model";
        header.TextScaled = true;
        header.Size = UDim2.fromScale(1, .02);
        header.TextColor = Color.white;
        header.BackgroundTransparency = 1;
        header.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        header.SetParent(game.UINode);

        game.Services.WindowService.BackgroundColor = new Color(76, 201, 254);

        System.out.println(game.Services.WindowService.GetScreenWidth());

=======
    static double velocity = 0.0;
    static double wind = 0.5; //m/s

    @SuppressWarnings("rawtypes")
    static Connection tickConnection;

    public static void main(String[] args) {
        game.Services.WindowService.BackgroundColor = new Color(76, 201, 254);

>>>>>>> 3c5f3e8201f520f8b89fe48e4289b050c49e1954
        Box2D b = new Box2D();
        b.Size = new Vector2(game.Services.WindowService.GetScreenWidth(), 100);
        b.Pivot = new Vector2(0, 1);
        b.Position = new Vector2(0, game.Services.WindowService.GetScreenHeight()+50);
        b.FillColor = new Color(88,234,66);
        game.Camera.Position = game.Services.WindowService.GetScreenSize().divide(2);
        b.SetParent(game.WorldNode);

        plane = new Box2D();
        plane.Size = new Vector2(30.4, 5);
<<<<<<< HEAD
        plane.Position = new Vector2(game.Services.WindowService.GetScreenWidth()-100, game.Services.WindowService.GetScreenHeight()-b.Size.Y-465.8);
        plane.Rotation = Math.toDegrees(45);
        plane.SetParent(game.WorldNode);

        game.Services.TimeService.WaitSeconds(5);

        
=======
        plane.Pivot = Vector2.one;
        plane.Position = new Vector2(0, game.Services.WindowService.GetScreenHeight() - b.Size.Y - ExperimentData.throwHeight * ExperimentData.meterInPixel);
        plane.Rotation = Math.toDegrees(45);
        plane.SetParent(game.WorldNode);

        tickConnection = game.Services.TimeService.OnTick.Connect(dt->{
            if (plane.Position.Y >= b.Position.Y - b.Size.Y) {
                tickConnection.Disconnect();
                System.out.println(plane.Position.X/ExperimentData.meterInPixel);
                return;
            }

            double xInMeters = plane.Position.X/ExperimentData.meterInPixel; //m
            xInMeters += .05;
            
            double yInMeters = -0.04*(xInMeters*xInMeters) + ExperimentData.throwHeight;

            double yInPixels = yInMeters * ExperimentData.meterInPixel;
            yInPixels = b.Position.Y - b.Size.Y - yInPixels;

            double xInPixels = xInMeters * ExperimentData.meterInPixel;

            plane.Position = new Vector2(xInPixels, yInPixels);
            System.out.println(plane.Position);
        });
>>>>>>> 3c5f3e8201f520f8b89fe48e4289b050c49e1954
    }
}
