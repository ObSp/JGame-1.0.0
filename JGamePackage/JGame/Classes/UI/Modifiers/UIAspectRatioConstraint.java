package JGamePackage.JGame.Classes.UI.Modifiers;

import JGamePackage.JGame.Types.Constants.Constants;

public class UIAspectRatioConstraint extends UIModifier {
    public double AspectRatio = 1;

    public Constants.Vector2Axis DominantAxis = Constants.Vector2Axis.Y;

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
