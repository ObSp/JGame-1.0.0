package JGamePackage.JGame.Classes.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class UIText extends UIBase{

    public String Text = "Text here";

    public boolean TextScaled = false;
    
    public Color TextColor = Color.black;

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

        Font font;
        
        if (TextScaled) {
            font = new Font(this.FontName, this.FontStyle, (int) (renderSize.Y*1.3));
        } else {
            font = new Font(this.FontName, this.FontStyle, this.FontSize);
        }
    }
}
