package JGameStudio.Studio.Classes.Modes;

import JGamePackage.JGame.JGame;
import JGameStudio.Studio.Classes.Modes.Drag.Drag;
public class ModeHandler {
    private Mode currentMode;

    public final Drag dragMode = new Drag();

    @SuppressWarnings("unused")
    private JGame game = JGame.CurrentGame;

    public ModeHandler() {
        currentMode = null;
        setMode(dragMode);
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
