package PaperAirplaneSimulation;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class MSAUtil {
    public static double toMeters(double pixels) {
        return pixels/ExperimentData.meterInPixel;
    }

    public static double toPixels(double meters) {
        return ExperimentData.meterInPixel*meters;
    }

    public static void positionSpecialCamera(JGame game, Vector2 p1, Vector2 p2) {
        game.Camera.Position = game.Camera.Position.lerp(p2.subtract(p1).divide(2).add(p1), 0.5);
    }
}
