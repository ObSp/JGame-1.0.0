package JGameStudio.Pages;

import java.awt.Color;

import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Types.PointObjects.UDim2;

public class Welcome {
    
    public static UIBase createWelcomePageNode() {
        UIFrame container = new UIFrame();
        container.BackgroundColor = new Color(20,20,20);

        container.Size = UDim2.fromScale(1, 1);

        UIText continueText = new UIText();
        continueText.Text = "Jump back in";
        continueText.TextScaled = true;
        continueText.SetParent(container);

        return container;
    }

}
