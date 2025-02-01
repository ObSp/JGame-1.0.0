package JGameStudio.Studio.Components;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.UITextInput;
import JGamePackage.JGame.Classes.UI.Modifiers.UIAspectRatioConstraint;
import JGamePackage.JGame.Classes.UI.Modifiers.UIListLayout;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGameStudio.StudioGlobals;

public class Explorer extends UIFrame {

    public UITextInput filter;
    public UIFrame listFrame;

    private Set<String> ignoredClasses = Set.of("Sidebar", "Topbar", "DisplayWindow");

    private ArrayList<Instance> trackedInstances = new ArrayList<>();

    public Explorer() {
        this.Size = UDim2.fromScale(1, .55);
        this.BackgroundTransparency = 1;
        createHeader();
        createList();

        this.filter.TextUpdated.Connect(()-> {
            for (UIBase c : this.listFrame.GetDescendantsOfClass(UIBase.class)) {
                if (c.GetCProp("ABSContainer") == null) continue;
                if ((c.Name.toLowerCase().contains(filter.Text.toLowerCase())) || c.GetDescendant(filter.Text) != null) {
                    c.Visible = true;
                } else {
                    c.Visible = false;
                }
            }
        });

        this.AddInstance(game.WorldNode);
        this.AddInstance(game.UINode);
        this.AddInstance(game.StorageNode);
    }

    private UIFrame createInstanceFrame(Instance obj) {
        if (ignoredClasses.contains(obj.getClass().getSimpleName())) return null;

        UIFrame absoluteContainer = new UIFrame();
        absoluteContainer.Size = UDim2.fromScale(1, 0).add(UDim2.fromAbsolute(0, 20));
        absoluteContainer.Name = obj.Name;
        absoluteContainer.BackgroundTransparency = 1;
        absoluteContainer.SetCProp("ABSContainer", true);
        absoluteContainer.SetCProp("Instance", obj);
        
        UIFrame childrenFrame = new UIFrame();
        childrenFrame.Position = UDim2.fromAbsolute(5, 20);
        childrenFrame.Size = UDim2.fromScale(.98, 0);
        childrenFrame.BackgroundTransparency = 1;
        childrenFrame.Name = "ChildrenFrame";
        childrenFrame.Visible = false;
        childrenFrame.SetParent(absoluteContainer);

        listFrame.GetChildWhichIsA("UIListLayout").Clone().SetParent(childrenFrame);

        UIFrame frame = new UIFrame();
        frame.Size = UDim2.fromScale(1,0).add(UDim2.fromAbsolute(0, 20));
        frame.BackgroundTransparency = 1;
        frame.Name = "ObjectFrame";
        frame.Name = obj.Name;
        frame.BackgroundColor = StudioGlobals.BlueColor;
        frame.SetParent(absoluteContainer);

        File imageIconFile = new File("JGameStudio\\Assets\\InstanceIcons\\"+obj.getClass().getSimpleName()+".png");
        if (!imageIconFile.exists()) {
            imageIconFile = new File("JGameStudio\\Assets\\InstanceIcons\\UNKNOWN.png");
        }

        UIImage img = new UIImage(); 
        img.AnchorPoint = new Vector2(0, .5);
        img.Size = UDim2.fromScale(.1, .9);
        img.Position = UDim2.fromScale(.04, .5);
        img.MouseTargetable = false;
        img.BackgroundTransparency = 1;
        img.SetImage(imageIconFile.getPath(), new Vector2(25));
        img.SetParent(frame);

        new UIAspectRatioConstraint().SetParent(img);

        UIText name = new UIText();
        name.Size = UDim2.fromScale(.9, 1);
        name.Position = UDim2.fromScale(.1, 0);
        name.Text = obj.Name;
        name.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        name.BackgroundTransparency = 1;
        name.TextColor = StudioGlobals.TextColor;
        name.CustomFont = StudioGlobals.GlobalFont;
        name.FontSize = 15;
        name.FontStyle = Font.BOLD;
        name.MouseTargetable = false;
        name.SetParent(frame);

        UIImage arrow = img.Clone();
        arrow.MouseTargetable = true;
        arrow.Position = UDim2.fromScale(0, .5);
        arrow.SetImage("JGameStudio\\Assets\\Icons\\DropdownOpen.png", new Vector2(15));
        arrow.Size = UDim2.fromScale(0, .7);
        arrow.Name = "DropdownArrow";
        arrow.Visible = obj.GetChildren().length > 0;

        for (Instance c : obj.GetChildren()) {
            UIFrame objFrame = createInstanceFrame(c);
            if (objFrame == null) return null;
            childrenFrame.Size = childrenFrame.Size.add(UDim2.fromAbsolute(0, objFrame.Size.Y.Absolute));
            objFrame.SetParent(childrenFrame);
            arrow.Visible = true;

            trackedInstances.add(c);
        }

        arrow.SetParent(frame);

        arrow.Mouse1Down.Connect(()-> {
            if (!arrow.Visible) return;
            if (!childrenFrame.Visible) {
                absoluteContainer.Size = UDim2.fromScale(1, 0).add(UDim2.fromAbsolute(0, 20+childrenFrame.Size.Y.Absolute));

                for (Instance ancestor : absoluteContainer.GetAncestors()) {
                    if (ancestor.GetCProp("ABSContainer") == null) continue;
                    UIBase baseAnc = (UIBase) ancestor;
                    baseAnc.Size = baseAnc.Size.add(UDim2.fromAbsolute(0, childrenFrame.Size.Y.Absolute));
                }

                childrenFrame.Visible = true;
                arrow.Rotation = Math.toRadians(90);
            } else {
                absoluteContainer.Size = UDim2.fromScale(1, 0).add(UDim2.fromAbsolute(0, 20));

                for (Instance ancestor : absoluteContainer.GetAncestors()) {
                    if (ancestor.GetCProp("ABSContainer") == null) continue;
                    UIBase baseAnc = (UIBase) ancestor;
                    baseAnc.Size = baseAnc.Size.subtract(UDim2.fromAbsolute(0, childrenFrame.Size.Y.Absolute));
                }

                childrenFrame.Visible = false;
                arrow.Rotation = Math.toRadians(0);
            }
        });

        frame.MouseEnter.Connect(()->{
            frame.BackgroundTransparency = .8;
        });

        frame.MouseLeave.Connect(()->{
            frame.BackgroundTransparency = 1;
        });

        
        obj.ChildAdded.Connect(inst ->{
            if (trackedInstances.contains(inst)) return;

            UIFrame objFrame = createInstanceFrame(inst);
            if (objFrame == null) return;
            childrenFrame.Size = childrenFrame.Size.add(UDim2.fromAbsolute(0, objFrame.Size.Y.Absolute));
            objFrame.SetParent(childrenFrame);
            arrow.Visible = true;

            trackedInstances.add(inst);
        });
        
        obj.ChildRemoved.Connect(inst ->{
            arrow.Visible = obj.GetChildren().length > 0;
        });

        obj.Destroying.Connect(()->{
            absoluteContainer.Destroy();
        });

        return absoluteContainer;
    }

    public void AddInstance(Instance obj) {
        UIFrame container = createInstanceFrame(obj);

        trackedInstances.add(obj);

        container.SetParent(listFrame);
    }

    private void createList() {
        listFrame = new UIFrame();
        listFrame.Size = UDim2.fromScale(1, 1);
        listFrame.Position = UDim2.fromAbsolute(0, 50);
        listFrame.BackgroundTransparency = 1;
        listFrame.SetParent(this);

        UIListLayout layout = new UIListLayout();
        layout.Padding = UDim2.zero;
        layout.SetParent(listFrame);
    }


    private void createHeader() {
        UIFrame headerBackground = new UIFrame();
        headerBackground.Size = UDim2.fromScale(1, 0).add(UDim2.fromAbsolute(0, 25));
        headerBackground.BackgroundColor = StudioGlobals.GrayColor.darker();
        headerBackground.ZIndex = 1;
        headerBackground.SetParent(this);

        UIText header = new UIText();
        header.Size = UDim2.fromScale(1, 1);
        header.BackgroundTransparency = 1;
        header.Text = "Explorer";
        header.TextColor = StudioGlobals.TextColor;
        header.FontSize = 20;
        header.CustomFont = StudioGlobals.GlobalFont;
        header.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        header.Position = UDim2.fromAbsolute(10, 0);
        header.SetParent(headerBackground);

        UIFrame line = new UIFrame();
        line.Size = UDim2.fromScale(1, 0).add(UDim2.fromAbsolute(0, 1));
        line.BackgroundColor = StudioGlobals.ForegroundColor;
        line.Position = UDim2.fromScale(0, 1);
        line.SetParent(headerBackground);

        UIFrame filterBackground = headerBackground.Clone();
        filterBackground.Position = filterBackground.Position.add(UDim2.fromAbsolute(0, filterBackground.Size.Y.Absolute));
        filterBackground.BackgroundColor = filterBackground.BackgroundColor.darker();
        filterBackground.Size = filterBackground.Size.subtract(UDim2.fromAbsolute(0, 3));
        filterBackground.ZIndex = 0;
        filterBackground.SetParent(this);

        filter = new UITextInput();
        filter.Size = UDim2.fromScale(1, 1);
        filter.BackgroundTransparency = 1;
        filter.PlaceholderText = "Filter Explorer";
        filter.TextColor = StudioGlobals.TextColor;
        filter.FontSize = 17;
        filter.CustomFont = StudioGlobals.GlobalFont;
        filter.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        filter.Position = UDim2.fromAbsolute(10, 0);
        filter.SetParent(filterBackground);

        filterBackground.GetChildOfClass(UIText.class).Destroy();
    }

}
