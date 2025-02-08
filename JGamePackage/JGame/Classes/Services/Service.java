package JGamePackage.JGame.Classes.Services;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Instance;
import JGamePackage.lib.CustomError.CustomError;

public abstract class Service extends Instance {
    @Override
    public Service Clone() {
        return null;
    }

    @Override
    public Service cloneWithoutChildren() {
        return null;
    }
}
