package JGamePackage.JGame.Classes.UI.Modifiers;

import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;

public class UIListLayout extends UIModifier {
    public Constants.ListAlignment ItemAlignment = Constants.ListAlignment.Vertical;

    public UDim2 Padding = UDim2.fromAbsolute(5, 5);

    @Override
    public UIListLayout Clone() {
        UIListLayout clone = this.cloneWithoutChildren();
        this.cloneHierarchyToNewParent(clone);
        return clone;
    }

    @Override
    protected UIListLayout cloneWithoutChildren() {
        UIListLayout clone = new UIListLayout();
        clone.Padding = this.Padding;
        clone.ItemAlignment = this.ItemAlignment;
        return clone;
    }
}
