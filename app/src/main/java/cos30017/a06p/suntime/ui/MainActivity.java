package cos30017.a06p.suntime.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TimeZone;

import cos30017.a06p.R;
import cos30017.a06p.suntime.calc.AstronomicalCalendar;
import cos30017.a06p.suntime.calc.GeoLocation;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

    /**
     *  read city text file in, check if user file exists, if so use that
     *  otherwise use the template file included in the apk values/raw
     */
	private void readCityFile() {

        //check user_au_locations.txt file exists in internal storage
        File userLocationFile = new File(getFilesDir(), getString(R.string.user_custom_location_file));
        if (!userLocationFile.exists()) {
            Log.i("readCityFile", "user file does not exist, using default list");

            try {
                InputStream inputStream = getResources().openRawResource(R.raw.au_locations);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    cityList.add(new City(line.split(",")));
                }
                Log.d("MAIN-readCityFile", "reading file complete");
            } catch (Exception e){
                Log.e("MAIN-readCityFile", "error reading default file");
                e.printStackTrace();
            }
        } else {
            //use user file from internal storage
            try {
                InputStream inputStream = openFileInput(userLocationFile.getName());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    cityList.add(new City(line.split(",")));
                }
                Log.d("MAIN-readCityFile", "reading user file complete");

            } catch (Exception e) {
                Log.e("MAIN-readCityFile", "error reading user file");
                e.printStackTrace();
            }
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


        //update file
        try {
            FileOutputStream outputStream;
            String fileDelimiter = getString(R.string.file_delimiter);
            char fileNewline = '\n';
            String line;

            outputStream = openFileOutput(getString(R.string.user_custom_location_file), MODE_PRIVATE);

            for (int i=0; i < cityList.size(); i++) {
                line = cityList.get(i).getName() + fileDelimiter + cityList.get(i).getLatitude() + fileDelimiter +
                        cityList.get(i).getLongitude() + fileDelimiter + cityList.get(i).getTimezone() + fileNewline;
                outputStream.write(line.getBytes());

            }

            outputStream.close();

            Log.i("FILE", "file saved");
        } catch (Exception e) {
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
        TextView sunriseTV = (TextView) findViewById(R.id.sunriseTimeTV);
        TextView sunsetTV = (TextView) findViewById(R.id.sunsetTimeTV);

        try {
           AstronomicalCalendar ac = new AstronomicalCalendar(geolocation);
           ac.getCalendar().set(year, monthOfYear, dayOfMonth);
           Date srise = ac.getSunrise();
           Date sset = ac.getSunset();

           SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

           Log.d("SUNRISE Unformatted", srise + "");

           sunriseTV.setText(sdf.format(srise));
           sunsetTV.setText(sdf.format(sset));

        } catch (NullPointerException e) {
            Log.i("SUNRISE", "Location too close to pole, cannot calculate suntime");

            sunriseTV.setText(getString(R.string.text_suntime_pole));
            sunsetTV.setText(getString(R.string.text_suntime_pole));

            showAlertDialog(getString(R.string.dialog_invalid_input) , getString(R.string.dialog_warn_location_poles));

        } catch (Exception e) {
           Log.e("SUNRISE", "Unknown error calculating suntime");
           e.printStackTrace();
           showAlertDialog(getString(R.string.dialog_error), getString(R.string.dialog_error_calculation));
        }

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


    private void showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}