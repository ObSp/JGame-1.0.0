package JGamePackage.JGame.Classes.World;

import java.awt.Color;

import JGamePackage.JGame.Classes.Rendering.Renderable;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public abstract class WorldBase extends Renderable{

    /**The position of the object, measured in pixels.
     * 
     */
    public Vector2 Position = Vector2.zero;

    /**The size of the object, measured in pixels.
     * 
     */
    public Vector2 Size = new Vector2(100);

    /**Where the object's "center" will be when rendered. Some common values are: <p>
     * {@code (0,0)} - The top left corner of the object, <p>
     * {@code (.5,.5)} - The center of the object,<p>
     * {@code (1,1)} - The bottom right corner of the object.<p>
     * 
     */
    public Vector2 Pivot = new Vector2(0);

    /**The background color of the object.
     * 
     */
    public Color FillColor = Color.white;

    /**Whether or not the object will be rendered.
     * 
     */
    public boolean Visible = true;

    /**The transparency of the object.
     * 
     */
    public double Transparency = 0;


    //--PIVOTS--//
    protected double getHorizontalPivotOffset() {
        return Size.X*Pivot.X;
    }

    protected double getVerticalPivotOffset() {
        return Size.Y*Pivot.Y;
    }

    public Vector2 GetPivotOffset() {
        return new Vector2(getHorizontalPivotOffset(), getVerticalPivotOffset());
    }

    //--RENDER STUFF--//
    public Vector2 GetRenderPosition() {
        return game.Camera.GetWorldBaseRenderPosition(this);
    }

    public Vector2 GetRenderSize() {
        return game.Camera.GetWorldBaseRenderSize(this);
    }

    protected Color GetRenderColor() {
        return new Color(FillColor.getRed(), FillColor.getGreen(), FillColor.getBlue(), (int) (255*(1-Transparency)));
    }

    @Override
    public WorldBase Clone() {
        return null;
    }

    @Override
    public WorldBase cloneWithoutChildren() {
        return null;
    }
}
