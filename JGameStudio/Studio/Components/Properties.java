package JGameStudio.Studio.Components;

import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGameStudio.StudioGlobals;

public class Properties extends UIFrame {

    public Properties() {
        this.Size = UDim2.fromScale(1, .45);
        this.Position = UDim2.fromScale(0, .55);
        this.BackgroundTransparency = 1;
        createHeader();
    }
        
    private void createHeader() {
        UIFrame headerBackground = new UIFrame();
        headerBackground.Size = UDim2.fromScale(1, 0).add(UDim2.fromAbsolute(0, 25));
        headerBackground.BackgroundColor = StudioGlobals.GrayColor.darker();
        headerBackground.SetParent(this);

        UIText header = new UIText();
        header.Size = UDim2.fromScale(1, 1);
        header.BackgroundTransparency = 1;
        header.Text = "Properties";
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

        UIFrame topLine = line.Clone();
        topLine.Position = UDim2.zero;
        topLine.SetParent(headerBackground);
    }
}
