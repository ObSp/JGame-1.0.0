package JGamePackage.JGame.Classes.Misc;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.lib.CustomError.CustomError;

public class WorldNode extends Instance {
private CustomError ErrCannotClone = new CustomError("Cannot clone instance of type WorldNode.", CustomError.ERROR, new String[] {"JGamePackage", "java.desktop", "java.base"});

    @Override
    public WorldNode Clone() {
        ErrCannotClone.Throw();
        return null;
    }

    @Override
    protected WorldNode cloneWithoutChildren() {
        ErrCannotClone.Throw();
        return null;
    }

    @Override
    public boolean CanClone() {
        return false;
    }
}
