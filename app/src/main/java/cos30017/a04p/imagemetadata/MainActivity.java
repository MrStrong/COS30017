package cos30017.a04p.imagemetadata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bananaBtnClickHandler(View v) {
        showMetadataEditView(getString(R.string.image_name_banana), R.drawable.banana_700);
    }

    public void grapeBtnClickHandler(View v) {
        showMetadataEditView(getString(R.string.image_name_grape), R.drawable.grape_700);
    }

    public void grapefruitBtnClickHandler(View v) {
        showMetadataEditView(getString(R.string.image_name_grapefruit), R.drawable.grapefruit_700);
    }

    public void pineappleBtnClickHandler(View v) {
        showMetadataEditView(getString(R.string.image_name_pineapple), R.drawable.pineapple_700);
    }

    private void showMetadataEditView(String imageName, int drawableImage)
    {
        Bundle dataBundle = new Bundle();
        dataBundle.putString("name", imageName);
        dataBundle.putInt("image", drawableImage);

        // set the sender and the receiver of the intent
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MetadataEditActivity.class);
        intent.putExtras(dataBundle); // store the data you want sent

        startActivityForResult(intent, 0);
    }

    public void onMetadataEditResult(int requestCode, int resultCode, Intent intent) {
        if (intent == null) {
            Log.i("ACTIVITY-RESULT-Intent", "Is NULL");
        }
        else {
            Log.i("ACTIVITY-RESULT-Intent", "Has Data");
            //read data back into person object
            ArrayList<ImageMetadata> imageMetadataDataList = intent.getParcelableArrayListExtra("");
        }
    }
}
