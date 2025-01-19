package JGamePackage.JGame.Classes.UI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;

import JGamePackage.JGame.Classes.Rendering.RenderUtil;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class UITextInput extends UIText {
    public String PlaceholderText = "Type Here";
    public Color PlaceholderColor = Color.gray;

    public boolean ClearTextOnFocus = true;

    private boolean showInTextCursor = true;    

    public UITextInput() {
        this.Mouse1Down.Connect(()-> {
            if (ClearTextOnFocus) Text = "";

            game.Services.InputService.SetFocusedUITextInput(this);
        });

        this.MouseEnter.Connect(()-> {
            game.Services.WindowService.SetMouseCursor(Cursor.TEXT_CURSOR);
        });

        this.MouseLeave.Connect(()-> {
            game.Services.WindowService.SetMouseCursor(Cursor.DEFAULT_CURSOR);
        });
    }

    @Override
    public void render(Graphics2D graphics) {
        Vector2 renderSize = GetAbsoluteSize();
        Vector2 renderPos = GetAbsolutePosition();

        if (!game.Camera.AreBoundsInCameraBounds(renderSize, renderPos)) return;

        boolean focused = game.Services.InputService.GetFocusedUITextInput() == this;

        //render background
        if (BackgroundTransparency < 1) {
            RenderUtil.drawRectangle(this, renderSize, renderPos, GetBackgroundRenderColor());
        }

        if (game.Services.TimeService.GetElapsedTicks() % 20 == 0) showInTextCursor = !showInTextCursor;

        String renderText = Text;
        Color renderColor = GetTextRenderColor();

        if ((Text == null || Text.length() == 0) && !focused) {
            renderText = PlaceholderText;
            renderColor = new Color(PlaceholderColor.getRed(), PlaceholderColor.getGreen(), PlaceholderColor.getBlue(), renderColor.getAlpha());
        }

        RenderUtil.drawText(renderText, renderSize, renderPos, renderColor, FontSize, FontStyle, FontName, CustomFont, TextScaled, HorizontalTextAlignment, VerticalTextAlignment, focused && showInTextCursor ? "I" : "");
    }

    @Override
    public UITextInput Clone() {
        UITextInput clone = cloneWithoutChildren();
        this.cloneHierarchyToNewParent(clone);
        return clone;
    }

    @Override
    protected UITextInput cloneWithoutChildren() {
        UITextInput text = new UITextInput();
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
        text.PlaceholderText = this.PlaceholderText;
        text.PlaceholderColor = this.PlaceholderColor;
        text.ClearTextOnFocus = this.ClearTextOnFocus;
        return text;
    }
    
}
