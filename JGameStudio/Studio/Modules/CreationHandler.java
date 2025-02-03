package JGameStudio.Studio.Modules;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.World.Box2D;
import JGamePackage.JGame.Classes.World.WorldBase;

public class CreationHandler {
    private static JGame game;

    private static String selected = "Box2D";

    private static HashMap<String, Class<? extends Instance>> classes = new HashMap<>();

    @SuppressWarnings({"deprecation"})
    public static void createInstance() {
        if (classes.get(selected) == null) {
            classes.put("Box2D", Box2D.class);
        }

        game = JGame.CurrentGame;

        Instance parent = Selection.getFirst();

        Instance created;
        
        try {
            created = classes.get(selected).newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
            return;
        }

        if (created instanceof WorldBase) {
            WorldBase c = (WorldBase) created;
            c.Position = game.Camera.Position.subtract(c.Size.multiply(.5));
        }

        if (selected != null) {
            created.SetParent(parent);
        } else if (created instanceof UIBase) {
            created.SetParent(game.UINode);
        } else {
            created.SetParent(game.WorldNode);
        }
    }
}
