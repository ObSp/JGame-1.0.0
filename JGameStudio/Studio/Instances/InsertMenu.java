package JGameStudio.Studio.Instances;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.Modifiers.AspectRatioConstraint;
import JGamePackage.JGame.Classes.Modifiers.BorderEffect;
import JGamePackage.JGame.Classes.Modifiers.CornerEffect;
import JGamePackage.JGame.Classes.Modifiers.ListLayout;
import JGamePackage.JGame.Classes.Scripts.Script;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIButton;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIImageButton;
import JGamePackage.JGame.Classes.UI.UIScrollFrame;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.UITextButton;
import JGamePackage.JGame.Classes.UI.UITextInput;
import JGamePackage.JGame.Classes.World.Box2D;
import JGamePackage.JGame.Classes.World.Image2D;
import JGamePackage.JGame.Classes.World.Text2D;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGameStudio.StudioGlobals;
import JGameStudio.Studio.Components.Sidebar;

public class InsertMenu extends UIFrame {
    private static ArrayList<Class<? extends Instance>> creatableClasses = new ArrayList<>(Arrays.asList(new Class[] {UIButton.class, UIFrame.class, UIImage.class, UIImageButton.class, UIScrollFrame.class, UIText.class, UITextButton.class, UITextInput.class, Box2D.class, Image2D.class, Text2D.class, Script.class, AspectRatioConstraint.class, BorderEffect.class, CornerEffect.class, ListLayout.class}));

    public UITextInput filter;

    private Instance createdParent;
    
    public InsertMenu(Sidebar sidebar) {
        this.SetParent(sidebar);

        this.Position = UDim2.fromAbsolute(0, 100);
        this.Size = UDim2.fromScale(.63, 0).add(UDim2.fromAbsolute(0, 250));
        this.BackgroundColor = StudioGlobals.BackgroundColor;
        this.ClipsDescendants = false;
        this.AnchorPoint = new Vector2(.5, 0);
        this.ZIndex = 100;

        this.Visible = false;

        filter = new UITextInput();
        filter.BackgroundColor = StudioGlobals.BackgroundColor.darker();
        filter.Size = UDim2.fromScale(.95, .1);
        filter.AnchorPoint = new Vector2(.5, 0);
        filter.Position = UDim2.fromScale(.5, .02);
        filter.TextColor = StudioGlobals.TextColor;
        filter.FontSize = 17;
        filter.ClipsDescendants = false;
        filter.PlaceholderText = "Search Instances";
        filter.SetParent(this);

        SelectionBorder border = new SelectionBorder();
        border.Width = 1;
        border.BorderColor = StudioGlobals.BlueColor;

        filter.FocusChanged.Connect(focused -> {
            border.SetParent(focused ? filter : null);
        });

        UIScrollFrame list = new UIScrollFrame();
        list.Position = UDim2.fromAbsolute(0, 32).add(UDim2.fromScale(.5, 0));
        list.Size = UDim2.fromScale(1, .87);
        list.AnchorPoint = new Vector2(.5, 0);
        list.BackgroundTransparency = 1;
        list.SetParent(this);

        ListLayout listLayout = new ListLayout();
        listLayout.Padding = UDim2.zero;
        listLayout.SetParent(list);

        for (Class<? extends Instance> creatableClass : creatableClasses) {
            UIFrame frame = new UIFrame();
            frame.Size = UDim2.fromScale(1,0).add(UDim2.fromAbsolute(0, 25));
            frame.BackgroundColor = StudioGlobals.BackgroundColor;
            frame.Name = creatableClass.getSimpleName();

            frame.MouseEnter.Connect(()-> {
                frame.BackgroundColor = frame.BackgroundColor.brighter();
            });

            frame.MouseLeave.Connect(()-> {
                frame.BackgroundColor = StudioGlobals.BackgroundColor;
            });

            File imageIconFile = new File("JGameStudio\\Assets\\InstanceIcons\\"+creatableClass.getSimpleName()+".png");
            if (!imageIconFile.exists()) {
                imageIconFile = new File("JGameStudio\\Assets\\InstanceIcons\\UNKNOWN.png");
            }

            UIImage icon = new UIImage();
            icon.Size = UDim2.fromScale(0, .8);
            icon.SetImage(imageIconFile.getPath(), new Vector2(40));
            icon.AnchorPoint = new Vector2(1, .5);
            icon.Position = UDim2.fromScale(.17, .5);
            icon.BackgroundTransparency = 1;
            icon.SetParent(frame);

            new AspectRatioConstraint().SetParent(icon);

            UIText name = new UIText();
            name.Size = UDim2.fromScale(.7, 1);
            name.Position = UDim2.fromScale(.2, 0);
            name.BackgroundTransparency = 1;
            name.Text = creatableClass.getSimpleName();
            name.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
            name.FontSize = 17;
            name.CustomFont = StudioGlobals.GlobalFont;
            name.TextColor = StudioGlobals.TextColor;
            name.MouseTargetable = false;
            name.SetParent(frame);
            
            frame.Mouse1Down.Connect(()-> {
                Instance created;
                try {
                    created = creatableClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                created.SetParent(createdParent);
                this.Visible = false;
            });

            frame.SetParent(list);
        }

        filter.TextUpdated.Connect(()-> {
            String curText = filter.Text.toLowerCase();

            for (UIBase v : list.GetChildrenOfClass(UIBase.class)) {
                v.Visible = v.Name.toLowerCase().contains(curText);
            }
        });
        

        StudioGlobals.GlobalBorder.SetParent(this);
    }

    public void OpenAtPosWithInstance(UDim2 pos, Instance parent) {
        this.Position = pos.subtract(UDim2.fromAbsolute(0, this.<Sidebar>GetParent().GetAbsolutePosition().Y));
        this.createdParent = parent;
        game.InputService.SetFocusedUITextInput(this.GetChildOfClass(UITextInput.class));
        this.Visible = true;
    }
}
