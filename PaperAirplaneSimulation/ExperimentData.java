package PaperAirplaneSimulation;

public final class ExperimentData {
    public static final double coefficient = 0.25;
    public static final double airDensity = 1.28; //kg/m^3
    public static final double referenceArea = 2.20; //m
    public static final double planeWeight = 0.008; //kg

    public static final double meterInPixel = 243; //pixels

    public static final double ref1Time = 1.04; //seconds
    public static final double ref1DistanceX = 6.41; //m
    public static final double ref1throwHeight = 1.85; //m
    public static final double ref1GlideDistancePerHeightLost = ref1DistanceX/ref1throwHeight; //m
    public static final double ref1Wind = .3;

    public static final double ref2Time = 1.07; //seconds
    public static final double ref2DistanceX = 4.85; //m
    public static final double ref2throwHeight = 1.80; //m
    public static final double ref2GlideDistancePerHeightLost = ref2DistanceX/ref2throwHeight; //m
    public static final double ref2Wind = .5;
}
