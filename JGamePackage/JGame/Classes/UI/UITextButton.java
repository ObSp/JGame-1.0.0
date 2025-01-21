package JGamePackage.JGame.Classes.UI;

import java.awt.Color;
import java.awt.Cursor;

public class UITextButton extends UIText {
    public Color HoverColor = Color.gray;
    public boolean HoverEffectsEnabled = true;

    private boolean isHovering = false;

    public UITextButton() {
        super();
        
        this.MouseEnter.Connect(()-> {
            this.isHovering = true;
            game.Services.WindowService.SetMouseCursor(Cursor.HAND_CURSOR);
        });

        this.MouseLeave.Connect(()-> {
            this.isHovering = false;
            if (game.Services.InputService.GetMouseUITarget() instanceof UITextButton || game.Services.InputService.GetMouseUITarget() instanceof UIButton) return;
            game.Services.WindowService.SetMouseCursor(Cursor.DEFAULT_CURSOR);
        });

    }

    @Override
    public Color GetBackgroundRenderColor() {
        Color curColor = (isHovering && HoverEffectsEnabled) ? HoverColor : BackgroundColor;
        return new Color(curColor.getRed(), curColor.getGreen(), curColor.getBlue(), (int) (255*(1-BackgroundTransparency)));
    }
}
