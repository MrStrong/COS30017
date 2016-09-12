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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI(savedInstanceState);
    }

    private void initializeUI(Bundle savedInstanceState) {
        Button btnConvert = (Button)findViewById(R.id.buttonConvert);
        btnConvert.setOnClickListener(clickBtnConvertListener);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private View.OnClickListener clickBtnConvertListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView convertedText = (TextView) findViewById(R.id.textResult);
            EditText inputMiles = (EditText) findViewById(R.id.inputMiles);
            EditText inputFeet = (EditText) findViewById(R.id.inputFeet);
            EditText inputInches = (EditText) findViewById(R.id.inputInches);

            try {
                Conversion conversion = new Conversion(inputMiles.getText().toString(), inputFeet.getText().toString(), inputInches.getText().toString());

                if ( !((CheckBox) findViewById(R.id.checkBox)).isChecked() ) {
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
