package JGameStudio;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Types.StartParams.StartParams;
import JGameStudio.Pages.Init;
import JGameStudio.Pages.Welcome;

public class Studio {
    static JGame game;

    //PAGE NODES
    static UIBase welcomeNode;
    public static void main(String[] args) {
        game = new JGame(new StartParams(false));

        StudioUtil.game = game;
        StudioGlobals.construct();

        Init.showLoadingFrame(game).Wait();

        welcomeNode = Welcome.createWelcomePageNode(game);
        welcomeNode.SetParent(game.UINode);
    }
}
