package JGamePackage.JGame.Classes.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class UIText extends UIBase{

    public String Text = "";

    /** Scales the text so that the text height will always be the same as {@code Size.Y}
     * 
     */
    public boolean TextScaled = false;
    
    public Color TextColor = Color.black;
    public double TextTransparency = 0;

    public int HorizontalTextAlignment = Constants.HorizontalTextAlignment.Center;

    public double VerticalTextAlignment = Constants.VerticalTextAlignment.Center;

    public int FontSize = 10;
    public String FontName = "Arial";
    public int FontStyle = Font.PLAIN;

    @Override
    public void render(Graphics2D g) {
        if (Text == null) return;

        Vector2 renderSize = GetAbsoluteSize();
        Vector2 renderPosition = GetAbsolutePosition();

        if (!game.Camera.AreBoundsInCameraBounds(renderSize, renderPosition)) return;

        int centerX = (int) (renderPosition.X + (renderSize.X/2));
        int centerY = (int) (renderPosition.Y + (renderSize.Y/2));

        //render background
        Color backgroundRenderColor = this.GetBackgroundRenderColor();
        if (backgroundRenderColor.getAlpha() > 0) {
            g.setColor(backgroundRenderColor);
            g.fillRect((int) renderPosition.X, (int) renderPosition.Y, (int) renderSize.X, (int) renderSize.Y);
        }

        Font font;
        
        if (TextScaled) {
            font = new Font(this.FontName, this.FontStyle, (int) (renderSize.Y*1.3));
        } else {
            font = new Font(this.FontName, this.FontStyle, this.FontSize);
        }

        g.setFont(font);

        FontMetrics fm = g.getFontMetrics();

        int pixelHeight = (int) Math.round((double) font.getSize()*.75);
        int pixelWidth = fm.stringWidth(Text);

        int xStringPos;
        int yStringPos;

        if (HorizontalTextAlignment == Constants.HorizontalTextAlignment.Left) {
            xStringPos = (int) renderPosition.X;
        } else if (HorizontalTextAlignment == Constants.HorizontalTextAlignment.Center) {
            xStringPos = (int) (centerX-pixelWidth/2);
        } else { //right alignment
            xStringPos = (int) (renderPosition.X + renderSize.X - pixelWidth);
        }

        if (VerticalTextAlignment == Constants.VerticalTextAlignment.Top) {
            yStringPos = (int) (renderPosition.Y + pixelHeight*2.2);
        } else if (VerticalTextAlignment == Constants.VerticalTextAlignment.Center) {
            yStringPos = (int) (centerY + pixelHeight/2);
        } else { //bottom alignment
            yStringPos = (int) (renderPosition.Y + renderSize.Y);
        }

        g.setColor(GetTextRenderColor());
        g.drawString(Text, xStringPos, yStringPos);
    }

    private Color GetTextRenderColor(){
        return new Color(TextColor.getRed(), TextColor.getGreen(), TextColor.getBlue(), (int) (255*(1-TextTransparency)));
    }
}
