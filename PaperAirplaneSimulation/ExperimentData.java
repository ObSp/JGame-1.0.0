package PaperAirplaneSimulation;

public final class ExperimentData {
    public static final double coefficient = 0.25;
    public static final double airDensity = 1.28; //kg/m^3
    public static final double referenceArea = 2.20; //m
    public static final double planeWeight = 0.008; //kg
    public static final double planeWingArea = 0.022; //m^2

    public static final double meterInPixel = 243; //pixels

    public static final double ref1Time = 1.04; //seconds
    public static final double ref1DistanceX = 6.41; //m
    public static final double ref1throwHeight = 1.85; //m
    public static final double ref1GlideDistancePerHeightLost = ref1DistanceX/ref1throwHeight; //m
    public static final double ref1Wind = .3; //m/s
    public static final double ref1ThrowAngle = 10; //deg
    public static final double ref1InitialVelocityX = 5.38; //m/s
    public static final double ref1InitialVelocityY = 0.948; //m/s


    public static final double ref2Time = 1.07; //seconds
    public static final double ref2DistanceX = 4.85; //m
    public static final double ref2throwHeight = 1.80; //m
    public static final double ref2GlideDistancePerHeightLost = ref2DistanceX/ref2throwHeight; //m
    public static final double ref2Wind = .05; //m/s
    public static final double ref2ThrowAngle = 37; //deg
    public static final double ref2InitialVelocityX = 4.5;//4.86; //m/s
    public static final double ref2InitialVelocityY = 3.67; //m/s
}
