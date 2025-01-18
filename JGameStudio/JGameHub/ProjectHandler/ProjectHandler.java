package JGameStudio.JGameHub.ProjectHandler;

import java.io.File;
import java.io.IOException;

public class ProjectHandler {
    
    public static File Create(String name, String creationFolder) {
        String pathToNewFolder = creationFolder+"\\"+name;
        String sep = "\\";
        String pathTodotJgame = pathToNewFolder+sep+".jgame";

        File dir = new File(pathToNewFolder);
        dir.mkdirs();
        
        File javaFile = new File(pathToNewFolder+sep+name+".java");
        File dotjgame = new File(pathTodotJgame);
        File projectJson = new File(pathTodotJgame+sep+"project.json");

        dotjgame.mkdir();
        try {
            javaFile.createNewFile();
            projectJson.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        };
        return dir;
    }
}
