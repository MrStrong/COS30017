package cos30017.a03p.converter;


/**
 * Created by Daniel on 3/09/2016.
 */
public class DistanceConversion {
    private double miles, feet, inches;
    private static final double milesToCentimeter = 160934.400579467, feetToCentimeter = 30.4799999536704, inchesToCentimeter = 2.539999983236;

    public DistanceConversion(double miles, double feet, double inches) throws Exception {
        this.miles = miles;
        this.feet = feet;
        this.inches = inches;
    }

    public DistanceConversion(String miles, String feet, String inches) throws Exception {
        this.miles = Double.parseDouble(miles);
        this.feet = Double.parseDouble(feet);
        this.inches = Double.parseDouble(inches);
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

    public double toKilometers() {
        return (toCentimeters() / 1000);
    }
}
