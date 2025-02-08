package JGamePackage.JGame.Classes.Modifiers;

import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;

public class ListLayout extends Modifier {
    public Constants.ListAlignment ItemAlignment = Constants.ListAlignment.Vertical;

    public UDim2 Padding = UDim2.fromAbsolute(5, 5);

    @Override
    public ListLayout Clone() {
        ListLayout clone = this.cloneWithoutChildren();
        this.cloneHierarchyToNewParent(clone);
        return clone;
    }

    @Override
    protected ListLayout cloneWithoutChildren() {
        ListLayout clone = new ListLayout();
        clone.Padding = this.Padding;
        clone.ItemAlignment = this.ItemAlignment;
        return clone;
    }
}
