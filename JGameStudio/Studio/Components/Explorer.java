package JGameStudio.Studio.Components;

import java.awt.Font;

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

    public Explorer() {
        this.Size = UDim2.fromScale(1, .55);
        this.BackgroundTransparency = 1;
        createHeader();
        createList();

        this.filter.TextUpdated.Connect(()-> {
            for (UIBase c : this.listFrame.GetChildrenOfClass(UIBase.class)) {
                c.Visible = c.Name.toLowerCase().contains(filter.Text.toLowerCase());
            }
        });

        this.AddInstance(game.WorldNode);
        this.AddInstance(game.UINode);
        this.AddInstance(game.StorageNode);
    }

    public void AddInstance(Instance obj) {
        UIFrame frame = new UIFrame();
        frame.Size = UDim2.fromScale(1,0).add(UDim2.fromAbsolute(0, 20));
        frame.BackgroundTransparency = 1;
        frame.Name = obj.Name;
        frame.SetParent(listFrame);

        UIImage img = new UIImage();
        img.AnchorPoint = new Vector2(0, .5);
        img.Size = UDim2.fromScale(.1, .9);
        img.SetParent(frame);
        img.Position = UDim2.fromScale(.04, .5);
        img.MouseTargetable = false;

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

        frame.MouseEnter.Connect(()->{
            frame.BackgroundTransparency = .8;
        });

        frame.MouseLeave.Connect(()->{
            frame.BackgroundTransparency = 1;
        });

        obj.Destroying.Connect(()->{
            frame.Destroy();
        });
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
