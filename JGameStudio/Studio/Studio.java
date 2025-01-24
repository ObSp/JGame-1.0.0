package JGameStudio.Studio;

import com.formdev.flatlaf.FlatDarculaLaf;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Types.StartParams.StartParams;
import JGameStudio.StudioGlobals;
import JGameStudio.StudioUtil;
import JGameStudio.Studio.Components.DisplayWindow;
import JGameStudio.Studio.Components.Sidebar;
import JGameStudio.Studio.Components.Topbar;
import JGameStudio.Studio.Pages.Init;

public class Studio {
    JGame game;

    private DisplayWindow displayWindow;
    private Sidebar sideBar;
    private Topbar topBar;

    private void initComponents() {
        displayWindow = new DisplayWindow();
        sideBar = new Sidebar();
        topBar = new Topbar();

        displayWindow.SetParent(game.UINode);
        sideBar.SetParent(game.UINode);
    }

    public Studio() {
        FlatDarculaLaf.setup();

        game = new JGame(new StartParams(false));

        StudioGlobals.construct();
        Init.showLoadingFrame(game).Wait();

        StudioUtil.game = game;
        StudioGlobals.construct();
        
        game.Services.WindowService.BackgroundColor = StudioGlobals.BackgroundColor;

        game.Services.TimeService.WaitSeconds(1);

        initComponents();
    }

    public static void main(String[] args) {
        new Studio();
    }
}
