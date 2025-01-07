package PaperAirplaneSimulation;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.World.Box2D;
import JGamePackage.JGame.Classes.World.Image2D;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class FinalProduct {
    static JGame game = new JGame();

    static Image2D plane;

    static double velocity = 150;

    static double drag = 0.0;


    public static void main(String[] args) {
        game.Services.WindowService.BackgroundColor = new Color(76, 201, 254);

        System.out.println(game.Services.WindowService.GetScreenWidth());

        Box2D b = new Box2D();
        b.Size = new Vector2(game.Services.WindowService.GetScreenWidth(), 100);
        b.Pivot = new Vector2(0, 1);
        b.Position = new Vector2(0, game.Services.WindowService.GetScreenHeight()+50);
        b.FillColor = new Color(88,234,66);
        game.Camera.Position = game.Services.WindowService.GetScreenSize().divide(2);
        b.SetParent(game.WorldNode);

        plane = new Image2D();
        plane.Size = new Vector2(30.4, 15);
        plane.Position = new Vector2(game.Services.WindowService.GetScreenWidth()-100, game.Services.WindowService.GetScreenHeight()-b.Size.Y-465.8);
        plane.Rotation = Math.toDegrees(45);
        plane.SetImage("PaperAirplaneSimulation\\Assets\\Airplane.png");
        plane.Transparency = 1;
        plane.SetParent(game.WorldNode);

        game.Services.TimeService.WaitSeconds(5);

        game.Services.TimeService.OnTick.Connect(dt->{
            plane.Position =  plane.Position.subtract(new Vector2(velocity, 0));
            drag = calcDrag();
            velocity -= drag;
            System.out.println(velocity+", "+drag);
        });
    }

    static double calcDrag() {
        return .5*ExperimentData.airDensity*velocity*ExperimentData.referenceArea*ExperimentData.coefficient;
    }
}
