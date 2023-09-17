package frc.robot;

public class Misc {
    public static double clamp(double value, double min, double max) {
        value = Math.max(value, min);
        value = Math.min(value, max);
        return value;
    }
}
