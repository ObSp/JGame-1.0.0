package JGamePackage.JGame.Classes.UI.Modifiers;

import JGamePackage.JGame.Types.Constants.Constants;

public class UICorner extends UIModifier {
    public double Radius = 0.1;

    /**The {@code Vector2Axis} that the arc size of the UICorner is dependant on.
     * 
     */
    public int RelativeTo = Constants.Vector2Axis.X;
}
