package JGamePackage.JGame.Classes.UI.Modifiers;

import JGamePackage.JGame.Classes.World.WorldBase;
import JGamePackage.JGame.Types.Constants.Constants;

public class UIAspectRatioConstraint extends UIModifier {
    public double AspectRatio = 1;

    public int DominantAxis = Constants.Vector2Axis.X;

    @Override
    public UIAspectRatioConstraint Clone() {
        UIAspectRatioConstraint clone = this.cloneWithoutChildren();
        this.cloneHierarchyToNewParent(clone);
        return clone;
    }

    @Override
    protected UIAspectRatioConstraint cloneWithoutChildren() {
        UIAspectRatioConstraint clone = new UIAspectRatioConstraint();
        clone.AspectRatio = this.AspectRatio;
        clone.DominantAxis = this.DominantAxis;
        return clone;
    }
}
