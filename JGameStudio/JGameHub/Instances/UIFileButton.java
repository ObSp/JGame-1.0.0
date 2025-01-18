package JGameStudio.JGameHub.Instances;

import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.UI.UIImage;
import JGamePackage.JGame.Classes.UI.UIText;

public class UIFileButton extends UIFrame {
    private UIText location;
    private UIImage icon;
    

    public UIFileButton() {
        
    }

    public void SetCurrentPath(String path) {
        location.Text = path;
    }
}
