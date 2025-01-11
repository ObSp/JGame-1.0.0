package JGamePackage.JGame.Classes.Services;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Instance;
import JGamePackage.lib.CustomError.CustomError;

public class Service extends Instance {
    private static CustomError ErrorNoJGame = new CustomError("A JGame must be running in order to create JGame services.", CustomError.ERROR, "JGamePackage");

    protected JGame game;

    public Service() {
        JGame current = JGame.CurrentGame;
        if (current == null) {
            ErrorNoJGame.Throw();
        }
        game = current;
    }

     @Override
    public Service Clone() {
        return null;
    }

    @Override
    public Service cloneWithoutChildren() {
        return null;
    }
}
