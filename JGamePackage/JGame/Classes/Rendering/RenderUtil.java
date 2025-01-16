package JGamePackage.JGame.Classes.Rendering;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.UI.Modifiers.UICorner;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class RenderUtil {
    public static Graphics2D g;

    public static void drawRectangle(Instance inst, Vector2 renderSize, Vector2 renderPos, Color color) {
        UICorner cornerEffect = inst.GetChildWhichIsA(UICorner.class);

        if (cornerEffect != null) {
            RenderUtil.drawRoundedRect(renderSize, renderPos, color, cornerEffect);
        } else {
            RenderUtil.drawSharpRect(renderSize, renderPos, color);
        }
    }

    public static void drawRoundedRect(Vector2 size, Vector2 pos, Color color, UICorner corner) {
        double radius = corner.Radius;
        radius *= size.getAxisFromVector2Axis(corner.RelativeTo);

        g.setColor(color);

        g.fillRoundRect((int) pos.X, (int) pos.Y, (int) size.X, (int) size.Y, (int) radius, (int) radius);
    }

    public static void drawSharpRect(Vector2 size, Vector2 pos, Color color) {
        g.setColor(color);
        g.fillRect((int) pos.X, (int) pos.Y, (int) size.X, (int) size.Y);
    }

    public static void drawImage(Instance inst, Vector2 renderSize, Vector2 renderPos, BufferedImage image) {
        UICorner cornerEffect = inst.GetChildWhichIsA(UICorner.class);

        if (cornerEffect != null) {
            RenderUtil.drawRoundImage(inst, renderSize, renderPos, image, cornerEffect);
        } else {
            RenderUtil.drawSharpImage(inst, renderSize, renderPos, image);
        }
    }

    public static void drawSharpImage(Instance inst, Vector2 renderSize, Vector2 renderPos, BufferedImage image) {
        g.drawImage(image, (int) renderPos.X, (int) renderPos.Y, (int) renderSize.X, (int) renderSize.Y, null);
    }

    public static void drawRoundImage(Instance inst, Vector2 renderSize, Vector2 renderPos, BufferedImage image, UICorner corner) {
        g.drawImage(image, (int) renderPos.X, (int) renderPos.Y, (int) renderSize.X, (int) renderSize.Y, null);
    }
}
