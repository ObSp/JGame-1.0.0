package JGameStudio.Studio.Modules;

import java.awt.Color;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.World.WorldBase;
import JGamePackage.lib.Signal.Signal;

public class ColorManager {
    private static Color curColor = Color.gray;

    public static final Signal<Color> ColorChanged = new Signal<>();

    public static void setColor(Color c) {
        curColor = c;
        ColorChanged.Fire(c);

        for (Instance v : Selection.get()) {
            if (v instanceof WorldBase) {
                ((WorldBase) v).FillColor = c;
            } else if (v instanceof UIBase) {
                ((UIBase) v).BackgroundColor = c;
            }
        }
    }

    public static Color getColor() {
        return curColor;
    }
}
