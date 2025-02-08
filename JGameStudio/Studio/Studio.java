package JGameStudio.Studio;

import java.io.IOException;

import com.formdev.flatlaf.FlatDarculaLaf;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Types.StartParams.StartParams;
import JGameStudio.StudioGlobals;
import JGameStudio.StudioUtil;
import JGameStudio.ProjectHandler.ProjectHandler;
import JGameStudio.ProjectHandler.ProjectHandler.ProjectData;
import JGameStudio.Studio.Classes.Modes.ModeHandler;
import JGameStudio.Studio.Classes.MouseBehavior.MouseBehaviorHandler;
import JGameStudio.Studio.Classes.WorldNavigation.WorldNavi;
import JGameStudio.Studio.Components.DisplayWindow;
import JGameStudio.Studio.Components.Sidebar;
import JGameStudio.Studio.Components.Topbar;
import JGameStudio.Studio.Pages.Init;

public class Studio {
    JGame game;
    
    private String path;

    private ProjectData projectData;

    private DisplayWindow displayWindow;
    private Sidebar sideBar;
    private Topbar topBar;

    private WorldNavi worldNavi;
    private MouseBehaviorHandler mouseHandler;

    private ModeHandler modeHandler;

    private void initComponents() {
        displayWindow = new DisplayWindow();
        sideBar = new Sidebar();
        topBar = new Topbar();

        displayWindow.SetParent(game.UINode);
        sideBar.SetParent(game.UINode);
        topBar.SetParent(game.UINode);
    }

    public Studio(String[] args) throws IOException {
        if (args.length > 0)
            path = args[0];

        FlatDarculaLaf.setup();

        game = new JGame(new StartParams(false));

        if (path != null) {
            projectData = ProjectHandler.ReadProjectDir(path);
            game.Services.WindowService.SetWindowTitle(projectData.name() + " - JGame Studio");
        }

        StudioGlobals.construct();
        Init.showLoadingFrame(game).Wait();

        StudioUtil.game = game;
        StudioGlobals.construct();
        
        game.Services.WindowService.BackgroundColor = StudioGlobals.BackgroundColor.darker();
        game.Services.WindowService.SetWindowIcon("JGameStudio\\Assets\\Logo.png");

        game.Services.TimeService.WaitSeconds(1);

        initComponents();

        worldNavi = new WorldNavi();
        mouseHandler = new MouseBehaviorHandler();

        modeHandler = new ModeHandler();
    }

    public static void main(String[] args) throws IOException {
        new Studio(args);
    }
}
