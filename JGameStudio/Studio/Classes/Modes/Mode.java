package JGameStudio.Studio.Classes.Modes;

import JGamePackage.JGame.JGame;

public abstract class Mode {
    protected boolean selected = false;

    protected JGame game = JGame.CurrentGame;

    public void Select() {
        selected = true;
    }
    public void Deselect() {
        selected = false;
    }
}
