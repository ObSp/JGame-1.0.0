package JGameStudio.Studio.Modules;

import java.util.ArrayList;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.lib.Signal.Signal;

public class Selection {
    private static ArrayList<Instance> selection = new ArrayList<>();

    public static final Signal<Instance> InstanceDeselected = new Signal<>();

    public static Instance[] get() {
        Instance[] list = new Instance[selection.size()];

        for (int i = 0; i < list.length; i++) {
            list[i] = selection.get(i);
        }

        return list;
    }

    public static Instance getFirst() {
        return selection.size() > 0 ? selection.getFirst() : null;
    }

    public static void add(Instance e) {
        selection.add(e);
    }

    public static void remove(Instance e) {
        selection.remove(e);
        InstanceDeselected.Fire(e);
    }

    public static void set(Instance e) {
        clear();
        selection.add(e);
    }

    public static void clear() {
        for (Instance e : get()) {
            InstanceDeselected.Fire(e);
        }

        selection.clear();
    }

    public static boolean contains(Instance e) {
        return selection.contains(e);
    }
}
