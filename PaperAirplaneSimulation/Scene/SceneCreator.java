package PaperAirplaneSimulation.Scene;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.Modifiers.UICorner;
import JGamePackage.JGame.Classes.World.Image2D;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import PaperAirplaneSimulation.ExperimentData;

public class SceneCreator {
    public static void createMap(JGame game) {
        UIText header = new UIText();
        header.Text = "Header";
        header.TextScaled = true;
        header.Size = UDim2.fromScale(.3, .02);
        header.TextColor = Color.gray;
        header.Position = UDim2.fromAbsolute(10, 10);
        header.BackgroundTransparency = 1;
        header.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        header.Name = "Header";
        header.SetParent(game.UINode);

        UIText close = new UIText();
        close.Text = "x";
        close.Name = "Close";
        close.TextScaled = true;
        close.Size = UDim2.fromScale(.01, .02);
        close.TextColor = Color.gray;
        close.Position = UDim2.fromScale(.98, .005);
        close.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Center;
        close.BackgroundTransparency = 1;
        close.SetParent(game.UINode);

        close.Mouse1Down.Connect(()->{
            System.exit(0);
        });

        UIText start = new UIText();
        start.AnchorPoint = new Vector2(.5, 0);
        start.Text = "Start Sim";
        start.Name = "Start";
        start.FontSize = 30;
        start.Size = UDim2.fromScale(.1, .03);
        start.TextColor = Color.gray;
        start.Position = UDim2.fromScale(.5,  .01);
        start.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Center;
        start.SetParent(game.UINode);

        UICorner corner = new UICorner();
        corner.SetParent(start);

        start.MouseEnter.Connect(()->{
            start.BackgroundColor = start.BackgroundColor.darker();
        });

        start.MouseLeave.Connect(()->{
            start.BackgroundColor = start.BackgroundColor.brighter();
        });

        Image2D background = new Image2D();
        background.SetImage("PaperAirplaneSimulation\\Assets\\Sky.png");
        background.Size = new Vector2(800, 300).multiply(4.4);
        background.Pivot = Vector2.half;
        background.Position = game.Services.WindowService.GetScreenSize().divide(2);
        background.SetParent(game.WorldNode);

        Image2D b = new Image2D();
        b.Size = new Vector2(game.Services.WindowService.GetScreenWidth()*1.5, 100);
        b.Pivot = new Vector2(.5, 1);
        b.Position = new Vector2(game.Services.WindowService.GetScreenWidth()/2, game.Services.WindowService.GetScreenHeight()+127);
        b.Transparency = 1;
        b.SetImage("PaperAirplaneSimulation\\Assets\\Ground.png");
        game.Camera.Position = game.Services.WindowService.GetScreenSize().divide(2);
        b.SetParent(game.WorldNode);

        Image2D plane1 = new Image2D();
        plane1.Size = new Vector2(ExperimentData.meterInPixel*.3, ExperimentData.meterInPixel*.13);
        plane1.Pivot = Vector2.half;
        plane1.Position = new Vector2(0, game.Services.WindowService.GetScreenHeight() - b.Size.Y/2 - ExperimentData.ref1throwHeight * ExperimentData.meterInPixel);
        plane1.SetImage("PaperAirplaneSimulation\\Assets\\Airplane1.png");
        plane1.Transparency = 1;
        plane1.Name = "Plane1";
        plane1.SetParent(game.WorldNode);

        Image2D plane2 = new Image2D();
        plane2.Size = new Vector2(ExperimentData.meterInPixel*.3, ExperimentData.meterInPixel*.13);
        plane2.Pivot = Vector2.half;
        plane2.Position = new Vector2(0, game.Services.WindowService.GetScreenHeight() - b.Size.Y/2 - ExperimentData.ref2throwHeight * ExperimentData.meterInPixel);
        plane2.SetImage("PaperAirplaneSimulation\\Assets\\Airplane2.png");
        plane2.Transparency = 1;
        plane2.Name = "Plane2";
        plane2.SetParent(game.WorldNode);
    }
}
