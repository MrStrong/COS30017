package cos30017.a02p.convertdistance;

import android.util.Log;
import android.widget.EditText;

import java.io.IOException;

/**
 * Created by Daniel on 3/09/2016.
 */
public class Conversion {
    private double miles, feet, inches;
    private static final double milesToCentimeter = 160934.400579467, feetToCentimeter = 30.4799999536704, inchesToCentimeter = 2.539999983236;

    public Conversion(double miles, double feet, double inc1hes) throws Exception {
        this.miles = miles;
        this.feet = feet;
        this.inches = inches;
    }

    public Conversion(String miles, String feet, String inches) throws Exception {
        Log.d("Puppy", miles);
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
}
