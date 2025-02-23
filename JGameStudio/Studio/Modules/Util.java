package JGameStudio.Studio.Modules;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class Util {
    private static JGame game = JGame.CurrentGame;

    public static boolean isStudioComponent(Instance e) {
        if (e == null) return false;
        Instance topbar = game.UINode.GetChild("Topbar");
        Instance sideBar = game.UINode.GetChild("Sidebar");

        return e.IsDescendantOf(topbar) || e.IsDescendantOf(sideBar);
    }

    public static boolean shouldUseScaleForUDim2Transformations(UDim2 point) {
        UDim2 scaleUD = point.ToScale();
        UDim2 absoluteUD = point.ToAbsolute();

        Vector2 scaleVec = scaleUD.ToVector2(game.WindowService.GetWindowSize());
        Vector2 absoluteVec = new Vector2(absoluteUD.X.Absolute, absoluteUD.Y.Absolute);

        return scaleVec.magnitude() > absoluteVec.magnitude();
    }

    public static Vector2 vec2FromString(String s) {
        s = s.replace("(", "").replace(")", "");

        String[] split = s.split(",");
        if (split.length <= 1) {
            split = s.split(" ");
        }

        if (split.length <= 1) {
            return new Vector2(Double.valueOf(s));
        }

        return new Vector2(Double.valueOf(split[0]), Double.valueOf(split[1]));
    }
}
