package JGameStudio.Studio.Components;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JColorChooser;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.Modifiers.AspectRatioConstraint;
import JGamePackage.JGame.Classes.Modifiers.ListLayout;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIButton;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.World.WorldBase;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGameStudio.StudioGlobals;
import JGameStudio.Studio.Modules.ColorManager;
import JGameStudio.Studio.Modules.CreationHandler;
import JGameStudio.Studio.Modules.Selection;

public class Topbar extends UIFrame {

    public UIButton dragButton;
    public UIButton moveButton;
    public UIButton scaleButton;

    public UIButton saveFile;
    public UIButton openFile;
    public UIButton saveFileAs;

    public UIButton createInstance;
    public UIButton setColor;

    private void createCreationFrame() {
        UIFrame container = new UIFrame();
        container.Size = UDim2.fromAbsolute(140, 70);
        container.AnchorPoint = new Vector2(0, .5);
        container.Position = UDim2.fromAbsolute(355, 0).add(UDim2.fromScale(0, .45));
        container.BackgroundColor = StudioGlobals.BackgroundColor;
        container.SetParent(this);

        ListLayout list = new ListLayout();
        list.Padding = UDim2.zero;
        list.ItemAlignment = Constants.ListAlignment.Horizontal;
        list.SetParent(container);

        UIText createText = new UIText();
        createText.BackgroundTransparency = 1;
        createText.Text = "Create WorldBase";
        createText.CustomFont = StudioGlobals.GlobalFont;
        createText.Size = UDim2.fromAbsolute(container.Size.X.Absolute, 10);
        createText.TextColor = StudioGlobals.TextColor;
        createText.Position = UDim2.fromScale(0, .85).add(UDim2.fromAbsolute(container.Position.X.Absolute, 0));
        createText.FontSize = 13;
        createText.SetParent(this);

        StudioGlobals.GlobalBorder.Clone().SetParent(container);

        createInstance = new UIButton();
        createInstance.Size = UDim2.fromAbsolute(70, 0).add(UDim2.fromScale(0, 1));
        createInstance.HoverColor = StudioGlobals.BackgroundColor.brighter();
        createInstance.BackgroundColor = StudioGlobals.BackgroundColor;
        createInstance.SetParent(container);

        UIImage createImage = new UIImage();
        createImage.SetImage("JGameStudio\\Assets\\InstanceIcons\\Box2D.png");
        createImage.Size = UDim2.fromScale(.7, .5);
        createImage.AnchorPoint = new Vector2(.5, 0);
        createImage.Position = UDim2.fromScale(.5, .1);
        createImage.BackgroundTransparency = 1;
        createImage.MouseTargetable = false;
        createImage.SetParent(createInstance);

        AspectRatioConstraint aspect = new AspectRatioConstraint();
        aspect.DominantAxis = Constants.Vector2Axis.X;
        aspect.SetParent(createImage);

        UIImage plus = createImage.Clone();
        plus.SetImage("JGameStudio\\Assets\\Icons\\Plus.png");
        plus.Size = UDim2.fromScale(.5, .5);
        plus.AnchorPoint = new Vector2(.5, .5);
        plus.Position = UDim2.fromScale(1,.95);
        plus.SetParent(createImage);

        UIText btnText = new UIText();
        btnText.BackgroundTransparency = 1;
        btnText.Text = "Create";
        btnText.CustomFont = StudioGlobals.GlobalFont;
        btnText.Size = UDim2.fromScale(1, .1);
        btnText.TextColor = StudioGlobals.TextColor;
        btnText.Position = UDim2.fromScale(0, .8);
        btnText.FontSize = 13;
        btnText.MouseTargetable = false;
        btnText.SetParent(createInstance);

        setColor = createInstance.Clone();
        setColor.SetParent(container);

        setColor.GetChildOfClass(UIImage.class).Destroy();

        UIFrame colorFrame = new UIFrame();
        colorFrame.Size = UDim2.fromScale(.53, .5);
        colorFrame.AnchorPoint = new Vector2(.5, 0);
        colorFrame.Position = UDim2.fromScale(.5, .17);
        colorFrame.BackgroundColor = ColorManager.getColor();
        colorFrame.MouseTargetable = false;
        colorFrame.SetParent(setColor);

        AspectRatioConstraint colorAspect = new AspectRatioConstraint();
        colorAspect.DominantAxis = Constants.Vector2Axis.X;
        colorAspect.SetParent(colorFrame);

        setColor.GetChildOfClass(UIText.class).Text = "Color";

        setColor.Mouse1Down.Connect(()->{
            Color chosen = JColorChooser.showDialog(game.GetWindow(), "Pick Color", colorFrame.BackgroundColor);
            if (chosen == null) return;
            ColorManager.setColor(chosen);
        });

        setColor.Mouse2Down.Connect(()->{
            Color c = ColorManager.getColor();
            for (Instance v : Selection.get()) {
                if (v instanceof WorldBase) {
                    ((WorldBase) v).FillColor = c;
                } else if (v instanceof UIBase) {
                    ((UIBase) v).BackgroundColor = c;
                }
            }
        });

        createInstance.Mouse1Down.Connect(()-> {
            CreationHandler.createInstance();
        });

        ColorManager.ColorChanged.Connect(newColor ->{
            colorFrame.BackgroundColor = newColor;
        });
    }

    private void createFileOptionsFrame() {
        UIFrame container = new UIFrame();
        container.Size = UDim2.fromAbsolute(90, 69);
        container.AnchorPoint = new Vector2(0, .5);
        container.Position = UDim2.fromAbsolute(20, 0).add(UDim2.fromScale(0, .45));
        container.BackgroundColor = StudioGlobals.BackgroundColor;
        container.SetParent(this);

        UIText optText = new UIText();
        optText.BackgroundTransparency = 1;
        optText.Text = "File";
        optText.CustomFont = StudioGlobals.GlobalFont;
        optText.Size = UDim2.fromAbsolute(container.Size.X.Absolute, 10);
        optText.TextColor = StudioGlobals.TextColor;
        optText.Position = UDim2.fromScale(0, .85).add(UDim2.fromAbsolute(container.Position.X.Absolute, 0));
        optText.FontSize = 13;
        optText.SetParent(this);

        StudioGlobals.GlobalBorder.Clone().SetParent(container);

        ListLayout list = new ListLayout();
        list.Padding = UDim2.zero;
        list.SetParent(container);

        saveFile = new UIButton();
        saveFile.Size = UDim2.fromScale(1, 0).add(UDim2.fromAbsolute(0, 23));
        saveFile.BackgroundColor = StudioGlobals.BackgroundColor;
        saveFile.HoverColor = StudioGlobals.BackgroundColor.brighter();
        saveFile.SetParent(container);

        UIImage saveFileImg = new UIImage();
        saveFileImg.AnchorPoint = new Vector2(0, .5);
        saveFileImg.Position = UDim2.fromScale(0, .5).add(UDim2.fromAbsolute(5, 0));
        saveFileImg.Size = UDim2.fromScale(0, .8);
        saveFileImg.BackgroundTransparency = 1;
        saveFileImg.MouseTargetable = false;
        saveFileImg.SetImage("JGameStudio\\Assets\\Icons\\Save.png", new Vector2(32));
        saveFileImg.SetParent(saveFile);

        new AspectRatioConstraint().SetParent(saveFileImg);

        UIText saveFileText = new UIText();
        saveFileText.BackgroundTransparency = 1;
        saveFileText.Text = "Save";
        saveFileText.CustomFont = StudioGlobals.GlobalFont;
        saveFileText.Size = UDim2.fromScale(.7, 1);
        saveFileText.TextColor = StudioGlobals.TextColor;
        saveFileText.Position = UDim2.fromAbsolute(30, 0);
        saveFileText.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        saveFileText.FontSize = 13;
        saveFileText.MouseTargetable = false;
        saveFileText.SetParent(saveFile);

        saveFileAs = saveFile.Clone();
        saveFileAs.GetChildOfClass(UIText.class).Text = "Save As";
        saveFileAs.GetChildOfClass(UIImage.class).SetImage("JGameStudio\\Assets\\Icons\\SaveAs.png");
        saveFileAs.SetParent(container);

        openFile = saveFileAs.Clone();
        openFile.GetChildOfClass(UIText.class).Text = "Open";
        openFile.GetChildOfClass(UIImage.class).SetImage("JGameStudio\\Assets\\Icons\\Open.png");
        openFile.SetParent(container);
    }

    private void createModeFrame() {
        UIFrame container = new UIFrame();
        container.Size = UDim2.fromAbsolute(180, 70);
        container.AnchorPoint = new Vector2(0, .5);
        container.Position = UDim2.fromAbsolute(140, 0).add(UDim2.fromScale(0, .45));
        container.BackgroundColor = StudioGlobals.BackgroundColor;
        container.SetParent(this);

        UIText modeText = new UIText();
        modeText.BackgroundTransparency = 1;
        modeText.Text = "Selection Modes";
        modeText.CustomFont = StudioGlobals.GlobalFont;
        modeText.Size = UDim2.fromAbsolute(container.Size.X.Absolute, 10);
        modeText.TextColor = StudioGlobals.TextColor;
        modeText.Position = UDim2.fromScale(0, .85).add(UDim2.fromAbsolute(container.Position.X.Absolute, 0));
        modeText.FontSize = 13;
        modeText.SetParent(this);

        StudioGlobals.GlobalBorder.Clone().SetParent(container);

        ListLayout layout = new ListLayout();
        layout.ItemAlignment = Constants.ListAlignment.Horizontal;
        layout.Padding = UDim2.zero;
        layout.SetParent(container);

        dragButton = new UIButton();
        dragButton.Size = UDim2.fromAbsolute(60, 0).add(UDim2.fromScale(0, 1));
        dragButton.HoverColor = StudioGlobals.BackgroundColor.brighter();
        dragButton.BackgroundColor = StudioGlobals.BackgroundColor;
        dragButton.SetParent(container);

        UIImage dragImage = new UIImage();
        dragImage.SetImage("JGameStudio\\Assets\\Icons\\Hand.png");
        dragImage.Size = UDim2.fromScale(.7, .5);
        dragImage.AnchorPoint = new Vector2(.5, 0);
        dragImage.Position = UDim2.fromScale(.5, .1);
        dragImage.BackgroundTransparency = 1;
        dragImage.MouseTargetable = false;
        dragImage.SetParent(dragButton);

        AspectRatioConstraint aspect = new AspectRatioConstraint();
        aspect.DominantAxis = Constants.Vector2Axis.X;
        aspect.SetParent(dragImage);

        UIText dragText = new UIText();
        dragText.BackgroundTransparency = 1;
        dragText.Text = "Drag";
        dragText.CustomFont = StudioGlobals.GlobalFont;
        dragText.Size = UDim2.fromScale(1, .1);
        dragText.TextColor = StudioGlobals.TextColor;
        dragText.Position = UDim2.fromScale(0, .75);
        dragText.FontSize = 13;
        dragText.MouseTargetable = false;
        dragText.SetParent(dragButton);

        dragButton.Mouse1Down.Connect(()-> {
            StudioGlobals.ModeHandler.setMode(StudioGlobals.ModeHandler.dragMode);
        });

        moveButton = dragButton.Clone();
        moveButton.GetChildOfClass(UIText.class).Text = "Move";
        moveButton.GetChildOfClass(UIImage.class).SetImage("JGameStudio\\Assets\\Icons\\Move.png");
        moveButton.SetParent(container);

        moveButton.Mouse1Down.Connect(()-> {
            StudioGlobals.ModeHandler.setMode(StudioGlobals.ModeHandler.moveMode);
        });

        scaleButton = moveButton.Clone();
        scaleButton.GetChildOfClass(UIText.class).Text = "Scale";
        scaleButton.GetChildOfClass(UIImage.class).SetImage("JGameStudio\\Assets\\Icons\\Scale.png");
        scaleButton.SetParent(container);

        scaleButton.Mouse1Down.Connect(()-> {
            StudioGlobals.ModeHandler.setMode(StudioGlobals.ModeHandler.scaleMode);
        });

        HashMap<String, UIButton> buttons = new HashMap<>();
        buttons.put("Drag", dragButton);
        buttons.put("Move", moveButton);
        buttons.put("Scale", scaleButton);

        StudioGlobals.ModeHandler.ModeSelected.Connect((oldMode, mode) -> {
            if (oldMode != null) {
                buttons.get(oldMode.getClass().getSimpleName()).BackgroundColor = StudioGlobals.BackgroundColor;
            }

            buttons.get(mode.getClass().getSimpleName()).BackgroundColor = StudioGlobals.BackgroundColor.darker();
        });

        StudioGlobals.ModeHandler.setMode(StudioGlobals.ModeHandler.dragMode);
    }
    
    public Topbar() {
        this.Size = UDim2.fromScale(1, 0).add(UDim2.fromAbsolute(0, 105));
        this.BackgroundColor = StudioGlobals.BackgroundColor;
        this.AnchorPoint = new Vector2(0, 0);
        this.ZIndex = 2;

        StudioGlobals.GlobalBorder.Clone().SetParent(this);

        createFileOptionsFrame();
        createModeFrame();
        createCreationFrame();
    }
}
