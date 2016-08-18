package cos30017.lab02.lightmeup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/* Note that the images are slightly different so when turning on and off
 * do expect the edges not to line up
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI(savedInstanceState);
    }

    //save vales needed for the view
    @Override
    protected void onSaveInstanceState(Bundle state) {
        ImageView bulb = (ImageView)findViewById(R.id.imageLight);
        state.putInt("bulbTagState",(Integer)bulb.getTag());
    }

    //restore previous values if available
    private void restoreState(Bundle state) {
        ImageView bulb = (ImageView)findViewById(R.id.imageLight);
        if (state == null) {
            bulb.setTag(R.drawable.off);
        } else {
            bulb.setTag(state.getInt("bulbTagState"));
        }
    }

    //keep things tidy
    private void initializeUI(Bundle savedInstanceState) {
        ImageView bulb = (ImageView)findViewById(R.id.imageLight);
        restoreState(savedInstanceState);
        setBulb();
        bulb.setOnLongClickListener(tapBulbClickListener);
    }

    private void setBulb() {
        ImageView bulb = (ImageView)findViewById(R.id.imageLight);;
        if ((Integer)bulb.getTag() == R.drawable.off) {
            bulb.setImageResource(R.drawable.off);
        } else {
            bulb.setImageResource(R.drawable.on);
        }

        //set landscape text
        TextView landText = (TextView)findViewById(R.id.landText);
        if (landText != null) {
            if ((Integer) bulb.getTag() == R.drawable.off) {
                landText.setText(R.string.land_text_off);
            } else {
                landText.setText(R.string.land_text_on);
            }
        }
    }

    private View.OnLongClickListener tapBulbClickListener = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            ImageView bulb = (ImageView)findViewById(R.id.imageLight);
            if ((Integer)bulb.getTag() == R.drawable.off) {
                bulb.setTag(R.drawable.on);
            } else {
                bulb.setTag(R.drawable.off);
            }
            setBulb();
            return true;
        }
    };
}
