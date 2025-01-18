package JGameStudio.Classes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;

import JGameStudio.lib.JSONSimple.JSONArray;
import JGameStudio.lib.JSONSimple.JSONObject;
import JGameStudio.lib.JSONSimple.parser.JSONParser;

public class DataReader {
    private String path;

    public JSONData Data;

    public DataReader(String pathToJSON) {
        this.path = pathToJSON;
        ReadJSON();
    }

    @SuppressWarnings("unchecked")
    private <T extends Object> T[] jsonArrayToArray(JSONArray jsonArr, Class<T> arrClass) {
        T[] arr = (T[]) Array.newInstance(arrClass, jsonArr.size());

        for (int i = 0; i < arr.length; i++) 
            arr[i] = (T) jsonArr.get(i);
        
        return arr;
    }

    @SuppressWarnings("unchecked")
    private JSONArray arrayToJSONArray(Object[] arr) {
        JSONArray jsonArr = new JSONArray();

        for (Object v : arr)
            jsonArr.add(v);
        
        return jsonArr;
    }

    /**Reads the JSON file located at {@code this.path} and sets {@code this.Data} to a {@code JSONData} object created from the JSON file.
     * 
     * @return The created JSONData object
     */
    public JSONData ReadJSON() {
        String[] projects = null;
        String hub_version = null;
        String editor_version = null;

        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(new File(path));

            JSONObject baseObject = (JSONObject) parser.parse(reader);

            projects = jsonArrayToArray((JSONArray) baseObject.get("projects"), String.class);
            hub_version = (String) baseObject.get("hub_version");
            editor_version = (String) baseObject.get("editor_version");
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.Data = new JSONData(projects, hub_version, editor_version);
        return this.Data;
    }

    /**Updates the JSON file located at {@code this.path} to contain the given arguments
     * 
     */
    @SuppressWarnings("unchecked")
    public void UpdateJSON(String[] projects, String hubVersion, String editorVersion) {
        JSONObject baseObject = new JSONObject();
        baseObject.put("projects", arrayToJSONArray(projects));
        baseObject.put("hub_version", hubVersion);
        baseObject.put("editor_version", editorVersion);

        try (FileWriter writer = new FileWriter(path)){
            writer.write(baseObject.toJSONString());
            writer.flush();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public class JSONData {
        public final String[] projects;
        public final String hub_version;
        public final String editor_version;

        public JSONData(String[] projectArr, String hubVersion, String editorVersion) {
            this.projects = projectArr;
            this.hub_version = hubVersion;
            this.editor_version = editorVersion;
        }
    }
}
