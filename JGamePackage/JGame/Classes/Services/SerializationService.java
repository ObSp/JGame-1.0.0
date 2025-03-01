package JGamePackage.JGame.Classes.Services;

import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.Abstracts.AbstractImage;
import JGamePackage.lib.JSONSimple.JSONArray;
import JGamePackage.lib.JSONSimple.JSONObject;
import JGamePackage.lib.JSONSimple.parser.JSONParser;
import JGamePackage.lib.Signal.AbstractSignalInstance;


public class SerializationService extends Service {
    private HashMap<Class<? extends Instance>, Instance> cachedDefaultComparisonInstances = new HashMap<>();

    private Instance findOrCreateDefaultComparisonInstance(Class<? extends Instance> clazz) {
        if (cachedDefaultComparisonInstances.containsKey(clazz)) {
            return cachedDefaultComparisonInstances.get(clazz);
        }

        try {
            Instance inst = clazz.getDeclaredConstructor().newInstance();
            cachedDefaultComparisonInstances.put(clazz, inst);
            return inst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public JSONObject InstanceToJSONObject(Instance inst) throws IllegalArgumentException, IllegalAccessException {
        Instance originalInst = findOrCreateDefaultComparisonInstance(inst.getClass());
        if (originalInst == null) return null;

        JSONObject obj = new JSONObject();

        for (Field field : inst.getClass().getFields()) {
            Object fieldValue = field.get(inst);

            //skip transient fields, allowing classes to define fields that shouldn't be serialized
            if (Modifier.isTransient(field.getModifiers()) || Objects.equals(field.get(originalInst), fieldValue)) continue;

            if (fieldValue instanceof AbstractSignalInstance) continue; //this value hasn't changed and therefore doesn't need to be serialized

            if (fieldValue instanceof Color) {
                fieldValue = ((Color) fieldValue).getRGB();
            }

            obj.put(field.getName(), fieldValue);
        }

                    
        //checking for class-specific fields that have getters/setters instead of fields
        if (inst instanceof AbstractImage) {
            obj.put("ImagePath", ((AbstractImage) inst).GetImagePath());
        }

        obj.put("class", inst.getClass().getName());

        return obj;
    }
    
    @SuppressWarnings("unchecked")
    public JSONArray InstanceArrayToJSONArray(Instance[] instances) {
        JSONArray arr = new JSONArray();

        HashMap<Instance, Integer> instanceIndexMap = new HashMap<>();
        for (int i = 0; i < instances.length; i++) {
            instanceIndexMap.put(instances[i], i);
        }
        
        for (Instance inst : instances) {
            JSONObject obj;
            try {
                obj = InstanceToJSONObject(inst);
                if (obj == null) continue;
                obj.put("IdentifierIndex", instanceIndexMap.get(inst));

                Integer parentIdentifierIndex = instanceIndexMap.get(inst.GetParent());

                obj.put("ParentIdentifierIndex", parentIdentifierIndex != null ? parentIdentifierIndex : -1);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
            arr.add(obj);
        }

        return arr;
    }

    public Instance[] JSONArrayToInstanceArray(JSONArray arr) {
        Instance[] instances = new Instance[arr.size()];

        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);
            Instance inst = JSONObjectToInstance(obj);
            instances[i] = inst;
        }

        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);
            Instance inst = instances[i];

            if ((int) obj.get("ParentIdentifierIndex") != -1) {
                inst.SetParent(instances[(int) obj.get("ParentIdentifierIndex")]);
            }
        }

        return instances;
    }

    public <T extends Instance> T JSONObjectToInstance(JSONObject obj) {
        try {
            Class<?> clazz = Class.forName((String) obj.get("class"));
            @SuppressWarnings("unchecked")
            T inst = (T) clazz.getDeclaredConstructor().newInstance();

            for (Object key : obj.keySet()) {
                if (key.equals("class") || !doesObjectContainField(inst, (String) key)) continue;

                Field field = inst.getClass().getField((String) key);
                field.set(inst, obj.get(key));
            }

            if (inst instanceof AbstractImage) {
                ((AbstractImage) inst).SetImage((String) obj.get("ImagePath"));
            }

            return inst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //IO methods
    public void WriteInstanceArrayToFile(Instance[] instances, String path) {
        JSONArray arr = InstanceArrayToJSONArray(instances);
        
        try (FileWriter writer = new FileWriter(path)){
            writer.write(arr.toJSONString());
            writer.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Instance[] ReadInstanceArrayFromFile(String path) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray arr = (JSONArray) parser.parse(new FileReader(path));
            return JSONArrayToInstanceArray(arr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean doesObjectContainField(Object object, String fieldName) {
        return Arrays.stream(object.getClass().getFields())
                .anyMatch(f -> f.getName().equals(fieldName));
    }
}
