package cos30017.a02p.convertdistance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView convertedText;
    private EditText inputMiles;
    private EditText inputFeet;
    private EditText inputInches;
    private CheckBox checkBoxMeters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI(savedInstanceState);
    }

    private void initializeUI(Bundle savedInstanceState) {
        convertedText = (TextView) findViewById(R.id.textResult);
        inputMiles = (EditText) findViewById(R.id.inputMiles);
        inputFeet = (EditText) findViewById(R.id.inputFeet);
        inputInches = (EditText) findViewById(R.id.inputInches);
        checkBoxMeters = (CheckBox) findViewById(R.id.checkBoxMeters);

        restoreState(savedInstanceState);

        Button btnConvert = (Button)findViewById(R.id.buttonConvert);
        btnConvert.setOnClickListener(clickBtnConvertListener);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    //save vales needed for the view
    @Override
    protected void onSaveInstanceState(Bundle state) {
        state.putString("convertedText", convertedText.getText().toString());
        state.putString("inputMiles", inputMiles.getText().toString());
        state.putString("inputFeet", inputFeet.getText().toString());
        state.putString("inputInches", inputInches.getText().toString());
        state.putBoolean("checkBoxMeters", checkBoxMeters.isChecked());

    }

    //restore previous values if available
    private void restoreState(Bundle state) {
        if (state != null) {
            convertedText.setText(state.getString("convertedText"));
            inputMiles.setText(state.getString("inputMiles"));
            inputFeet.setText(state.getString("inputFeet"));
            inputInches.setText(state.getString("inputInches"));
            checkBoxMeters.setChecked(state.getBoolean("checkBoxMeters"));

        }
    }

    private View.OnClickListener clickBtnConvertListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Conversion conversion = new Conversion(inputMiles.getText().toString(), inputFeet.getText().toString(), inputInches.getText().toString());

                if ( !checkBoxMeters.isChecked() ) {
                    convertedText.setText(String.format("%.2f CM", conversion.toCentimeters()));
                } else {
                    convertedText.setText(String.format("%.2f M", conversion.toMeters()));
                }

            } catch (Exception e) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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
