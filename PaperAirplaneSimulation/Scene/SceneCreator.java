package PaperAirplaneSimulation.Scene;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Modifiers.AspectRatioConstraint;
import JGamePackage.JGame.Classes.Modifiers.CornerEffect;
import JGamePackage.JGame.Classes.Modifiers.ListLayout;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.UITextInput;
import JGamePackage.JGame.Classes.World.Box2D;
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

        UIImage cam = new UIImage();
        cam.Size = UDim2.fromScale(.012, .02);
        cam.Position = UDim2.fromScale(.96, .007);
        cam.BackgroundTransparency = 1;
        cam.SetImage("PaperAirplaneSimulation\\Assets\\Camera.png");
        cam.Name = "CamButton";
        cam.SetParent(game.UINode);

        new AspectRatioConstraint().SetParent(cam);

        UIImage settings = cam.Clone();
        settings.SetImage("PaperAirplaneSimulation\\Assets\\Settings.png");
        settings.Position = settings.Position.subtract(UDim2.fromScale(.02, 0));
        settings.Name = "Settings";
        settings.SetParent(game.UINode);

        AspectRatioConstraint camConstraint = new AspectRatioConstraint();
        camConstraint.SetParent(cam);

        UIFrame settingsFrame = new UIFrame();
        settingsFrame.Size = UDim2.fromScale(.3, .6);
        settingsFrame.BackgroundColor = Color.black;
        settingsFrame.BackgroundTransparency = .5;
        settingsFrame.AnchorPoint = Vector2.half;
        settingsFrame.Position = UDim2.fromScale(.5, .5);
        settingsFrame.Name = "SettingsFrame";
        settingsFrame.SetParent(game.UINode);

        ListLayout layout =  new ListLayout();
        layout.Padding = UDim2.fromScale(0, .02);
        layout.SetParent(settingsFrame);

        UIText settingsHeader = new UIText();
        settingsHeader.Text = "Settings";
        settingsHeader.Size = UDim2.fromScale(1, .05);
        settingsHeader.TextScaled = true;
        settingsHeader.BackgroundTransparency = 1;
        settingsHeader.TextColor = Color.white;
        settingsHeader.SetParent(settingsFrame);

        UIFrame throwHeight = new UIFrame();
        throwHeight.Size = UDim2.fromScale(1, .05);
        throwHeight.Position = UDim2.fromScale(.5, .1);
        throwHeight.AnchorPoint = new Vector2(.5, 0);
        throwHeight.Name = "ThrowHeight";
        throwHeight.BackgroundTransparency = 1;
        
        throwHeight.SetParent(settingsFrame);

        UIText throwHeightLabel = new UIText();
        throwHeightLabel.Text = "Throw Height(m)";
        throwHeightLabel.Size = UDim2.fromScale(.5, .7);
        throwHeightLabel.TextScaled = true;
        throwHeightLabel.BackgroundTransparency = 1;
        throwHeightLabel.TextColor = Color.white;
        throwHeightLabel.SetParent(throwHeight);

        UITextInput throwHeightInput = new UITextInput();
        throwHeightInput.Position = UDim2.fromScale(.5,0);
        throwHeightInput.FontSize = 25;
        throwHeightInput.Size = UDim2.fromScale(.3, 1);
        throwHeightInput.SetParent(throwHeight);

        CornerEffect throwHeightCorner = new CornerEffect();
        throwHeightCorner.RelativeTo = Constants.Vector2Axis.Y;
        throwHeightCorner.Radius = .4;
        throwHeightCorner.SetParent(throwHeightInput);

        UIFrame initialXVel = throwHeight.Clone();
        initialXVel.GetChildOfClass(UIText.class).Text = "Initial X Velocity (m/s)";
        initialXVel.Name = "InitialXVel";
        initialXVel.SetParent(settingsFrame);

        UIFrame initialYVel = throwHeight.Clone();
        initialYVel.GetChildOfClass(UIText.class).Text = "Initial Y Velocity (m/s)";
        initialYVel.Name = "InitialYVel";
        initialYVel.SetParent(settingsFrame);

        UIFrame wind = throwHeight.Clone();
        wind.GetChildOfClass(UIText.class).Text = "Wind (m/s)";
        wind.Name = "Wind";
        wind.SetParent(settingsFrame);

        settingsFrame.Visible = false;

        settings.Mouse1Down.Connect(()->{
            settingsFrame.Visible = !settingsFrame.Visible;
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

        CornerEffect corner = new CornerEffect();
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
        background.Position = game.Services.WindowService.GetWindowSize().divide(2);
        background.SetParent(game.WorldNode);

        Image2D b = new Image2D();
        b.Size = new Vector2(game.Services.WindowService.GetWindowWidth()*1.5, 100);
        b.Pivot = new Vector2(.5, 1);
        b.Position = new Vector2(game.Services.WindowService.GetWindowWidth()/2, game.Services.WindowService.GetWindowHeight()+b.Size.Y/2 -15);
        b.Transparency = 1;
        b.SetImage("PaperAirplaneSimulation\\Assets\\Ground.png");
        b.Name = "Ground";
        b.SetParent(game.WorldNode);

        Box2D belowB = new Box2D();
        belowB.Size = b.Size.add(0, 500);
        belowB.Pivot = new Vector2(.5, 0);
        belowB.FillColor = new Color(35,191,53);
        belowB.Position = b.Position.subtract(0, 5);
        belowB.SetParent(game.WorldNode);

        Image2D plane1 = new Image2D();
        plane1.Size = new Vector2(ExperimentData.meterInPixel*.3, ExperimentData.meterInPixel*.13);
        plane1.Pivot = Vector2.half;
        plane1.Position = new Vector2(0, game.Services.WindowService.GetWindowHeight() - b.Size.Y/2 - ExperimentData.ref1throwHeight * ExperimentData.meterInPixel);
        plane1.SetImage("PaperAirplaneSimulation\\Assets\\Airplane1.png");
        plane1.Transparency = 1;
        plane1.Name = "Plane1";
        plane1.SetParent(game.WorldNode);

        Image2D plane2 = new Image2D();
        plane2.Size = new Vector2(ExperimentData.meterInPixel*.3, ExperimentData.meterInPixel*.13);
        plane2.Pivot = Vector2.half;
        plane2.Position = new Vector2(0, game.Services.WindowService.GetWindowHeight() - b.Size.Y/2 - ExperimentData.ref2throwHeight * ExperimentData.meterInPixel);
        plane2.SetImage("PaperAirplaneSimulation\\Assets\\Airplane2.png");
        plane2.Transparency = 1;
        plane2.Name = "Plane2";
        plane2.SetParent(game.WorldNode);

        game.Camera.Position = game.Services.WindowService.GetWindowSize().divide(2);
    }
}
