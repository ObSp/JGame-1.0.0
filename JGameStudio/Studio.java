package JGameStudio;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGameStudio.Pages.Welcome;

public class Studio {
    static JGame game = new JGame();

    //PAGE NODES
    static UIBase welcomeNode;
    public static void main(String[] args) {
        welcomeNode = Welcome.createWelcomePageNode();
        welcomeNode.SetParent(game.UINode);
    }
}
