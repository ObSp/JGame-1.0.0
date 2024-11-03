package JGamePackage.JGame.Classes.World;

import java.awt.Graphics2D;

import JGamePackage.JGame.Types.PointObjects.Vector2;

public class Box2D extends WorldBase {
    
    @Override
    public void render(Graphics2D graphics) {
        if (this.Transparency >= 1) return;

        Vector2 renderSize = GetRenderSize();
        Vector2 renderPosition = GetRenderPosition();

        if (!game.Camera.AreBoundsInCameraBounds(renderSize, renderPosition)) return;

        graphics.setColor(this.GetRenderColor());

        graphics.fillRect((int) renderPosition.X, (int) renderPosition.Y, (int) renderSize.X, (int) renderSize.Y);
    }}
