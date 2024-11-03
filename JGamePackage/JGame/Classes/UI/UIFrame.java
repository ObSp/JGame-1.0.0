package JGamePackage.JGame.Classes.UI;

import java.awt.Graphics2D;

import JGamePackage.JGame.Classes.UI.Modifiers.UICorner;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class UIFrame extends UIBase {

    @Override
    public void render(Graphics2D graphics) {
        Vector2 renderPos = GetAbsolutePosition();
        Vector2 renderSize = GetAbsoluteSize();
        
        graphics.setColor(GetBackgroundRenderColor());
        
        
        UICorner cornerEffect = this.GetChildWhichIsA(UICorner.class);

        if (cornerEffect != null) {
            double radius = cornerEffect.Radius;
            radius *= renderSize.getAxisFromIndex(cornerEffect.RelativeTo);
            graphics.fillRoundRect((int) renderPos.X, (int) renderPos.Y, (int) renderSize.X, (int) renderSize.Y, (int) radius, (int) radius);
        } else {
            graphics.fillRect((int) renderPos.X, (int) renderPos.Y, (int) renderSize.X, (int) renderSize.Y);
        }
    }
    
}
