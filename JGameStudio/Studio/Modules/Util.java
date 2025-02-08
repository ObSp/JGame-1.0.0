package JGameStudio.Studio.Modules;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Instance;

public class Util {
    private static JGame game = JGame.CurrentGame;

    public static boolean isStudioComponent(Instance e) {
        if (e == null) return false;
        Instance topbar = game.UINode.GetChild("Topbar");
        Instance sideBar = game.UINode.GetChild("Sidebar");

        return e.IsDescendantOf(topbar) || e.IsDescendantOf(sideBar);
    }
}
