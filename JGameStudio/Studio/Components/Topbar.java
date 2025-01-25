package JGameStudio.Studio.Components;

import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGameStudio.StudioGlobals;

public class Topbar extends UIFrame {
    
    public Topbar() {
        this.Size = UDim2.fromScale(1, 0).add(UDim2.fromAbsolute(0, 105));
        this.BackgroundColor = StudioGlobals.BackgroundColor;
        this.AnchorPoint = new Vector2(0, 0);
        this.ZIndex = 2;

        StudioGlobals.GlobalBorder.Clone().SetParent(this);
    }
}
