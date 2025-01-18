package JGamePackage.JGame.Classes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import JGamePackage.JGame.JGame;
import JGamePackage.lib.CustomError.CustomError;
import JGamePackage.lib.Signal.Signal;
import JGamePackage.lib.Signal.SignalWrapper;

public abstract class Instance {
    //--CUSTOM ERRORS--//
    private static CustomError ErrorRecursiveParent = new CustomError("Cannot set an object's parent to the object itself.", CustomError.WARNING, "JGamePackage");
    private static CustomError ErrorAlreadyDestroyed = new CustomError("Cannot destroy an already destroyed instance.", CustomError.WARNING, "JGamePackage");
    private static CustomError ErrorParentLocked = new CustomError("Unable to set parent property; the property is locked.", CustomError.WARNING, "JGamePackage");
    private static CustomError ErrorNoJGame = new CustomError("A JGame must be running in order to create JGame instances.", CustomError.ERROR, "JGamePackage");
    private static CustomError WarningNullClone = new CustomError("The method Clone() is unimplemented for the class %s..", CustomError.WARNING, "JGamePackage");

    /**A non-unique identifier that can be used to access this instance through its parent.
     * 
     */
    public String Name;


    //--SIGNALS--//

    private Signal<Instance> childAddedSignal = new Signal<>();
    /**Fires when an instance is parented to this instance
     * 
     */
    public SignalWrapper<Instance> ChildAdded = new SignalWrapper<>(childAddedSignal);

    private Signal<Instance> childRemovedSignal = new Signal<>();
    /**Fires when a child of this instance is parented to something else
     * 
     */
    public SignalWrapper<Instance> ChildRemoved = new SignalWrapper<>(childRemovedSignal);

    private Signal<Instance> descendantAddedSignal = new Signal<>();
    /**Fires when an instance is parented to a descendant of this instance
     * 
     */
    public SignalWrapper<Instance> DescendantAdded = new SignalWrapper<>(descendantAddedSignal);

    private Signal<Instance> descendantRemovedSignal = new Signal<>();
    /**Fires when a descendant of this instance is parented to an instance that's not an ancestor of this instance
     * 
     */
    public SignalWrapper<Instance> DescendantRemoved = new SignalWrapper<>(descendantRemovedSignal);

    //--MISC--//
    protected JGame game;

    //--HIERARCHY VARS--//
    protected ArrayList<Instance> children = new ArrayList<>();
    protected Instance parent;
    protected boolean parentLocked = false;

    //--Custom Properties--//
    protected Map<String, Object> cprops = new HashMap<>();

    //--UTIL--//
    private Instance[] instanceListToArray(ArrayList<Instance> list){
        Instance[] array = new Instance[list.size()];

        for (int i = 0; i < array.length; i++)
            array[i] = list.get(i);

        return array;
    }

    //--CONSTRUCTORS--//
    public Instance() {
        JGame curGame = JGame.CurrentGame;
        if (curGame == null){
            ErrorNoJGame.Throw(); //exits program
        }
        this.game = curGame;
        this.Name = this.getClass().getSimpleName();
    }

    //--HIERARCHY METHODS--//
    public void Destroy(){
        if (parentLocked) {
            ErrorAlreadyDestroyed.Throw();
            return;
        }

        this.SetParent(null);
        this.parentLocked = true;

        for (Instance child : GetChildren())
            child.Destroy();
    }

    private void addDescendantsRecursive(Instance curInstance, ArrayList<Instance> list){
        for (Instance child : curInstance.GetChildren()) {
            list.add(child);
            addDescendantsRecursive(child, list);
        }
    }

    public Instance[] GetDescendants() {
        ArrayList<Instance> list = new ArrayList<>();

        addDescendantsRecursive(this, list);

        return instanceListToArray(list);
    }

    @SuppressWarnings("unchecked")
    private <T extends Instance> void addDescendantsOfClassRecursive(Instance curInstance, ArrayList<T> list, Class<T> classToFind){
        for (Instance child : curInstance.GetChildren()) {
            if (!(classToFind.isInstance(child))) continue;
            list.add((T) child);
            addDescendantsOfClassRecursive(child, list, classToFind);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Instance> T[] GetDescendantsOfClass(Class<T> classToFind) {
        ArrayList<T> list = new ArrayList<>();

        addDescendantsOfClassRecursive(this, list, classToFind);

        T[] arrayChildren = (T[]) Array.newInstance(classToFind, list.size());
        for (int i = 0; i < arrayChildren.length; i++) {
            arrayChildren[i] = list.get(i);
        }
        return arrayChildren;
    }

    public Instance[] GetAncestors() {
        ArrayList<Instance> ancestors = new ArrayList<>();

        Instance curParent = this.GetParent();

        while (curParent != null) {
            ancestors.add(curParent);
            curParent = curParent.GetParent();
        }

        return instanceListToArray(ancestors);
    }

    public Instance[] GetChildren() {
        return instanceListToArray(children);
    }

    @SuppressWarnings("unchecked")
    public <T extends Instance> T[] GetChildrenOfClass(Class<T> classToFind) {
        ArrayList<T> filteredChildren = new ArrayList<>();
        for (Instance child : children) {
            if (classToFind.isInstance(child))
                filteredChildren.add((T) child);
        }
        T[] arrayChildren = (T[]) Array.newInstance(classToFind, filteredChildren.size());
        for (int i = 0; i < arrayChildren.length; i++) {
            arrayChildren[i] = filteredChildren.get(i);
        }
        return arrayChildren;
    }

    public Instance GetChild(String name){
        for (Instance child : GetChildren()) {
            if (child.Name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Instance> T GetTypedChild(String name){
        for (Instance child : GetChildren()) {
            if (child.Name.equals(name)) {
                return ((T) child);
            }
        }
        return null;
    }

    /**Returns the first found child whose classname is equal to the className parameter.
     * 
     * @param className
     * @return
     */
    public Instance GetChildOfClass(String className) {
        for (Instance child : GetChildren()) {
            if (child.getClass().getSimpleName().equals(className))
                return child;
        }
        
        return null;
    }

    /**Returns the first found child whose class is related to the given class
     * 
     * @param className
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T GetChildWhichIsA(Class<T> childClass) {
        for (Instance child : GetChildren()) {
            if (childClass.isInstance(child))
                return (T) child;
        }
        return null;
    }

    public void DestroyChildren() {
        for (Instance child : GetChildren()) {
            child.Destroy();
        }
    }

    protected void AddChild(Instance child) {
        children.add(child);
        childAddedSignal.Fire(child);
        descendantAddedSignal.Fire(child);

        for (Instance ancestor : GetAncestors()) {
            ancestor.descendantAddedSignal.Fire(child);
        }
    }

    protected void RemoveChild(Instance child) {
        children.remove(child);
        childRemovedSignal.Fire(child);
        descendantRemovedSignal.Fire(child);

        for (Instance ancestor : GetAncestors()) {
            ancestor.descendantRemovedSignal.Fire(child);
        }
    }

    public void SetParent(Instance parent) {
        if (parentLocked) {
            ErrorParentLocked.Throw();
            return;
        }

        if (parent == this) {
            ErrorRecursiveParent.Throw();
            return;
        }

        if (this.parent != null) {
            this.parent.RemoveChild(this);
        }

        this.parent = parent;

        if (parent == null) return;

        this.parent.AddChild(this);
    }

    public Instance GetParent() {
        return parent;
    }

    /**Returns a duplicate of this instance while also duplicating all descendants
     * 
     * @return A clone of this instance
     */
    public abstract Instance Clone();
    protected abstract Instance cloneWithoutChildren();
    
    private static void duplicateChildren(Instance from, Instance to) {
        for (Instance child : from.GetChildren()) {
            Instance clone = child.cloneWithoutChildren();
            if (clone == null) {
                WarningNullClone.Throw(new String[] {child.getClass().getSimpleName()});
                continue;
            }
            clone.SetParent(to);
            duplicateChildren(child, clone);
        }
    }

    /**Clone all children from this Instance to the new instance, also clones CProps */
    protected void cloneHierarchyToNewParent(Instance newParent) {
        newParent.cprops = new HashMap<>(this.cprops);
        duplicateChildren(this, newParent);
    }

    public Object GetCProp(String name) {
        return cprops.get(name);
    }

    @SuppressWarnings("unchecked")
    public <T> T GetTypedCProp(String name) {
        return (T) cprops.get(name);
    }

    public void SetCProp(String name, Object value) {
        cprops.put(name, value);
    }

    //--OVERRIDES--//
    @Override
    public String toString(){
        return this.Name;
    }
    
}
