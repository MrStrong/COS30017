package cos30017.a05p.suntime.ui;

/**
 * Created by Daniel on 1/11/2016.
 */

public class City {
    private String name;
    private double latitude;
    private double longitude;
    private String timezone;

    City(String[] inputArray) {
        this.name = inputArray[0];
        this.latitude = Double.parseDouble(inputArray[1]);
        this.longitude = Double.parseDouble(inputArray[2]);
        this.timezone = inputArray[3];
    }

    City(String name, long latitude, long longitude, String timezone) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }
    public String getCapitalCity() {
        String temp[] = timezone.split("/");
        return temp[1];
    }
}


