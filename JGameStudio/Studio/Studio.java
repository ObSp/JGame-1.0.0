package JGameStudio.Studio;

import javax.swing.ImageIcon;

import com.formdev.flatlaf.FlatDarculaLaf;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Types.StartParams.StartParams;
import JGameStudio.StudioGlobals;
import JGameStudio.StudioUtil;
import JGameStudio.ProjectHandler.ProjectHandler;
import JGameStudio.ProjectHandler.ProjectHandler.ProjectData;
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

    private void initComponents() {
        displayWindow = new DisplayWindow();
        sideBar = new Sidebar();
        topBar = new Topbar();

        displayWindow.SetParent(game.UINode);
        sideBar.SetParent(game.UINode);
        topBar.SetParent(game.UINode);
    }

    public Studio(String[] args) {
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
        game.Services.WindowService.SetWindowIcon(new ImageIcon("JGameStudio/Assets/Logo.png").getImage());

        game.Services.TimeService.WaitSeconds(1);

        initComponents();
    }

    public static void main(String[] args) {
        new Studio(args);
    }
}
