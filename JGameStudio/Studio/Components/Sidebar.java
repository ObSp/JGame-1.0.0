package JGameStudio.Studio.Components;

import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.Modifiers.UIBorder;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGameStudio.StudioGlobals;

public class Sidebar extends UIFrame {
    public Sidebar() {
        this.Size = UDim2.fromAbsolute(350, 0).add(UDim2.fromScale(0, .85));
        this.BackgroundColor = StudioGlobals.BackgroundColor;
        this.AnchorPoint = new Vector2(1, 0);
        this.Position = UDim2.fromScale(1,.15);
        this.ZIndex = 1;

        UIBorder border = new UIBorder();
        border.Width = 1;
        border.BorderColor = StudioGlobals.ForegroundColor;
        border.SetParent(this);
    }
}
