package cos30017.a03p.converter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TemperatureActivity extends AppCompatActivity {

    private TextView convertedText;
    private EditText inputCelsius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        initializeUI(savedInstanceState);
    }

    private void initializeUI(Bundle savedInstanceState) {
        convertedText = (TextView) findViewById(R.id.textResult);
        inputCelsius = (EditText) findViewById(R.id.inputCelsius);

        restoreState(savedInstanceState);

        Button btnConvert = (Button)findViewById(R.id.buttonConvert);
        btnConvert.setOnClickListener(clickBtnConvertListener);
    }

    //save vales needed for the view
    @Override
    protected void onSaveInstanceState(Bundle state) {
        state.putString("convertedText", convertedText.getText().toString());
        state.putString("inputCelsius", inputCelsius.getText().toString());

    }

    //restore previous values if available
    private void restoreState(Bundle state) {
        if (state != null) {
            convertedText.setText(state.getString("convertedText"));
            inputCelsius.setText(state.getString("inputCelsius"));
        }
    }

    private View.OnClickListener clickBtnConvertListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                TemperatureConversion temperatureConversion = new TemperatureConversion(inputCelsius.getText().toString());

                convertedText.setText(String.format("%.2f F", temperatureConversion.toFahrenheit()));

            } catch (Exception e) {
                AlertDialog alertDialog = new AlertDialog.Builder(TemperatureActivity.this).create();
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
