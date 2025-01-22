package JGamePackage.JGame.Classes.Rendering;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.UI.Modifiers.UIBorder;
import JGamePackage.JGame.Classes.UI.Modifiers.UICorner;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.Constants.Constants.HorizontalTextAlignment;
import JGamePackage.JGame.Types.Constants.Constants.VerticalTextAlignment;
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
        UIBorder borderEffect = corner.GetParent().GetChildWhichIsA(UIBorder.class);

        double radius = corner.Radius;
        radius *= size.getAxisFromVector2Axis(corner.RelativeTo);

        if (borderEffect != null) {
            g.setColor(borderEffect.BorderColor);
            g.fillRoundRect((int)( pos.X - borderEffect.Width), (int) (pos.Y - borderEffect.Width), (int) (size.X + borderEffect.Width*2), (int) (size.Y + borderEffect.Width*2), (int) radius, (int) radius);
        }

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
        double radius = corner.Radius;
        radius *= renderSize.getAxisFromVector2Axis(corner.RelativeTo);

        int w = (int) renderSize.X;
        int h = (int) renderSize.Y;

        int x = (int) renderPos.X;
        int y = (int) renderPos.Y;

        
        Shape rounded = new RoundRectangle2D.Double(x, y, w, h, radius, radius);

        g.setColor(new Color(0,0,0,0));
        g.fillRect(x, y, w, h);
        g.setClip(rounded);

        g.drawImage(image, x, y, w, h, null);

        g.setClip(null);

        /**g.setColor(new Color(0,0,0,0));

        Shape clipShape = new RoundRectangle2D.Double(x, y, w, h, 20, 20);
        //WORKS BUT LAGGY
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(x, y, w, h);
        g.setComposite(AlphaComposite.SrcOver);

        g.setPaint(new TexturePaint(image, new Rectangle2D.Float(x, y, w, h)));

        g.fill(clipShape);*/

        //g.drawImage(image, (int) renderPos.X, (int) renderPos.Y, (int) renderSize.X, (int) renderSize.Y, null);
    }

    public static void drawText(String text, Vector2 renderSize, Vector2 renderPos, Color color, int fontSize, int fontStyle, String fontName, Font customFont, boolean textScaled, HorizontalTextAlignment xAlign, VerticalTextAlignment yAlign, String add) {
        int centerX = (int) (renderPos.X + (renderSize.X/2));
        int centerY = (int) (renderPos.Y + (renderSize.Y/2));
        
        Font font;
        int fontRenderSize = textScaled ? (int) (renderSize.Y * 1.3) : fontSize;
        font = customFont == null ? new Font(fontName, fontStyle, fontRenderSize) : customFont.deriveFont(fontStyle, fontRenderSize);

        g.setFont(font);

        FontMetrics fm = g.getFontMetrics();

        int pixelHeight = (int) Math.round((double) font.getSize()*.75);
        int pixelWidth = fm.stringWidth(text);

        int xStringPos;
        int yStringPos;

        if (xAlign == Constants.HorizontalTextAlignment.Left) {
            xStringPos = (int) renderPos.X;
        } else if (xAlign == Constants.HorizontalTextAlignment.Center) {
            xStringPos = (int) (centerX-pixelWidth/2);
        } else { //right alignment
            xStringPos = (int) (renderPos.X + renderSize.X - pixelWidth);
        }

        if (yAlign == Constants.VerticalTextAlignment.Top) {
            yStringPos = (int) (renderPos.Y + pixelHeight*2.2);
        } else if (yAlign == Constants.VerticalTextAlignment.Center) {
            yStringPos = (int) (centerY + pixelHeight/2);
        } else { //bottom alignment
            yStringPos = (int) (renderPos.Y + renderSize.Y);
        }

        g.setColor(color);
        g.drawString(text + add, xStringPos, yStringPos);
    }

    public static void drawText(String text, Vector2 renderSize, Vector2 renderPos, Color color, int fontSize, int fontStyle, String fontName, Font customFont, boolean textScaled, HorizontalTextAlignment xAlign, VerticalTextAlignment yAlign) {
        drawText(text, renderSize, renderPos, color, fontSize, fontStyle, fontName, customFont, textScaled, xAlign, yAlign, "");
    }
}
