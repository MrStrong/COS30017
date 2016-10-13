package cos30017.a03p.converter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class DistanceActivity extends AppCompatActivity {

    private TextView convertedText;
    private EditText inputMiles;
    private EditText inputFeet;
    private EditText inputInches;
    private Spinner spinnerConversionUnits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        initializeUI(savedInstanceState);
    }

    private void initializeUI(Bundle savedInstanceState) {
        convertedText = (TextView) findViewById(R.id.textResult);
        inputMiles = (EditText) findViewById(R.id.inputMiles);
        inputFeet = (EditText) findViewById(R.id.inputFeet);
        inputInches = (EditText) findViewById(R.id.inputInches);
        spinnerConversionUnits = (Spinner) findViewById(R.id.spinnerConversionUnits);

        restoreState(savedInstanceState);

        Button btnConvert = (Button)findViewById(R.id.buttonConvert);
        btnConvert.setOnClickListener(clickBtnConvertListener);
    }

    //save vales needed for the view
    @Override
    protected void onSaveInstanceState(Bundle state) {
        state.putString("convertedText", convertedText.getText().toString());
        state.putString("inputMiles", inputMiles.getText().toString());
        state.putString("inputFeet", inputFeet.getText().toString());
        state.putString("inputInches", inputInches.getText().toString());
        state.putInt("spinnerConversionUnits", spinnerConversionUnits.getSelectedItemPosition());

    }

    //restore previous values if available
    private void restoreState(Bundle state) {
        if (state != null) {
            convertedText.setText(state.getString("convertedText"));
            inputMiles.setText(state.getString("inputMiles"));
            inputFeet.setText(state.getString("inputFeet"));
            inputInches.setText(state.getString("inputInches"));
            spinnerConversionUnits.setSelection(state.getInt("spinnerConversionUnits"));
        }
    }

    private View.OnClickListener clickBtnConvertListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                DistanceConversion distanceConversion = new DistanceConversion(inputMiles.getText().toString(), inputFeet.getText().toString(), inputInches.getText().toString());

                switch (spinnerConversionUnits.getSelectedItemPosition()) {
                    //centimeters
                    case 0:
                        convertedText.setText(String.format("%.2f CM", distanceConversion.toCentimeters()));
                        break;
                    //Meters
                    case 1:
                        convertedText.setText(String.format("%.2f M", distanceConversion.toMeters()));
                        break;
                    //Kilometers
                    case 2:
                        convertedText.setText(String.format("%.2f KM", distanceConversion.toKilometers()));
                        break;
                }

            } catch (Exception e) {
                AlertDialog alertDialog = new AlertDialog.Builder(DistanceActivity.this).create();
                alertDialog.setTitle("Input Error");
                alertDialog.setMessage("Input cannot be empty");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

            //hide keyboard after submit
            try {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                //no keyboard to hide. do nothing
            }
        }
    };
}
