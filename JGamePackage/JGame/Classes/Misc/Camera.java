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
        return object.Size.multiply(DepthFactor);
    }
    
    public Vector2 GetWorldBaseRenderPosition(WorldBase object) {
        checkOutOfBounds();
        return Position.add(object.Position.multiply(DepthFactor));
    }
}
