package JGameStudio;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class StudioUtil {
    public static JGame game;

    public static Vector2 UDim2ToVector2(UDim2 im2) {
        return im2.ToVector2(game.Services.WindowService.GetScreenSize());
    }
}
