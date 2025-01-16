package JGamePackage.JGame.Classes.UI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import JGamePackage.JGame.Classes.Rendering.RenderUtil;
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
            RenderUtil.drawRectangle(this, renderSize, renderPos, GetBackgroundRenderColor());
        }

        RenderUtil.drawImage(this, renderSize, renderPos, Image);
    }

    @Override
    public UIImage Clone() {
        UIImage clone = cloneWithoutChildren();
        this.cloneHierarchyToNewParent(clone);
        return clone;
    }

    @Override
    protected UIImage cloneWithoutChildren() {
        UIImage img = new UIImage();
        img.SetImage(this.imagePath);
        this.cloneHelper(img);
        return img;
    }
    
}
