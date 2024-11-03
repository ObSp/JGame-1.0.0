package JGamePackage.JGame.Classes.World;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import JGamePackage.JGame.Types.PointObjects.Vector2;

public class Image2D extends WorldBase {
    /**The BufferedImage that is rendered with this object.
     * 
     */
    public BufferedImage Image;

    private String imagePath = "JGamePackage\\JGame\\Assets\\imageDefault.png";

    public Image2D() {
        SetImage(imagePath);
    }

    public void SetImage(String path) {
        try {
            this.Image = ImageIO.read(new File(path));
            imagePath = path;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics2D graphics) {
        Vector2 renderSize = GetRenderSize();
        Vector2 renderPosition = GetRenderPosition();

        if (!game.Camera.AreBoundsInCameraBounds(renderSize, renderPosition)) return;

        if (this.Transparency < 1) {
            graphics.setColor(this.GetRenderColor());

            graphics.fillRect((int) renderPosition.X, (int) renderPosition.Y, (int) renderSize.X, (int) renderSize.Y);
        }

        graphics.drawImage(this.Image, (int) renderPosition.X, (int) renderPosition.Y, (int) renderSize.X, (int) renderSize.Y, null);
    }
}
