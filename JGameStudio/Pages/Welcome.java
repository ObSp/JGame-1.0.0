package JGameStudio.Pages;

import java.awt.Color;

import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Types.PointObjects.UDim2;

public class Welcome {
    
    public static UIBase createWelcomePageNode() {
        UIFrame container = new UIFrame();
        container.BackgroundColor = new Color(20,20,20);

        container.Size = UDim2.fromScale(1, 1);

        UIImage cover = new UIImage();
        cover.SetImage("JGameStudio\\Assets\\JGameStudioCover.png");
        cover.Size = UDim2.fromScale(1, .25);
        cover.BackgroundTransparency = 1;
        cover.SetParent(container);

        return container;
    }

}
