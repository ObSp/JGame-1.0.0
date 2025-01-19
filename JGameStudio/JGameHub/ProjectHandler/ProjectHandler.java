package JGameStudio.JGameHub.ProjectHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.OffsetDateTime;

import JGameStudio.lib.JSONSimple.JSONObject;

public class ProjectHandler {

    private static void writeToFile(File f, String contents) {
        try (FileWriter writer = new FileWriter(f)){
            writer.write(contents);
            writer.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String readFile(File f) {
        String contents = null;
        try {
            contents = Files.readString(f.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }
    
    @SuppressWarnings("unchecked")
    public static File Create(String name, String creationFolder, String jgameSrc) {
        String pathToNewFolder = creationFolder+"\\"+name;
        String sep = "\\";
        String pathTodotJgame = pathToNewFolder+sep+".jgame";

        File dir = new File(pathToNewFolder);
        dir.mkdirs();
        
        File javaFile = new File(pathToNewFolder+sep+name+".java");
        File dotjgame = new File(pathTodotJgame);
        File projectJson = new File(pathTodotJgame+sep+"project.json");
        File dotVSCode = new File(pathToNewFolder+sep+".vscode");
        File settingsJSON = new File(dotVSCode.getPath()+sep+"settings.json");
        File jgamePackageDir = new File(pathToNewFolder+sep+"JGamePackage");

        dotjgame.mkdir();
        dotVSCode.mkdir();
        jgamePackageDir.mkdir();

        try {
            javaFile.createNewFile();
            projectJson.createNewFile();
            settingsJSON.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        };

        //write project json
        JSONObject projectJSONDict = new JSONObject();
        projectJSONDict.put("jgame_src", jgameSrc);
        projectJSONDict.put("creation_date", OffsetDateTime.now().toString());
        writeToFile(projectJson, projectJSONDict.toJSONString());

        //write to VS Code settings
        JSONObject settingsJSONDict = new JSONObject();
        JSONObject settingsExclude = new JSONObject();
        settingsExclude.put("JGamePackage", true);
        settingsExclude.put(".jgame", true);
        settingsJSONDict.put("files.exclude", settingsExclude);
        writeToFile(settingsJSON, settingsJSONDict.toJSONString());

        File mainSrcFile = new File("JGameStudio\\JGameHub\\ProjectHandler\\Templates\\MainFileTemplate.txt");
        String mainSrc = readFile(mainSrcFile);
        mainSrc =  mainSrc.replaceFirst("%s", name); //replace class name arg with actual class nam;
        writeToFile(javaFile, mainSrc);

        //clone JGame Source
        copyDir(jgameSrc, jgamePackageDir.getAbsolutePath(), false);
        
        return dir;
    }

    private static void copyDir(String src, String dest, boolean overwrite) {
    try {
        Files.walk(Paths.get(src)).forEach(a -> {
            Path b = Paths.get(dest, a.toString().substring(src.length()));
            try {
                if (!a.toString().equals(src))
                    Files.copy(a, b, overwrite ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[]{});
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    } catch (IOException e) {
        //permission issue
        e.printStackTrace();
    }
}
}
