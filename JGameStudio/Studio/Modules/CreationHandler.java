package JGameStudio.Studio.Modules;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.World.Box2D;

public class CreationHandler {
    private static JGame game;

    private static String selected = "Box2D";

    public static void createInstance() {
        game = JGame.CurrentGame;
        new Box2D().SetParent(game.WorldNode);
    }
}
