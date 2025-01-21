package JGamePackage.JGame.Classes.UI;

import java.awt.Color;
import java.awt.Cursor;

public class UIButton extends UIFrame {
    public Color HoverColor = Color.gray;
    public boolean HoverEffectsEnabled = true;

    private boolean isHovering = false;

    public UIButton() {
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

    @Override
    public UIButton Clone() {
        UIButton clone = cloneWithoutChildren();
        this.cloneHierarchyToNewParent(clone);
        return clone;
    }

    @Override
    protected UIButton cloneWithoutChildren() {
        UIButton frame = new UIButton();
        this.cloneHelper(frame);
        frame.HoverColor = this.HoverColor;
        frame.HoverEffectsEnabled = this.HoverEffectsEnabled;
        return frame;
    }
}
