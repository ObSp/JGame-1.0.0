package JGamePackage.JGame.Classes.UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;

public class UIButton extends UIFrame {
    public Color HoverColor = Color.gray;

    private boolean isHovering = false;

    public UIButton() {
        super();

        Container pane =  this.game.GetWindow().getContentPane();
        
        this.MouseEnter.Connect(()-> {
            this.isHovering = true;
            pane.setCursor(new Cursor(Cursor.HAND_CURSOR));
        });

        this.MouseLeave.Connect(()-> {
            this.isHovering = false;
            pane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        });

    }

    @Override
    public Color GetBackgroundRenderColor() {
        Color curColor = isHovering ? HoverColor : BackgroundColor;
        return new Color(curColor.getRed(), curColor.getGreen(), curColor.getBlue(), (int) (255*(1-BackgroundTransparency)));
    }
}
