package JGameStudio.ProjectHandler;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import JGamePackage.lib.JSONSimple.JSONObject;
import JGamePackage.lib.JSONSimple.parser.JSONParser;

/**Structure of a project is as follows: <p>
 * ProjectFolder <p>
 *   |--.vscode <p>
 *   |     |--settings.json<p>
 *   |--.jgame<p>
 *   |     |--project.json<p>
 *   |--JGamePackage<p>
 *   |--ProjectMain.java<p>
 * 
 */
public class ProjectHandler {

    private static void writeToFile(File f, String contents) {
        try (FileWriter writer = new FileWriter(f)) {
            writer.write(contents);
            writer.flush();
        } catch (Exception e) {
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
        String pathToNewFolder = creationFolder + "\\" + name;
        String sep = "\\";
        String pathTodotJgame = pathToNewFolder + sep + ".jgame";
        String alphanumericName = name.replaceAll("[^A-Za-z0-9]","");

        File dir = new File(pathToNewFolder);
        dir.mkdirs();

        File javaFile = new File(pathToNewFolder + sep + alphanumericName + ".java");
        File dotjgame = new File(pathTodotJgame);
        File projectJson = new File(pathTodotJgame + sep + "project.json");
        File worldJson = new File(pathTodotJgame + sep + "world.json");
        File dotVSCode = new File(pathToNewFolder + sep + ".vscode");
        File settingsJSON = new File(dotVSCode.getPath() + sep + "settings.json");
        File jgamePackageDir = new File(pathToNewFolder + sep + "JGamePackage");

        dotjgame.mkdir();
        dotVSCode.mkdir();
        jgamePackageDir.mkdir();

        try {
            javaFile.createNewFile();
            projectJson.createNewFile();
            settingsJSON.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        // write project json
        JSONObject projectJSONDict = new JSONObject();
        projectJSONDict.put("jgame_src", new File(jgameSrc).getAbsolutePath());
        projectJSONDict.put("creation_date", Instant.now().toString());
        projectJSONDict.put("modified_date", Instant.now().toString());
        projectJSONDict.put("name", name);
        writeToFile(projectJson, projectJSONDict.toJSONString());

        //write world json
        writeToFile(worldJson, "[]");
        

        // write to VS Code settings
        JSONObject settingsJSONDict = new JSONObject();
        JSONObject settingsExclude = new JSONObject();
        settingsExclude.put("JGamePackage", true);
        settingsExclude.put(".jgame", true);
        settingsJSONDict.put("files.exclude", settingsExclude);
        writeToFile(settingsJSON, settingsJSONDict.toJSONString());

        File mainSrcFile = new File("JGameStudio\\ProjectHandler\\Templates\\MainFileTemplate.txt");
        String mainSrc = readFile(mainSrcFile);
        mainSrc = mainSrc.replaceFirst("%s", alphanumericName); // replace class name arg with actual class nam;
        writeToFile(javaFile, mainSrc);

        // clone JGame Source
        copyDir(jgameSrc, jgamePackageDir.getAbsolutePath(), true);

        return dir;
    }

    public static ProjectData ReadProjectDir(String path) {
        File dotJGame = new File(path + "\\.jgame");
        File settingsJSON = new File(dotJGame.getPath()+"\\project.json");

        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(settingsJSON);

            JSONObject baseObject = (JSONObject) parser.parse(reader);

            String creationDateString; String modifiedDateString; String name;

            creationDateString = (String) baseObject.get("creation_date");
            modifiedDateString = (String) baseObject.get("modified_date");
            name = (String) baseObject.get("name");

            Instant creationInstant = Instant.parse(creationDateString);
            Instant modifiedInstant = Instant.parse(modifiedDateString);

            return new ProjectData(convertInstantToDayMonthYear(creationInstant) , convertInstantToDayMonthYear(modifiedInstant), name, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public record ProjectData (String creationDate, String modifiedDate, String name, String path) {}

    private static String convertInstantToDayMonthYear(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern("dd-MM-uuuu")
        .withZone(ZoneOffset.systemDefault());

        return LocalDateTime.ofInstant(instant, formatter.getZone()).format(formatter).replaceAll("-", ".");
    }

    private static void copyDir(String src, String dest, boolean overwrite) { //courtesy of stack overflow :)
        try {
            Files.walk(Paths.get(src)).forEach(a -> {
                Path b = Paths.get(dest, a.toString().substring(src.length()));
                try {
                    if (!a.toString().equals(src))
                        Files.copy(a, b, overwrite ? new CopyOption[] { StandardCopyOption.REPLACE_EXISTING }: new CopyOption[] {});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            // permission issue
            e.printStackTrace();
        }
    }
}
