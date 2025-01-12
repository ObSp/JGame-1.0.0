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

public class Welcome {
    
    public static UIBase createWelcomePageNode() {
        UIFrame container = new UIFrame();
        container.BackgroundColor = new Color(20,20,20);
        container.Size = UDim2.fromScale(1, 1);

        UIFrame leftMenu = new UIFrame();
        leftMenu.ZIndex = 2;
        leftMenu.BackgroundColor = container.BackgroundColor.brighter().brighter();
        leftMenu.Size = UDim2.fromScale(.15, 1);
        leftMenu.SetParent(container);

        UIImage banner = new UIImage();
        banner.SetImage("JGameStudio\\Assets\\jgameBannerBackground.png");
        banner.AnchorPoint = new Vector2(.5, 0);
        banner.Position = UDim2.fromScale(.5, .02);
        banner.Size = UDim2.fromScale(.9, .4);
        banner.SetParent(container);

        UIText continueText = new UIText();
        continueText.Position = UDim2.fromScale(.01, .2);
        continueText.Text = "Jump back in";
        continueText.BackgroundTransparency = 1;
        continueText.Size = UDim2.fromScale(1, .02);
        continueText.TextColor = Color.white;
        continueText.TextScaled = true;
        continueText.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        continueText.SetParent(container);

        return container;
    }

}
