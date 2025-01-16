package JGamePackage.JGame.Classes.Misc;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.World.WorldBase;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.lib.CustomError.CustomError;

public class Camera extends Instance {

    private static CustomError ErrorDepthFactorOutOfBounds = new CustomError("Camera DepthFactor of %s is invalid; DepthFactor must be greater than 0.", CustomError.ERROR, "JGamePackage");

    /**The world offset of the camera.
     * 
     */
    public Vector2 Position = Vector2.zero;

    /**The Z-Offset factor of the Camera. Increasing this will increase the camera's view size.
     * 
     */
    public double DepthFactor = 1;

    /**The rotation of the camera, in radians.
     * 
     */
    public double Rotation = 0;


    private void checkOutOfBounds() {
        if (DepthFactor <= 0) {
            ErrorDepthFactorOutOfBounds.Throw(new String[] {DepthFactor+""});
        }
    }


    public Vector2 GetWorldBaseRenderSize(WorldBase object) {
        checkOutOfBounds();
        return object.Size.divide(DepthFactor);
    }

    private Vector2 getWorldBaseTopLeftCorner(WorldBase object) {
        return object.Position.subtract(object.GetPivotOffset());
    }

    private Vector2 getCenterPos() {
        return game.Services.WindowService.GetWindowSize().divide(2);
    }
    
    public Vector2 GetWorldBaseRenderPosition(WorldBase object) {
        checkOutOfBounds();
        return getWorldBaseTopLeftCorner(object).subtract(this.Position).divide(DepthFactor).add(getCenterPos()).subtract(0, game.Services.WindowService.IsFullscreen() ? 50 : 0);//Position.add(object.Position.subtract(object.GetPivotOffset())).divide(DepthFactor).add(game.Services.WindowService.GetWindowSize().divide(2));
    }


    public boolean AreBoundsInCameraBounds(Vector2 size, Vector2 position) {
        Vector2 screenSize = game.Services.WindowService.GetWindowSize();

        double left = 0;
        double top = 0;
        double right = left + screenSize.X;
        double bottom = top + screenSize.Y;

        Vector2 topLeft = position;
        double otherLeft = topLeft.X;
        double otherRight = topLeft.X+size.X;
        double otherTop = topLeft.Y;
        double otherBottom = otherTop+size.Y;

        boolean visibleLeft = otherRight > left;
        boolean visibleRight = otherLeft < right;
        boolean visibleTop = otherBottom > top;
        boolean visibleBottom = otherTop < bottom;

        return visibleLeft && visibleRight && visibleTop && visibleBottom;
    }

    @Override
    public Camera Clone() {
        Camera clone = cloneWithoutChildren();
        this.cloneHierarchyToNewParent(clone);
        return clone;
    }

    @Override
    protected Camera cloneWithoutChildren() {
        Camera cam = new Camera();
        cam.Position = this.Position;
        cam.Rotation = this.Rotation;
        cam.DepthFactor = this.DepthFactor;
        return cam;
    }
}
