package JGamePackage.JGame.Classes.UI;

import java.awt.Color;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.Rendering.Renderable;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public abstract class UIBase extends Renderable {

    /**The position of the object in 2D space, represented by a UDim2.
     * 
     */
    public UDim2 Position = UDim2.zero;

    /**The size of the object in 2D space, represented by a UDim2.
     * 
     */
    public UDim2 Size = UDim2.fromScale(.1, .1);

    /**Determines what point of the UIBase will be rendered at the {@code UIBase.Position} <p>
     * {@code (0,0)} - The top left corner of the object, <p>
     * {@code (.5,.5)} - The center of the object,<p>
     * {@code (1,1)} - The bottom right corner of the object.<p>
     * 
     */
    public Vector2 AnchorPoint = Vector2.zero;

    /**The background color of the object.
     * 
     */
    public Color BackgroundColor = Color.white;

    /**The transparency of the background of the object.
     * 
     */
    public double BackgroundTransparency = 0;

    /**Whether or not the object and its descenants will be rendered.
     * 
     */
    public boolean Visible = true;

    //--ABSOLUTE STUFF--//
    public Vector2 GetAbsolutePosition() {
        Instance parentInstance = this.GetParent();

        if (!(parentInstance instanceof UIBase)) return Position.ToVector2(game.Services.WindowService.GetScreenSize());

        return Position.ToVector2(((UIBase) parentInstance).GetAbsolutePosition());
    }

    public Vector2 GetAbsoluteSize() {
        Instance parentInstance = this.GetParent();
        
        if (!(parentInstance instanceof UIBase)) return Size.ToVector2(game.Services.WindowService.GetScreenSize());

        return Size.ToVector2(((UIBase) parentInstance).GetAbsoluteSize());
    }

    public double GetAbsoluteRotation() {
        Instance parentInstance = this.GetParent();
        if (!(parentInstance instanceof UIBase)) return Rotation;

        return Rotation + ((UIBase) parentInstance).GetAbsoluteRotation();
    }

    //--ANCHOR POINTS--//
    protected double getHorizontalAnchorOffset() {
        return GetAbsoluteSize().X*AnchorPoint.X;
    }

    protected double getVerticalAnchorOffset() {
        return GetAbsoluteSize().Y*AnchorPoint.Y;
    }

    public Vector2 GetAnchorPointOffset() {
        return new Vector2(getHorizontalAnchorOffset(), getVerticalAnchorOffset());
    }


    protected Color GetBackgroundRenderColor() {
        return new Color(BackgroundColor.getRed(), BackgroundColor.getGreen(), BackgroundColor.getBlue(), (int) (255*(1-BackgroundTransparency)));
    }
}
