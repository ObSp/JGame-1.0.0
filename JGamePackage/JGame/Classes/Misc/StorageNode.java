package JGamePackage.JGame.Classes.Misc;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.lib.CustomError.CustomError;

public class StorageNode extends Instance{
    private CustomError ErrCannotClone = new CustomError("Cannot clone instance of type StorageNode.", CustomError.ERROR, new String[] {"JGamePackage", "java.desktop", "java.base"});

    @Override
    public StorageNode Clone() {
        ErrCannotClone.Throw();
        return null;
    }

    @Override
    protected StorageNode cloneWithoutChildren() {
        ErrCannotClone.Throw();
        return null;
    }

    @Override
    public boolean CanClone() {
        return false;
    }
}
