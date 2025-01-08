package PaperAirplaneSimulation;

public class MSAUtil {
    public static double toMeters(double pixels) {
        return pixels/ExperimentData.meterInPixel;
    }

    public static double toPixels(double meters) {
        return ExperimentData.meterInPixel*meters;
    }
}
