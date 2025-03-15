package JGameStudio;

import java.util.Arrays;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class StudioUtil {
    public static JGame game;

    public static Vector2 UDim2ToVector2(UDim2 im2) {
        return im2.ToVector2(game.Services.WindowService.GetScreenSize());
    }

    public static double ScaleToAbsoluteX(double scale) {
        return scale/game.Services.WindowService.GetWindowWidth();
    }

    public static UDim2 UDim2ScaleToAbsolute(UDim2 im2) {
        Vector2 vec = UDim2ToVector2(im2);
        return UDim2.fromAbsolute(vec.X, vec.Y);
    }

    public static UDim2 UDim2ScaleToAbsolute(UDim2 im2, UIBase parent) {
        Vector2 vec = im2.ToVector2(parent.GetAbsoluteSize());
        return UDim2.fromAbsolute(vec.X, vec.Y);
    }

    public static Instance[] ConcatInstanceArrays(Instance[]... arrays) {
        int length = 0;

        for (Instance[] array : arrays)
            length += array.length;

        Instance[] first = arrays[0];
        Instance[] result = Arrays.copyOf(first, length);

        int index = first.length;

        for (int i = 1; i < arrays.length; i++) {
            Instance[] array = arrays[i];
            System.arraycopy(array, 0, result, index, array.length);
            index += array.length;
        }

        return result;
    }
}
