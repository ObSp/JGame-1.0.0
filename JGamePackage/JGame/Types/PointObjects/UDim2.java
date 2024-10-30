package JGamePackage.JGame.Types.PointObjects;

public class UDim2 extends BasePoint {

    public static UDim2 fromScale(double xScale, double yScale) {
        return new UDim2(xScale, 0, yScale, 0);
    }

    public static UDim2 fromAbsolute(double xAbsolute, double yAbsolute) {
        return new UDim2(0, xAbsolute, 0, yAbsolute);
    }


    public final UDim X;
    public final UDim Y;


    public UDim2(double xScale, double xAbsolute, double yScale, double yAbsolute) {
        this.X = new UDim(xAbsolute, xAbsolute);
        this.Y = new UDim(yScale, yAbsolute);
    }

    public UDim2(UDim x, UDim y) {
        this.X = x;
        this.Y = y;
    }


    public UDim2 lerp(UDim2 goal, double t) {
        return new UDim2(X.lerp(goal.X, t), Y.lerp(goal.Y, t));
    }


    /**Converts this UDim2 into a Vector2 by multiplying
     * X.Scale and Y.Scale by totalSize.X and totalSize.Y and then
     * adding X.Absolute and Y.Absolute to their respective components.
     * 
     * @param totalSize
     * @return This UDim2 converted to a Vector2
     */
    public Vector2 ToVector2(Vector2 totalSize) {
        double scaleXAbsolute = this.X.Scale * totalSize.X;
        double scaleYAbsolute = this.Y.Scale * totalSize.Y;
        return new Vector2(scaleXAbsolute + this.X.Absolute, scaleYAbsolute + this.Y.Absolute);
    }
}
