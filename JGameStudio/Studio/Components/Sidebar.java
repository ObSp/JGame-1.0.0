package JGameStudio.Studio.Components;

import JGamePackage.JGame.Classes.Modifiers.BorderEffect;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGameStudio.StudioGlobals;

public class Sidebar extends UIFrame {
    public Explorer explorer;
    public Properties properties;

    public Sidebar() {
        this.Size = UDim2.fromAbsolute(350, 0).add(UDim2.fromScale(0, .91));
        this.BackgroundColor = StudioGlobals.BackgroundColor;
        this.AnchorPoint = new Vector2(1, 0);
        this.Position = UDim2.fromScale(1,0).add(UDim2.fromAbsolute(0, 105));
        this.ZIndex = 1;

        BorderEffect border = new BorderEffect();
        border.Width = 1;
        border.BorderColor = StudioGlobals.ForegroundColor;
        border.SetParent(this);

        explorer = new Explorer();
        explorer.SetParent(this);

        properties = new Properties();
        properties.SetParent(this);
    }

}
