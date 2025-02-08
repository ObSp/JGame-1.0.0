package JGamePackage.JGame.Classes.Scripts;

import java.lang.reflect.InvocationTargetException;

public class Script extends ScriptBase {

    @Override
    public Script Clone() {
        Script newScript = cloneWithoutChildren();
        this.cloneHierarchyToNewParent(newScript);
        return newScript;
    }

    @Override
    protected Script cloneWithoutChildren() {
        Script newScript;

        try {
            newScript = this.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            ErrorCloneFailed.Throw(new String[] {this.getClass().getSimpleName(), e.getMessage()});
            return null;
        }

        return newScript;
    }
    
    
}
