package JGameStudio;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class StudioGlobals{
    public static Font GlobalFont;
    public static Font StartupFont;

    public static Color BackgroundColor = new Color(60,63,65).darker();
    public static Color ForegroundColor = BackgroundColor.brighter().brighter();
    public static Color TextColor = Color.lightGray.brighter();

    public static void construct() {
        try {
            GlobalFont = Font.createFont(Font.TRUETYPE_FONT, new File("JGameStudio\\Assets\\Fonts\\Roboto_Condensed\\RobotoCondensed-VariableFont_wght.ttf"));
            StartupFont = Font.createFont(Font.TRUETYPE_FONT, new File("JGameStudio\\Assets\\Fonts\\PixeloidSans-mLxMm.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
