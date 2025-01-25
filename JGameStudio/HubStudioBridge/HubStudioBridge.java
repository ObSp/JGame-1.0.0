package JGameStudio.HubStudioBridge;

import java.io.IOException;

public class HubStudioBridge {

    public static void openProjectInStudio(String path) {
        System.out.println("open");
        try {
            Runtime.getRuntime().exec( new String[] {
                "cd C:\\Users\\Paul W\\Documents\\GitHub\\JGame-1.0.0"
                //"powershell.exe c:; cd 'c:\\Users\\Paul W\\Documents\\GitHub\\JGame-1.0.0'; & 'C:\\Program Files\\Java\\jdk-21\\bin\\java.exe' '@C:\\Users\\PAULW~1\\AppData\\Local\\Temp\\cp_7ltqjll75avcqjpjt91uewav6.argfile' 'JGameStudio.Studio.Studio' \"" + path + "\""
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
