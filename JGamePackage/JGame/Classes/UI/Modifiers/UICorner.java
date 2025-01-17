package JGamePackage.JGame.Classes.UI.Modifiers;

import JGamePackage.JGame.Types.Constants.Constants;

public class UICorner extends UIModifier {
    public double Radius = .1;

    /**The {@code Vector2Axis} that the arc size of the UICorner is dependant on.
     * 
     */
    public Constants.Vector2Axis RelativeTo = Constants.Vector2Axis.X;

    @Override
    public UICorner Clone() {
        UICorner clone = this.cloneWithoutChildren();
        this.cloneHierarchyToNewParent(clone);
        return clone;
    }

    @Override
    protected UICorner cloneWithoutChildren() {
        UICorner clone = new UICorner();
        clone.Radius = this.Radius;
        clone.RelativeTo = this.RelativeTo;
        return clone;
    }
}
