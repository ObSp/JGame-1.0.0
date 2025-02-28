package JGamePackage.JGame.Classes.Services;

import java.lang.reflect.Field;
import java.util.HashMap;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.lib.JSONSimple.JSONArray;
import JGamePackage.lib.JSONSimple.JSONObject;
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

        JSONObject obj = new JSONObject();

        for (Field field : inst.getClass().getFields()) {
            Object fieldValue = field.get(inst);

            if (fieldValue instanceof AbstractSignalInstance || field.get(originalInst).equals(fieldValue)) continue; //this value hasn't changed and therefore doesn't need to be serialized

            obj.put(field.getName(), field.get(inst));
            
            //checking for class-specific fields that have getters/setters instead of fields
            
        }

        return obj;
    }
    
    @SuppressWarnings("unchecked")
    public JSONArray InstanceArrayToJSONArray(Instance[] instances) {
        JSONArray arr = new JSONArray();
        
        for (Instance inst : instances) {
            JSONObject obj;
            try {
                obj = InstanceToJSONObject(inst);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
            arr.add(obj);
        }

        return arr;
    }
}
