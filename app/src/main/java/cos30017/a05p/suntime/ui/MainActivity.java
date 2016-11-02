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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity
{
    private List<City> cityList = new ArrayList<>();
    private GeoLocation geolocation;
    private int year;
    private int monthOfYear;
    private int dayOfMonth;

    private Spinner spinnerCity;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
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

        //setup spinner
        readCityFile();
        spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        populateCitySpinner();
        spinnerCity.setOnItemSelectedListener(onItemSelectedListener);

        //initial timezone and location. 34 = Melbourne
        spinnerCity.setSelection(34);
        updateLocation(34);

        updateSunTime();
	}

    /**
     * populate spinner with city names and timezones from cityList
     * this should be called anytime the cityList changes
     */
    private void populateCitySpinner() {
        ArrayList<String> cityNameList= new ArrayList<>();
        for(City city : cityList) {
            cityNameList.add(city.getName() + ", " + city.getCapitalCity());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityNameList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(spinnerArrayAdapter);
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

    /**
     * update both the cityFile and citySpinner
     * @param city: new city to be added
     */
    private void updateCityFile(City city) {
        cityList.add(city);

        //update spinner
        populateCitySpinner();

        //write to file
        //TODO write to file
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

    public void onAddLocationBtnClickListener(View v) {
        // set the sender and reliever of the intent
        Intent intent = new Intent(getApplicationContext(), CustomLocationActivity.class);
        //no need to send anything so just start send the message
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent == null) {
            Log.i("ACTIVITY-RESULT-Intent", "Is NULL");
        }
        else {
            Log.i("ACTIVITY-RESULT-Intent", "Has Data");
            //read data back into ImageMetadata Array List
            City city = intent.getParcelableExtra("CITY");

            updateCityFile(city);
        }
    }
}