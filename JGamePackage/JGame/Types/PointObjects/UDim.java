package JGamePackage.JGame.Types.PointObjects;

import JGamePackage.lib.Utils.ExtendedMath;

public class UDim {
    public final double Scale;
    public final double Absolute;

    public UDim(double scale, double absolute) {
        this.Scale = scale;
        this.Absolute = absolute;
    }

    public UDim lerp(UDim goal, double t) {
        return new UDim(ExtendedMath.lerp(this.Scale, goal.Scale, t), ExtendedMath.lerp(this.Absolute, goal.Absolute, t));
    }
}
