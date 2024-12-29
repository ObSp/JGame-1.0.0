package JGameStudio.Pages;

import java.awt.Color;

import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;

public class Welcome {
    
    public static UIBase createWelcomePageNode() {
        UIFrame container = new UIFrame();
        container.BackgroundColor = new Color(20,20,20);

        container.Size = UDim2.fromScale(1, 1);
        System.out.println(container.GetAbsoluteSize());

        UIText continueText = new UIText();
        continueText.Position = UDim2.fromScale(.01, .03);
        continueText.Text = "Jump back in";
        continueText.BackgroundTransparency = 1;
        continueText.Size = UDim2.fromScale(1, .02);
        continueText.TextColor = Color.white;
        continueText.TextScaled = true;
        continueText.HorizontalTextAlignment = Constants.HorizontalTextAlignment.Left;
        continueText.SetParent(container);

        System.out.println(continueText.GetAbsolutePosition());

        return container;
    }

}
