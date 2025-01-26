package JGameStudio.HubStudioBridge;

import java.io.IOException;

public class HubStudioBridge {
    private static String username = System.getProperty("user.name");
    private static String command = "powershell.exe cd 'c:\\Users\\%u\\Documents\\GitHub\\JGame-1.0.0' ; & 'C:\\Program Files\\Java\\jdk-21\\bin\\java.exe' '@C:\\Users\\%u\\AppData\\Local\\Temp\\cp_b1tap17rzruuy5jvwumf417xx.argfile' 'JGameStudio.Studio.Studio'";
    //private static String command = "powershell.exe";
    
	@SuppressWarnings("deprecation")
    public static void openProjectInStudio(String path) {
        try {
            Runtime.getRuntime().exec(
                command.replaceAll("%u", username) + path
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
