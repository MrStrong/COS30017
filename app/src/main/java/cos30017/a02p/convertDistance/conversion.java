package cos30017.a02p.convertDistance;

/**
 * Created by Daniel on 3/09/2016.
 */
public class Conversion {
    private double miles, feet, inches;
    private static final double milesToCentimeter = 160934.400579467, feetToCentimeter = 30.4799999536704, inchesToCentimeter = 2.539999983236;

    public Conversion(double miles, double feet, double inches) {
        this.miles = miles;
        this.feet = feet;
        this.inches = inches;
    }

    public Conversion(String miles, String feet, String inches) {

    }


    public double toCentimeters() {
        double centimeters;

        centimeters = miles * milesToCentimeter;
        centimeters += feet * feetToCentimeter;
        centimeters += inches * inchesToCentimeter;

        return centimeters;
    }

    public double toMeters() {
        return (toCentimeters() / 100) ;
    }
}
