package JGamePackage.JGame.Classes.UI.Modifiers;

import java.awt.Color;

public class UIBorder extends UIModifier {
    public int Width = 5;

    public Color BorderColor = Color.black;

    @Override
    public UIBorder Clone() {
        UIBorder clone = this.cloneWithoutChildren();
        this.cloneHierarchyToNewParent(clone);
        return clone;
    }

    @Override
    protected UIBorder cloneWithoutChildren() {
        UIBorder clone = new UIBorder();
        clone.Width = this.Width;
        clone.BorderColor = this.BorderColor;
        return clone;
    }
}
