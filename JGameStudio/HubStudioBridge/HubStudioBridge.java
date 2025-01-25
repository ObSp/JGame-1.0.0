package JGameStudio.HubStudioBridge;

import java.io.IOException;

public class HubStudioBridge {

    @SuppressWarnings("deprecation")
    public static void openProjectInStudio(String path) {
        try {
            Runtime.getRuntime().exec(
                "powershell.exe cd 'c:\\Users\\Paul\\Documents\\GitHub\\JGame-1.0.0'; & 'C:\\Program Files\\Java\\jdk-21\\bin\\java.exe' '@C:\\Users\\Paul\\AppData\\Local\\Temp\\cp_b1tap17rzruuy5jvwumf417xx.argfile' 'JGameStudio.Studio.Studio' \"" + path + "\""
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
