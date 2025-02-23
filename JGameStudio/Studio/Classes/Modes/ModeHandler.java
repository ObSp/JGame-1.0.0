package JGameStudio.Studio.Classes.Modes;

import JGamePackage.JGame.JGame;
import JGamePackage.lib.Signal.BiSignal;
import JGameStudio.Studio.Classes.Modes.ModeInstances.Drag;
import JGameStudio.Studio.Classes.Modes.ModeInstances.Move;
import JGameStudio.Studio.Classes.Modes.ModeInstances.Scale;

public class ModeHandler {
    private Mode currentMode;

    public final Drag dragMode = new Drag();
    public final Move moveMode = new Move();
    public final Scale scaleMode = new Scale();

    public final BiSignal<Mode, Mode> ModeSelected = new BiSignal<>();

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

        Mode oldMode = currentMode;

        currentMode = mode;
        currentMode.Select();

        ModeSelected.Fire(oldMode, currentMode);
    }

    public Mode getMode() {
        return currentMode;
    }
}
