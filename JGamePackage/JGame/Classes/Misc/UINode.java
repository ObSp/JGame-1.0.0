package JGamePackage.JGame.Classes.Misc;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.lib.CustomError.CustomError;

public class UINode extends Instance {
    private CustomError ErrCannotClone = new CustomError("Cannot clone instance of type UINode.", CustomError.ERROR, new String[] {"JGamePackage", "java.desktop", "java.base"});

    @Override
    public UINode Clone() {
        ErrCannotClone.Throw();
        return null;
    }

    @Override
    protected UINode cloneWithoutChildren() {
        ErrCannotClone.Throw();
        return null;
    }

    @Override
    public boolean CanClone() {
        return false;
    }
}
