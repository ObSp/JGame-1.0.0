package JGameStudio.Studio.Components;

import java.awt.Color;

import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.Modifiers.UIBorder;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGameStudio.StudioGlobals;

public class DisplayWindow extends UIFrame {

    public DisplayWindow() {
        this.Size = UDim2.fromAbsolute(game.Services.WindowService.GetWindowWidth()-350, 0).add(UDim2.fromScale(0, .85));
        this.Position = UDim2.fromScale(0, .15);
        this.BackgroundColor = new Color(46, 46, 46);

        UIBorder border = new UIBorder();
        border.Width = 1;
        border.BorderColor = StudioGlobals.ForegroundColor;
        border.SetParent(this);
    }
}
