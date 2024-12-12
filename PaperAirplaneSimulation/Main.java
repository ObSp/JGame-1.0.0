package PaperAirplaneSimulation;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.World.Box2D;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class Main {
    static JGame game = new JGame();

    static Box2D plane;

    static double c = 0;
    public static void main(String[] args) {
        game.Services.WindowService.BackgroundColor = new Color(76, 201, 254);

        Box2D b = new Box2D();
        b.Size = new Vector2(game.Services.WindowService.GetScreenWidth(), 100);
        b.Pivot = new Vector2(0, 1);
        b.Position = new Vector2(0, game.Services.WindowService.GetScreenHeight()+50);
        b.FillColor = new Color(88,234,66);
        game.Camera.Position = game.Services.WindowService.GetScreenSize().divide(2);
        b.SetParent(game.WorldNode);

        plane = new Box2D();
        plane.Size = new Vector2(30, 5);
        plane.Position = new Vector2(game.Services.WindowService.GetScreenWidth()-100, 150);
        plane.Rotation = Math.toDegrees(45);
        plane.SetParent(game.WorldNode);

        game.Services.TimeService.OnTick.Connect(dt->calculateValues());
    }

    private static void calculateValues() {
        c = plane.Rotation;
    }
}
