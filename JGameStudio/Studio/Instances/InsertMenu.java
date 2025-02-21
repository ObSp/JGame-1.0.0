package JGameStudio.Studio.Instances;

import java.awt.Color;

import JGamePackage.JGame.Classes.Modifiers.ListLayout;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIScrollFrame;
import JGamePackage.JGame.Classes.UI.UITextInput;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGameStudio.StudioGlobals;
import JGameStudio.Studio.Components.Sidebar;

public class InsertMenu extends UIFrame {
    public UITextInput filter;
    
    public InsertMenu(Sidebar sidebar) {
        this.SetParent(sidebar);

        this.Position = UDim2.fromAbsolute(0, 100);
        this.Size = UDim2.fromScale(.7, 0).add(UDim2.fromAbsolute(0, 250));
        this.BackgroundColor = StudioGlobals.BackgroundColor;
        this.ClipsDescendants = true;
        this.ZIndex = 100;

        filter = new UITextInput();
        filter.BackgroundColor = StudioGlobals.BackgroundColor.darker();
        filter.Size = UDim2.fromScale(.95, .1);
        filter.AnchorPoint = new Vector2(.5, 0);
        filter.Position = UDim2.fromScale(.5, .02);
        filter.TextColor = StudioGlobals.TextColor;
        filter.FontSize = 17;
        filter.ClipsDescendants = true;
        filter.PlaceholderText = "Search Instances";
        filter.SetParent(this);

        SelectionBorder border = new SelectionBorder();
        border.Width = 1;
        border.BorderColor = StudioGlobals.BlueColor;

        filter.FocusChanged.Connect(focused -> {
            border.SetParent(focused ? filter : null);
        });

        UIScrollFrame list = new UIScrollFrame();
        list.Position = UDim2.fromAbsolute(0, 40);
        list.Size = UDim2.fromScale(1, .85);
        list.BackgroundTransparency = .5;
        list.SetParent(this);

        ListLayout listLayout = new ListLayout();
        listLayout.Padding = UDim2.zero;
        listLayout.SetParent(list);

        for (int i = 0; i < 100; i++) {
            UIFrame frame = new UIFrame();
            frame.Size = UDim2.fromScale(.9, .1);
            frame.BackgroundColor = StudioGlobals.BackgroundColor.brighter();
            frame.SetParent(list);
        }
        

        StudioGlobals.GlobalBorder.SetParent(this);
    }

    public void OpenAt(UDim2 pos) {

    }
}
