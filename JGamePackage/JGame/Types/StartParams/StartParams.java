package JGamePackage.JGame.Types.StartParams;

public class StartParams {
    public boolean initializeWindow = true;
    public boolean loadScripts = true;

    public StartParams() {}

    public StartParams(boolean initWindow) {
        initializeWindow = initWindow;
    }

    public StartParams(boolean initWindow, boolean loadScripts) {
        initializeWindow = initWindow;
        this.loadScripts = loadScripts;
    }
}
