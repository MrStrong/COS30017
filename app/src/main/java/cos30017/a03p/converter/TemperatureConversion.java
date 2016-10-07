package cos30017.a03p.converter;

/**
 * Created by Daniel on 7/10/2016.
 */
public class TemperatureConversion {
    private double celsius, fahrenheit;

    public TemperatureConversion(double celsius) {
        this.celsius = celsius;
    }

    public TemperatureConversion(String celsius) throws Exception {
        this.celsius = Double.parseDouble(celsius);
    }

    public double toFahrenheit() {
        return (celsius * 1.8 + 32);
    }
}
