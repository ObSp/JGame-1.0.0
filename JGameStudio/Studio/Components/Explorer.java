package JGameStudio.Studio.Components;

import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.UITextInput;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGameStudio.StudioGlobals;

public class Explorer extends UIFrame {

    public UITextInput filter;

    public Explorer() {
        this.Size = UDim2.fromScale(1, .55);
        this.BackgroundTransparency = 1;
        createHeader();
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

        filterBackground.GetChildWhichIsA(UIText.class).Destroy();
    }

}
