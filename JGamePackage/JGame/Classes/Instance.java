package JGamePackage.JGame.Classes;

import java.util.ArrayList;

import JGamePackage.lib.CustomError.CustomError;
import JGamePackage.lib.Signal.Signal;

public class Instance {
    //--CUSTOM ERRORS--//
    private static CustomError ErrorAlreadyDestroyed = new CustomError("Cannot destroy an already destroyed instance.", CustomError.WARNING, 1);
    private static CustomError ErrorParentLocked = new CustomError("Unable to set parent property; the property is locked.", CustomError.WARNING, 1);

    /**A non-unique identifier that can be used to access this instance through its parent.
     * 
     */
    public String Name = "Instance";


    //--SIGNALS--//

    /**Fires when an instance is parented to this instance
     * 
     */
    public Signal<Instance> ChildAdded = new Signal<>();

    /**Fires when a child of this instance is parented to something else
     * 
     */
    public Signal<Instance> ChildRemoved = new Signal<>();

    /**Fires when an instance is parented to a descendant of this instance
     * 
     */
    public Signal<Instance> DescendantAdded = new Signal<>();

    /**Fires when a descendant of this instance is parented to an instance that's not an ancestor of this instance
     * 
     */
    public Signal<Instance> DescendantRemoved = new Signal<>();

    //--HIERARCHY VARS--//
    protected ArrayList<Instance> children = new ArrayList<>();
    protected Instance parent;
    protected boolean parentLocked = false;

    //--UTIL--//
    private Instance[] instanceListToArray(ArrayList<Instance> list){
        Instance[] array = new Instance[list.size()];

        for (int i = 0; i < array.length; i++)
            array[i] = list.get(i);

        return array;
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

    public Instance GetChild(String name){
        for (Instance child : GetChildren()) {
            if (child.Name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    protected void AddChild(Instance child) {
        children.add(child);
        ChildAdded.Fire(child);
        DescendantAdded.Fire(child);

        for (Instance ancestor : GetAncestors()) {
            ancestor.DescendantAdded.Fire(child);
        }
    }

    protected void RemoveChild(Instance child) {
        children.remove(child);
        ChildRemoved.Fire(child);
        DescendantRemoved.Fire(child);

        for (Instance ancestor : GetAncestors()) {
            ancestor.DescendantRemoved.Fire(child);
        }
    }

    public void SetParent(Instance parent) {
        if (parentLocked) {
            ErrorParentLocked.Throw();
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

    //--OVERRIDES--//
    @Override
    public String toString(){
        return this.Name;
    }
    
}
