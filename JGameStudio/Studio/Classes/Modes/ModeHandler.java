package JGameStudio.Studio.Classes.Modes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Modifiers.BorderEffect;
import JGamePackage.JGame.Classes.Rendering.Renderable;
import JGameStudio.StudioGlobals;
import JGameStudio.Studio.Classes.Modes.Drag.Drag;
import JGameStudio.Studio.Instances.SelectionBorder;
import JGameStudio.Studio.Modules.Selection;

public class ModeHandler {
    private Mode currentMode;

    private Drag dragMode = new Drag();

    @SuppressWarnings("unused")
    private JGame game = JGame.CurrentGame;

    public ModeHandler() {
        currentMode = null;
        setMode(dragMode);

        Selection.InstanceSelected.Connect((instance) -> {
            if (!(instance instanceof Renderable)) return;

            new SelectionBorder().SetParent(instance);
        });

        Selection.InstanceDeselected.Connect((instance) -> {
            if (!(instance instanceof Renderable)) return;

            BorderEffect border = instance.GetChildOfClass(BorderEffect.class);
            if (border != null) {
                border.Destroy();
            }
        });
    }

    public void setMode(Mode mode) {
        if (currentMode != null) {
            currentMode.Deselect();
        }

        currentMode = mode;
        currentMode.Select();
    }

    public Mode getMode() {
        return currentMode;
    }
}
