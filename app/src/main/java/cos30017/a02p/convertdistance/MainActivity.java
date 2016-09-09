package cos30017.a02p.convertdistance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    }

    private View.OnClickListener clickBtnConvertListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView convertedText = (TextView) findViewById(R.id.textResult);
            EditText inputMiles = (EditText) findViewById(R.id.inputMiles);
            EditText inputFeet = (EditText) findViewById(R.id.inputFeet);
            EditText inputinches = (EditText) findViewById(R.id.inputInches);

            //Conversion conversion = new Conversion((Double) inputMiles.getText().toString());
            if ( !((CheckBox) findViewById(R.id.checkBox)).isChecked() ) {

            }
        }
    };
}
