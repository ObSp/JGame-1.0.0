package JGameStudio.Studio.Instances;

import JGamePackage.JGame.Classes.Modifiers.BorderEffect;
import JGameStudio.StudioGlobals;

public class SelectionBorder extends BorderEffect {
    
    public SelectionBorder() {
        super();
        this.BorderColor = StudioGlobals.BlueColor.darker();
        this.Width = 4;
    }

    @Override
    public boolean CanClone() {
        return false;
    }
    
}
