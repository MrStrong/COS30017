package cos30017.a04p.imagemetadata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //store image data
    ArrayList<ImageMetadata> imageMetadataArrayList = new ArrayList<ImageMetadata>(4);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            createInitialImageMetadata();
        } catch (Exception e) {
            //do nothing, static time
        }
        //TODO restore state if previously run. Currently resets every run
        initializeUI();
    }

    private void initializeUI() {
        //populate table
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);
        ImageView imageView4 = (ImageView) findViewById(R.id.imageView4);
        TextView TextView1Name = (TextView) findViewById(R.id.textView1Name);
        TextView TextView2Name = (TextView) findViewById(R.id.textView2Name);
        TextView TextView3Name = (TextView) findViewById(R.id.textView3Name);
        TextView TextView4Name = (TextView) findViewById(R.id.textView4Name);
        TextView TextView1Date = (TextView) findViewById(R.id.textView1Date);
        TextView TextView2Date = (TextView) findViewById(R.id.textView2Date);
        TextView TextView3Date = (TextView) findViewById(R.id.textView3Date);
        TextView TextView4Date = (TextView) findViewById(R.id.textView4Date);

        imageView1.setImageResource(imageMetadataArrayList.get(0).getDrawableImage());
        imageView2.setImageResource(imageMetadataArrayList.get(1).getDrawableImage());
        imageView3.setImageResource(imageMetadataArrayList.get(2).getDrawableImage());
        imageView4.setImageResource(imageMetadataArrayList.get(3).getDrawableImage());
        TextView1Name.setText(imageMetadataArrayList.get(0).getName());
        TextView2Name.setText(imageMetadataArrayList.get(1).getName());
        TextView3Name.setText(imageMetadataArrayList.get(2).getName());
        TextView4Name.setText(imageMetadataArrayList.get(3).getName());

        String pattern = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        TextView1Date.setText(format.format(imageMetadataArrayList.get(0).getObtainedDate()));
        TextView2Date.setText(format.format(imageMetadataArrayList.get(1).getObtainedDate()));
        TextView3Date.setText(format.format(imageMetadataArrayList.get(2).getObtainedDate()));
        TextView4Date.setText(format.format(imageMetadataArrayList.get(3).getObtainedDate()));
    }

    private void createInitialImageMetadata() throws Exception {
        //sample date
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = format.parse("03/09/2016");


        //add image 1, banana required fields only
        imageMetadataArrayList.add(new ImageMetadata(
                R.drawable.banana,
                getString(R.string.image_name_banana),
                getString(R.string.image_obtainer_bob),
                date
        ));

        //add image 2, grapes required fields only
        imageMetadataArrayList.add(new ImageMetadata(
                R.drawable.grape,
                getString(R.string.image_name_grape),
                getString(R.string.image_obtainer_jill),
                date
        ));

        //add image 3, grapefruit required fields only
        imageMetadataArrayList.add(new ImageMetadata(
                R.drawable.grapefruit,
                getString(R.string.image_name_grapefruit),
                getString(R.string.image_obtainer_jill),
                date
        ));

        //add image 4, pineapple required fields only
        imageMetadataArrayList.add(new ImageMetadata(
                R.drawable.pineapple,
                getString(R.string.image_name_pineapple),
                getString(R.string.image_obtainer_bob),
                format.parse("15/08/2016"),
                "test.com.au",
                "pen pineapple apple pen",
                true,
                4
        ));
    }

    public void BtnClickHandler1(View v) {
        showMetadataEditView(imageMetadataArrayList.get(0), 0);
    }

    public void BtnClickHandler2(View v) {
        showMetadataEditView(imageMetadataArrayList.get(1), 1);
    }

    public void BtnClickHandler3(View v) {
        showMetadataEditView(imageMetadataArrayList.get(2), 2);
    }

    public void BtnClickHandler4(View v) {
        showMetadataEditView(imageMetadataArrayList.get(3), 3);
    }

    private void showMetadataEditView(ImageMetadata imageMetadata, Integer imageID)
    {
        // set the sender and the receiver of the intent
        Intent intent = new Intent(getApplicationContext(), MetadataEditActivity.class);
        // need an array list even if we put a single object only
        ArrayList<ImageMetadata> dataList = new ArrayList<ImageMetadata>();
        dataList.add(imageMetadata);

        intent.putParcelableArrayListExtra("IMAGE_METADATA", dataList);
        startActivityForResult(intent, imageID);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent == null) {
            Log.i("ACTIVITY-RESULT-Intent", "Is NULL");
        }
        else {
            Log.i("ACTIVITY-RESULT-Intent", "Has Data");
            //read data back into ImageMetadata Array List
            ArrayList<ImageMetadata> imageMetadataDataList = intent.getParcelableArrayListExtra("IMAGE_METADATA");
            ImageMetadata imageMetadata = imageMetadataDataList.get(0);
            imageMetadataArrayList.get(requestCode).update(imageMetadata);

            initializeUI();
        }
    }
}
