package JGameStudio.Studio.Components;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.Modifiers.AspectRatioConstraint;
import JGamePackage.JGame.Classes.Modifiers.ListLayout;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIImageButton;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.UITextInput;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.lib.Signal.Signal;
import JGameStudio.StudioGlobals;
import JGameStudio.Studio.Modules.Selection;

public class Explorer extends UIFrame {

    public UITextInput filter;
    public UIFrame listFrame;

    private UIImageButton plusButton;

    private Set<String> ignoredClasses = Set.of("Sidebar", "Topbar", "SelectionBorder");

    private ArrayList<Instance> trackedInstances = new ArrayList<>();
    private ArrayList<Instance> trackedBoxes = new ArrayList<>();

    public Explorer() {
        this.Size = UDim2.fromScale(1, .55);
        this.BackgroundTransparency = 1;

        createPlusButton();
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

        Selection.InstanceDeselected.Connect(inst -> {
            for (Instance frame : trackedBoxes) {
                if (frame.GetCProp("Instance") == inst) {
                    frame.<Signal<Boolean>>GetCProp("SelectSignal").Fire(false);
                    return;
                }
            }
        });

        Selection.InstanceSelected.Connect(inst -> {
            for (Instance frame : trackedBoxes) {
                if (frame.GetCProp("Instance") == inst) {
                    frame.<Signal<Boolean>>GetCProp("SelectSignal").Fire(true);
                    return;
                }
            }
        });

        //duplication
        game.InputService.OnKeyPress.Connect(kv -> {
            if (kv.getKeyCode() != KeyEvent.VK_D || !game.InputService.IsKeyDown(KeyEvent.VK_CONTROL)) return;

            for (Instance e : Selection.get()) {
                if (!e.CanClone()) continue;
                Instance clone = e.Clone();
                clone.SetParent(e.GetParent());

                Selection.remove(e);
                Selection.add(clone);
            }
        });

        this.AddInstance(game.WorldNode);
        this.AddInstanceAs(game.UINode.GetChildOfClass(DisplayWindow.class), "UINode", "UINode");
        this.AddInstance(game.ScriptNode);
        this.AddInstance(game.StorageNode);
        this.AddInstance(game.Camera);
    }

    private void createPlusButton() {
        plusButton = new UIImageButton();
        plusButton.SetImage("JGameStudio\\Assets\\Icons\\Plus.png");
        plusButton.Size = UDim2.fromScale(0, 1);
        plusButton.AnchorPoint = new Vector2(1, 0);
        plusButton.Position = UDim2.fromScale(1, 0);
        plusButton.BackgroundTransparency = 1;
        plusButton.MouseTargetable = false;
        plusButton.Destroyable = false;
        new AspectRatioConstraint().SetParent(plusButton);

        game.InputService.OnMouse1Click.Connect(()->{
            UIBase target = game.InputService.GetMouseUITarget(false);
            if (target == plusButton) {
                StudioGlobals.InsertMenu.OpenAtPosWithInstance(UDim2.fromScale(.5, 0).add(UDim2.fromAbsolute(0, plusButton.GetAbsolutePosition().Y)), plusButton.GetParent().GetParent().<Instance>GetCProp("Instance"));
            } else {
                if (target == StudioGlobals.InsertMenu || target.IsDescendantOf(StudioGlobals.InsertMenu)) return;
                StudioGlobals.InsertMenu.Visible = false;
            }
        });
    }

    private UIFrame createInstanceFrame(Instance obj, String displayText, String className) {
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

        listFrame.GetChildWhichIsA("ListLayout").Clone().SetParent(childrenFrame);

        UIFrame frame = new UIFrame();
        frame.Size = UDim2.fromScale(1,0).add(UDim2.fromAbsolute(0, 20));
        frame.BackgroundTransparency = 1;
        frame.Name = "ObjectFrame";
        frame.Name = obj.Name;
        frame.BackgroundColor = StudioGlobals.BlueColor;
        frame.SetParent(absoluteContainer);

        File imageIconFile = new File("JGameStudio\\Assets\\InstanceIcons\\"+className+".png");
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

        new AspectRatioConstraint().SetParent(img);

        UIText name = new UIText();
        name.Size = UDim2.fromScale(.9, 1);
        name.Position = UDim2.fromScale(.1, 0);
        name.Text = displayText;
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
            UIFrame objFrame = createInstanceFrame(c, c.Name, c.getClass().getSimpleName());
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

        frame.Mouse1Down.Connect(()-> {
            selectInstance(obj);
        });

        frame.MouseEnter.Connect(()->{
            plusButton.SetParent(frame);
            if (absoluteContainer.GetCProp("Selected") != null) return;
            frame.BackgroundTransparency = .8;
        });

        frame.MouseLeave.Connect(()->{
            if (plusButton.GetParent() == frame) {
                plusButton.SetParent(null);
            }

            if (absoluteContainer.GetCProp("Selected") != null) return;
            frame.BackgroundTransparency = 1;
        });

        
        obj.ChildAdded.Connect(inst ->{
            if (trackedInstances.contains(inst)) return;

            UIFrame objFrame = createInstanceFrame(inst, inst.Name, inst.getClass().getSimpleName());
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

        Signal<Boolean> selectChanged = new Signal<>();
        absoluteContainer.SetCProp("SelectSignal", selectChanged);

        selectChanged.Connect(selected -> {
            if (selected) {
                frame.BackgroundTransparency = .7;
                absoluteContainer.SetCProp("Selected", true);
            } else {
                frame.BackgroundTransparency = 1;
                absoluteContainer.SetCProp("Selected", null);
            }
        });

        trackedBoxes.add(absoluteContainer);

        return absoluteContainer;
    }

    public void AddInstance(Instance obj) {
        UIFrame container = createInstanceFrame(obj, obj.Name, obj.getClass().getSimpleName());

        trackedInstances.add(obj);

        container.SetParent(listFrame);
    }

    public void AddInstanceAs(Instance obj, String name, String className) {
        UIFrame container = createInstanceFrame(obj, name, className);

        trackedInstances.add(obj);

        container.SetParent(listFrame);
    }

    private void createList() {
        listFrame = new UIFrame();
        listFrame.Size = UDim2.fromScale(1, 1);
        listFrame.Position = UDim2.fromAbsolute(0, 50);
        listFrame.BackgroundTransparency = 1;
        listFrame.SetParent(this);

        ListLayout layout = new ListLayout();
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

    private void selectInstance(Instance v) {
        boolean ctrlDown = game.InputService.IsKeyDown(KeyEvent.VK_CONTROL);

        if (ctrlDown) {
            if (Selection.contains(v)) {
                Selection.remove(v);
            } else {
                Selection.add(v);
            }
        } else {
            Selection.set(v);
        }
    }
}
