package gui.general;

public class Maths {

    public static double map(double v, double a, double b, double A, double B) {
        return A + (B - A) * ((v - a) / (b - a));
    }

    public static int randomInt(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public static double bound(double value, double min, double max) {
        return value < min ? min : value > max ? max : value;
    }

}
