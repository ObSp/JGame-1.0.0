package JGameStudio.Pages;

import java.awt.Color;

import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.Modifiers.UIAspectRatioConstraint;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGameStudio.StudioUtil;

public class Welcome {
    
    public static UIBase createWelcomePageNode() {
        UIFrame container = new UIFrame();
        container.BackgroundColor = new Color(20,20,20);
        container.Size = UDim2.fromScale(1, 1);

        UIFrame leftMenu = new UIFrame();
        leftMenu.ZIndex = 2;
        leftMenu.BackgroundColor = container.BackgroundColor.brighter().brighter();
        leftMenu.Size = StudioUtil.UDim2ScaleToAbsolute(UDim2.fromScale(.10, 1));
        leftMenu.SetParent(container);

        return container;
    }

}
