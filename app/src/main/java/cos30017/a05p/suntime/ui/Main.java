package cos30017.a05p.suntime.ui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cos30017.a05p.R;
import cos30017.a05p.suntime.calc.AstronomicalCalendar;
import cos30017.a05p.suntime.calc.GeoLocation;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.Spinner;
import android.widget.TextView;

public class Main extends Activity
{
    private List<City> cityList = new ArrayList<>();
    private GeoLocation geolocation;
    private int year;
    private int monthOfYear;
    private int dayOfMonth;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initializeUI();
    }

	private void initializeUI()
	{
		DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		dp.init(year,month,day,dateChangeHandler); // setup initial values and reg. handler
        updateDate(year, month, day);

        //populate spinner
        readCityFile();
        ArrayList<String> cityNameList= new ArrayList<>();
        for(City city : cityList) {
            cityNameList.add(city.getName() + ", " + city.getCapitalCity());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityNameList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        spinnerCity.setAdapter(spinnerArrayAdapter);
        spinnerCity.setOnItemSelectedListener(onItemSelectedListener);

        //initial timezone and location. 34 = Melbourne
        spinnerCity.setSelection(34);
        updateLocation(34);

        updateSunTime();
	}

	private void readCityFile() {
        InputStream inputStream = getResources().openRawResource(R.raw.au_locations);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                cityList.add(new City(line.split(",")));
            }
            Log.d("MAIN-readCityFile", "reading file complete");
        } catch (Exception e){
            Log.e("MAIN-readCityFile", "error reading file");
            e.printStackTrace();
        }
	}

	private void updateDate(int year, int monthOfYear, int dayOfMonth)
	{
        this.year = year;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;
	}

    //cbf hashmapping city names
    private void updateLocation(int cityIndex) {
        TimeZone tz = TimeZone.getTimeZone(cityList.get(cityIndex).getTimezone());
        geolocation = new GeoLocation(cityList.get(cityIndex).getCapitalCity(), cityList.get(cityIndex).getLatitude(), cityList.get(cityIndex).getLongitude(), tz);
    }


    private void updateSunTime() {
        AstronomicalCalendar ac = new AstronomicalCalendar(geolocation);
        ac.getCalendar().set(year, monthOfYear, dayOfMonth);
        Date srise = ac.getSunrise();
        Date sset = ac.getSunset();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        TextView sunriseTV = (TextView) findViewById(R.id.sunriseTimeTV);
        TextView sunsetTV = (TextView) findViewById(R.id.sunsetTimeTV);
        Log.d("SUNRISE Unformatted", srise+"");

        sunriseTV.setText(sdf.format(srise));
        sunsetTV.setText(sdf.format(sset));
    }
	
	OnDateChangedListener dateChangeHandler = new OnDateChangedListener()
	{
		public void onDateChanged(DatePicker dp, int year, int monthOfYear, int dayOfMonth)
		{
			updateDate(year, monthOfYear, dayOfMonth);
            updateSunTime();
		}	
	};

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            updateLocation(position);
            updateSunTime();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //do nothing
        }
    };
}