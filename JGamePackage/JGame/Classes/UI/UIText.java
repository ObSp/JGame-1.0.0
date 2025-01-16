package JGamePackage.JGame.Classes.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import JGamePackage.JGame.Classes.Rendering.RenderUtil;
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

    public Constants.HorizontalTextAlignment HorizontalTextAlignment = Constants.HorizontalTextAlignment.Center;

    public Constants.VerticalTextAlignment VerticalTextAlignment = Constants.VerticalTextAlignment.Center;

    public int FontSize = 10;
    public String FontName = "Arial";
    public int FontStyle = Font.PLAIN;

    public Font CustomFont = null;

    @Override
    public void render(Graphics2D graphics) {
        if (Text == null) return;

        Vector2 renderSize = GetAbsoluteSize();
        Vector2 renderPos = GetAbsolutePosition();

        if (!game.Camera.AreBoundsInCameraBounds(renderSize, renderPos)) return;

        int centerX = (int) (renderPos.X + (renderSize.X/2));
        int centerY = (int) (renderPos.Y + (renderSize.Y/2));

        //render background
        if (BackgroundTransparency < 1) {
            RenderUtil.drawRectangle(this, renderSize, renderPos, GetBackgroundRenderColor());
        }

        Font font;
        int fontRenderSize = TextScaled ? (int) (renderSize.Y * 1.3) : this.FontSize;
        font = this.CustomFont == null ? new Font(this.FontName, this.FontStyle, fontRenderSize) : this.CustomFont.deriveFont(this.FontStyle, fontRenderSize);

        graphics.setFont(font);

        FontMetrics fm = graphics.getFontMetrics();

        int pixelHeight = (int) Math.round((double) font.getSize()*.75);
        int pixelWidth = fm.stringWidth(Text);

        int xStringPos;
        int yStringPos;

        if (HorizontalTextAlignment == Constants.HorizontalTextAlignment.Left) {
            xStringPos = (int) renderPos.X;
        } else if (HorizontalTextAlignment == Constants.HorizontalTextAlignment.Center) {
            xStringPos = (int) (centerX-pixelWidth/2);
        } else { //right alignment
            xStringPos = (int) (renderPos.X + renderSize.X - pixelWidth);
        }

        if (VerticalTextAlignment == Constants.VerticalTextAlignment.Top) {
            yStringPos = (int) (renderPos.Y + pixelHeight*2.2);
        } else if (VerticalTextAlignment == Constants.VerticalTextAlignment.Center) {
            yStringPos = (int) (centerY + pixelHeight/2);
        } else { //bottom alignment
            yStringPos = (int) (renderPos.Y + renderSize.Y);
        }

        graphics.setColor(GetTextRenderColor());
        graphics.drawString(Text, xStringPos, yStringPos);
    }

    private Color GetTextRenderColor(){
        return new Color(TextColor.getRed(), TextColor.getGreen(), TextColor.getBlue(), (int) (255*(1-TextTransparency)));
    }

    @Override
    public UIText Clone() {
        UIText clone = cloneWithoutChildren();
        this.cloneHierarchyToNewParent(clone);
        return clone;
    }

    @Override
    protected UIText cloneWithoutChildren() {
        UIText text = new UIText();
        this.cloneHelper(text);
        text.Text = this.Text;
        text.TextColor = this.TextColor;
        text.TextTransparency = this.TextTransparency;
        text.HorizontalTextAlignment = this.HorizontalTextAlignment;
        text.VerticalTextAlignment = this.VerticalTextAlignment;
        text.FontSize = this.FontSize;
        text.FontName = this.FontName;
        text.FontStyle = this.FontStyle;
        text.TextScaled = this.TextScaled;
        return text;
    }
}
