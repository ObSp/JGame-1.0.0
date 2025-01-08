package JGamePackage.JGame.Classes.UI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import JGamePackage.JGame.Types.PointObjects.Vector2;

public class UIImage extends UIBase {

    /**The BufferedImage that is rendered with this object.
     * 
     */
    public BufferedImage Image;

    private String imagePath = "JGamePackage\\JGame\\Assets\\imageDefault.png";

    public UIImage() {
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
        Vector2 renderSize = GetAbsoluteSize();
        Vector2 renderPos = GetAbsolutePosition();

        if (!game.Camera.AreBoundsInCameraBounds(renderSize, renderPos)) return;

        if (this.BackgroundTransparency < 1) {
            graphics.setColor(this.GetBackgroundRenderColor());

            graphics.fillRect((int) renderPos.X, (int) renderPos.Y, (int) renderSize.X, (int) renderSize.Y);
        }

        graphics.drawImage(this.Image, (int) renderPos.X, (int) renderPos.Y, (int) renderSize.X, (int) renderSize.Y, null);
    }
    
}
