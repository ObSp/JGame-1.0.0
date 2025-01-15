package JGameStudio.Pages;

import java.awt.Color;

import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Classes.UI.Modifiers.UIAspectRatioConstraint;
import JGamePackage.JGame.Classes.UI.Modifiers.UIListLayout;
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
        leftMenu.Size = StudioUtil.UDim2ScaleToAbsolute(UDim2.fromScale(.15, 1));
        leftMenu.SetParent(container);

        UIFrame innerContainer = new UIFrame();
        innerContainer.Size = StudioUtil.UDim2ScaleToAbsolute(UDim2.fromScale(.85, 1));
        innerContainer.Position = StudioUtil.UDim2ScaleToAbsolute(UDim2.fromScale(.15, 0));
        innerContainer.BackgroundTransparency = 1;
        innerContainer.SetParent(container);

        UIListLayout listLayout = new UIListLayout();
        listLayout.SetParent(innerContainer);

        UIFrame listFrame = new UIFrame();
        listFrame.Size = StudioUtil.UDim2ScaleToAbsolute(UDim2.fromScale(3, .3), innerContainer);
        listFrame.SetParent(innerContainer);
        

        return container;
    }

}
