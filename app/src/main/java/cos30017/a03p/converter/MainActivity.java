package cos30017.a03p.converter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void temperatureBtnClickHandler(View v) {
        Intent intent = new Intent(this, TemperatureActivity.class);
        startActivity(intent);
    }

    public void distanceBtnClickHandler(View v) {
        Intent intent = new Intent(this, DistanceActivity.class);
        startActivity(intent);
    }
}
