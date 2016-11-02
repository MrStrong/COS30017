package cos30017.a05p.suntime.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import cos30017.a05p.R;

public class CustomLocationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_location_activity);
        initializeUI();
    }

    private void initializeUI() {

        //populate timezone spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, createTimezones());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerTimezone = (Spinner) findViewById(R.id.spinnerTimezone);
        spinnerTimezone.setAdapter(spinnerArrayAdapter);
    }

    /**
     * create an array list of Australian timezones
     * ref http://www.epochconverter.com/timezones
     * @return ArrayList<String> of timezones in the format of Country/State
     */

    private ArrayList<String> createTimezones () {
        ArrayList<String> timezoneArrayList = new ArrayList<>();

        timezoneArrayList.add("Australia/ACT");
        timezoneArrayList.add("Australia/Adelaide");
        timezoneArrayList.add("Australia/Brisbane");
        timezoneArrayList.add("Australia/Broken_Hill");
        timezoneArrayList.add("Australia/Canberra");
        timezoneArrayList.add("Australia/Currie");
        timezoneArrayList.add("Australia/Darwin");
        timezoneArrayList.add("Australia/Eucla");
        timezoneArrayList.add("Australia/Hobart");
        timezoneArrayList.add("Australia/LHI");
        timezoneArrayList.add("Australia/Lindeman");
        timezoneArrayList.add("Australia/Lord_Howe");
        timezoneArrayList.add("Australia/Melbourne");
        timezoneArrayList.add("Australia/North");
        timezoneArrayList.add("Australia/NSW");
        timezoneArrayList.add("Australia/Perth");
        timezoneArrayList.add("Australia/Queensland");
        timezoneArrayList.add("Australia/South");
        timezoneArrayList.add("Australia/Sydney");
        timezoneArrayList.add("Australia/Tasmania");
        timezoneArrayList.add("Australia/Victoria");
        timezoneArrayList.add("Australia/West");
        timezoneArrayList.add("Australia/Yancowinna");

        return  timezoneArrayList;
    }

    public City saveCity() throws Exception{
        String cityName;
        String latitude;
        String longitude;
        String timezone;

        //simple validation, ensure all fields are not blank
        EditText editTextLocationName = (EditText) findViewById(R.id.editTextLocationName);
        EditText editTextLat = (EditText) findViewById(R.id.editTextLatitude);
        EditText editTextLong = (EditText) findViewById(R.id.editTextLongitude);
        Spinner spinnerTimezone = (Spinner) findViewById(R.id.spinnerTimezone);

        if (editTextLocationName.getText().toString().equals("")) {
            showAlertDialog(getString(R.string.dialog_invalid_input), getString(R.string.dialog_invalid_input_name));
            throw new Exception();
        } else {
            cityName = editTextLocationName.getText().toString();
        }

        if (editTextLat.getText().toString().equals("")) {
            showAlertDialog(getString(R.string.dialog_invalid_input), getString(R.string.dialog_invalid_input_lat));
            throw new Exception();
        } else {
            latitude = editTextLat.getText().toString();
        }

        if (editTextLong.getText().toString().equals("")) {
            showAlertDialog(getString(R.string.dialog_invalid_input), getString(R.string.dialog_invalid_input_long));
            throw new Exception();
        } else {
            longitude = editTextLong.getText().toString();
        }

        timezone = spinnerTimezone.getSelectedItem().toString();

        return new City(cityName, latitude, longitude, timezone);

    }

    public void onSaveLocationBtnClickListener(View v) {

        try {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("CITY", saveCity());
            setResult(RESULT_OK, resultIntent);

        } catch (Exception e) {
            //don't leave the activity with an error
            Log.i("CustomLocationActivity", "Input invalid");
            return;
        }

        //go back
        onBackPressed();
    }



    private void showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(CustomLocationActivity.this).create();
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
