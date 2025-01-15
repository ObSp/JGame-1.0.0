package JGameStudio;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class StudioUtil {
    public static JGame game;

    public static Vector2 UDim2ToVector2(UDim2 im2) {
        return im2.ToVector2(game.Services.WindowService.GetScreenSize());
    }

    public static double ScaleToAbsoluteX(double scale) {
        return scale/game.Services.WindowService.GetScreenWidth();
    }

    public static UDim2 UDim2ScaleToAbsolute(UDim2 im2) {
        Vector2 vec = UDim2ToVector2(im2);
        return UDim2.fromAbsolute(vec.X, vec.Y);
    }
}
