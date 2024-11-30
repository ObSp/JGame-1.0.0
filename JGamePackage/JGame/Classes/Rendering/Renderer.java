package JGamePackage.JGame.Classes.Rendering;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.World.WorldBase;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class Renderer extends JPanel {
    private JGame game;

    public Renderer(JGame game) {
        this.game = game;
    }

    private void sortRenderableArrayByZIndex(Renderable[] arr) {
        int size = arr.length;
        for (int i = 0; i < size - 1; i++) {
            if (arr[i] == null)
                continue;

            int mindex = i;
            for (int j = i + 1; j < size; j++) {
                if (arr[j] == null)
                    continue;
                if (arr[j].ZIndex < arr[mindex].ZIndex)
                    mindex = j;
            }

            Renderable itemAtIndex = arr[i];
            arr[i] = arr[mindex];
            arr[mindex] = itemAtIndex;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        RenderWorld(g2d, game.WorldNode.GetDescendants());
        RenderUI(g2d, game.UINode.GetDescendants());
    }

    private void renderUIRecursive(UIBase[] curChildren, Graphics2D g) {
        sortRenderableArrayByZIndex(curChildren);
        for (UIBase child : curChildren) {
            if (!child.Visible) continue;
            child.render(g);
        }

        for (UIBase child : curChildren) {
            if (!child.Visible) continue;
            renderUIRecursive(child.GetChildrenOfClass(UIBase.class), g);
        }
    }

    public void RenderUI(Graphics2D g, Instance[] UI) {
        renderUIRecursive(game.UINode.GetChildrenOfClass(UIBase.class), g);
    }

    private void renderWorldRecursive(WorldBase[] curChildren, Graphics2D g) {
        //sort by ZIndex
        sortRenderableArrayByZIndex(curChildren);
        for (WorldBase child : curChildren) {
            if (!child.Visible) continue;
            child.render(g);
        }

        for (WorldBase child : curChildren) {
            if (!child.Visible) continue;
            renderWorldRecursive(child.GetChildrenOfClass(WorldBase.class), g);
        }
    } 

    public void RenderWorld(Graphics2D g, Instance[] World) {
        g.setColor(game.Services.WindowService.BackgroundColor);
        g.fillRect(0, 0, game.Services.WindowService.GetScreenWidth(), game.Services.WindowService.GetScreenHeight());

        Vector2 cameraPos = game.Services.WindowService.GetScreenSize().divide(2);

        g.rotate(game.Camera.Rotation, cameraPos.X, cameraPos.Y);

        renderWorldRecursive(game.WorldNode.GetChildrenOfClass(WorldBase.class), g);

        g.rotate(-game.Camera.Rotation, cameraPos.X, cameraPos.Y);
    }
}