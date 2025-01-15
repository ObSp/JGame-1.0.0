package JGameStudio;

import javax.swing.JFrame;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGameStudio.Pages.Welcome;

public class Studio {
    static JGame game;

    //PAGE NODES
    static UIBase welcomeNode;
    public static void main(String[] args) {
        game = new JGame();

        StudioUtil.game = game;

        welcomeNode = Welcome.createWelcomePageNode();
        welcomeNode.SetParent(game.UINode);
    }
}
