package cos30017.a06p.suntime.ui;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel on 1/11/2016.
 */

public class City implements Parcelable {
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

    City(String name, double latitude, double longitude, String timezone) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
    }

    City(String name, String latitude, String longitude, String timezone) {
        this.name = name;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.timezone = timezone;
    }

    protected City(Parcel in) {
        name = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        timezone = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(timezone);
    }
}


