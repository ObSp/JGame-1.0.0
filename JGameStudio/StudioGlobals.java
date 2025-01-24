package JGameStudio;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class StudioGlobals{
    public static Font GlobalFont;
    public static Font StartupFont;

    public static Color BackgroundColor = new Color(42,44,45);
    public static Color ForegroundColor = new Color(85, 88, 91);
    public static Color TextColor = Color.white;

    public static Color GreenColor = new Color(86, 227, 159);
    public static Color RedColor = new Color(216, 17, 89);
    public static Color OrangeColor  = new Color(255, 188, 66);
    public static Color BlueColor  = new Color(151, 223, 252);

    public static String jsonData = "JGameStudio\\Data\\data.json";

    public static void construct() {
        try {
            GlobalFont = Font.createFont(Font.TRUETYPE_FONT, new File("JGameStudio\\Assets\\Fonts\\Roboto\\static\\Roboto-Light.ttf"));
            StartupFont = Font.createFont(Font.TRUETYPE_FONT, new File("JGameStudio\\Assets\\Fonts\\PixeloidSans-mLxMm.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
