package JGameStudio.Studio.Modules;

import JGamePackage.JGame.Types.PointObjects.Vector2;

public class GridManager {
    private static double gridScale = 0;

    public static double getGridScale() {
        return gridScale;
    }

    public static void setGridScale(double scale) {
        gridScale = scale;
    }

    public static double snapToGrid(double value) {
        return Math.floor((value + gridScale / 2) / gridScale) * gridScale;
    }

    public static Vector2 snapToGrid(Vector2 value) {
        return new Vector2(snapToGrid(value.X), snapToGrid(value.Y));
    }
}
