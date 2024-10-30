package JGamePackage.JGame.Classes.Rendering;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.World.WorldBase;

public class Renderer extends JPanel {
    private JGame game;

    public Renderer(JGame game) {
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderWorld(g2d, game.WorldNode.GetDescendants());
        RenderUI(g2d, game.UINode.GetDescendants());
    }

    public void RenderUI(Graphics2D g, Instance[] UI) {

    }

    public void RenderWorld(Graphics2D g, Instance[] World) {
        g.setColor(game.Services.WindowService.BackgroundColor);
        g.fillRect(0, 0, game.Services.WindowService.GetScreenWidth(), game.Services.WindowService.GetScreenHeight());
    }
}