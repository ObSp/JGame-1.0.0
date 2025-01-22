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

    @Override
    public UITextButton Clone() {
        UITextButton clone = cloneWithoutChildren();
        this.cloneHierarchyToNewParent(clone);
        return clone;
    }

    @Override
    protected UITextButton cloneWithoutChildren() {
        UITextButton text = new UITextButton();
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
        text.CustomFont = this.CustomFont;
        text.HoverColor = this.HoverColor;
        text.HoverEffectsEnabled = this.HoverEffectsEnabled;
        return text;
    }
}
