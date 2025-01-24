package JGamePackage.JGame.Classes.UI;

import java.awt.Graphics2D;
import java.io.File;

import javax.imageio.ImageIO;

import JGamePackage.JGame.Classes.Rendering.RenderUtil;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class UIImage extends UIBase {

    /**The Image that is rendered with this object.
     * 
     */
    public java.awt.Image Image;

    private String imagePath = "JGamePackage\\JGame\\Assets\\imageDefault.png";

    private Vector2 scale = null;

    public boolean PixelPerfect = false;

    public UIImage() {
        SetImage(imagePath);

        PixelPerfect = game.Camera.GlobalPixelPerfect;
    }

    public void SetImage(String path, Vector2 scale) {
        try {
            this.Image = ImageIO.read(new File(path));
            imagePath = path;
            if (scale != null) SetImageScale(scale);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SetImage(String path) {
        try {
            this.Image = ImageIO.read(new File(path));
            imagePath = path;
            if (scale != null) SetImageScale(scale);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**Downsizes the BufferedImage of this instance while making sure the render quality of the image
     * is not impacted.
     * 
     */
    public void SetImageScale(Vector2 scale) {
        this.Image = this.Image.getScaledInstance((int) scale.X, (int) scale.Y, java.awt.Image.SCALE_SMOOTH);
        this.scale = scale;
    }

    @Override
    public void render(Graphics2D graphics) {
        Vector2 renderSize = GetAbsoluteSize();
        Vector2 renderPos = GetAbsolutePosition();

        if (!game.Camera.AreBoundsInCameraBounds(renderSize, renderPos)) return;

        if (this.BackgroundTransparency < 1) {
            RenderUtil.drawRectangle(this, renderSize, renderPos, GetBackgroundRenderColor());
        }

        RenderUtil.drawImage(this, renderSize, renderPos, Image, PixelPerfect);
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

        if (this.scale != null)
            img.SetImageScale(scale);

        this.cloneHelper(img);
        return img;
    }
    
}
