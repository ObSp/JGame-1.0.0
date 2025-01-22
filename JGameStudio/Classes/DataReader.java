package JGameStudio.Classes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import JGameStudio.lib.JSONSimple.JSONArray;
import JGameStudio.lib.JSONSimple.JSONObject;
import JGameStudio.lib.JSONSimple.parser.JSONParser;

public class DataReader {
    private static HashMap<String, DataReader> activeReaders = new HashMap<>();

    private String path;

    public JSONData Data;

    public static DataReader GetActiveReaderFromPath(String path) {
        return activeReaders.get(path);
    }

    public DataReader(String pathToJSON) {
        this.path = pathToJSON;
        ReadJSON();

        activeReaders.put(pathToJSON, this);
    }

    /**Reads the JSON file located at {@code this.path} and sets {@code this.Data} to a {@code JSONData} object created from the JSON file.
     * 
     * @return The created JSONData object
     */
    @SuppressWarnings("unchecked")
    public JSONData ReadJSON() {
        ArrayList<String> projects = null;
        String hub_version = null;
        String editor_version = null;
        String creation_path = null;
        String default_jgame_installation = null;

        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(new File(path));

            JSONObject baseObject = (JSONObject) parser.parse(reader);

            projects = (JSONArray) baseObject.get("projects");
            hub_version = (String) baseObject.get("hub_version");
            editor_version = (String) baseObject.get("editor_version");
            creation_path = (String) baseObject.get("creation_path");
            default_jgame_installation = (String) baseObject.get("default_jgame_installation");
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.Data = new JSONData(projects, hub_version, editor_version, creation_path, default_jgame_installation);
        return this.Data;
    }

    /**Updates the JSON file located at {@code this.path} to contain the given arguments
     * 
     */
    @SuppressWarnings("unchecked")
    public void UpdateJSON(ArrayList<String> projects, String hubVersion, String editorVersion, String creationPath, String default_jgame_installation) {
        JSONObject baseObject = new JSONObject();
        baseObject.put("projects", (JSONArray) projects);
        baseObject.put("hub_version", hubVersion);
        baseObject.put("editor_version", editorVersion);
        baseObject.put("creation_path", creationPath);
        baseObject.put("default_jgame_installation", default_jgame_installation);

        try (FileWriter writer = new FileWriter(path)){
            writer.write(baseObject.toJSONString());
            writer.flush();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void UpdateJSON() {
        JSONObject baseObject = new JSONObject();
        baseObject.put("projects", (JSONArray) Data.projects);
        baseObject.put("hub_version", Data.hub_version);
        baseObject.put("editor_version", Data.editor_version);
        baseObject.put("creation_path", Data.creation_path);
        baseObject.put("default_jgame_installation", Data.default_jgame_installation);

        try (FileWriter writer = new FileWriter(path)){
            writer.write(baseObject.toJSONString());
            writer.flush();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public class JSONData {
        public final ArrayList<String> projects;
        public final String hub_version;
        public final String editor_version;
        public final String creation_path;
        public final String default_jgame_installation;

        public JSONData(ArrayList<String> projectArr, String hubVersion, String editorVersion, String creationPath, String default_jgame_installation) {
            this.projects = projectArr;
            this.hub_version = hubVersion;
            this.editor_version = editorVersion;
            this.creation_path = creationPath;
            this.default_jgame_installation = default_jgame_installation;
        }
    }
}
