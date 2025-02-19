package JGamePackage.JGame.Classes.UI;

import java.awt.Graphics2D;

import JGamePackage.JGame.Classes.Rendering.RenderUtil;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class UIScrollFrame extends UIBase {

    public Vector2 ScrollOffset = Vector2.zero;

    private boolean mouseInBounds = false;

    public UIScrollFrame() {
        super();
        this.ClipsDescendants = true;

        this.MouseEnter.Connect(()-> {
            mouseInBounds = true;
        });

        this.MouseLeave.Connect(()-> {
            mouseInBounds = false;
        });

        game.InputService.OnMouseScroll.Connect(amount -> {
            if (!mouseInBounds) return;
            ScrollOffset = new Vector2(0, ScrollOffset.Y + amount);
        });
    }

    @Override
    public void render(Graphics2D graphics) {
        Vector2 renderPos = GetAbsolutePosition();
        Vector2 renderSize = GetAbsoluteSize();

        RenderUtil.drawRectangle(this, renderSize, renderPos, GetBackgroundRenderColor());
    }
    
    @Override
    public UIFrame Clone() {
        UIFrame clone = cloneWithoutChildren();
        this.cloneHierarchyToNewParent(clone);
        return clone;
    }

    @Override
    protected UIFrame cloneWithoutChildren() {
        UIFrame frame = new UIFrame();
        this.cloneHelper(frame);
        return frame;
    }
}
