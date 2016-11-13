package cos30017.a04p.imagemetadata18sp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MetadataEditActivity extends AppCompatActivity {

    private ImageMetadata imageMetadata;

    private String pattern = "dd/MM/yyyy";
    private SimpleDateFormat format = new SimpleDateFormat(pattern);

    ImageView ImageViewImage;
    EditText EditViewImageName;
    EditText EditViewObtainedDate;
    RatingBar RatingBarImage;
    EditText EditTextInputLocations;
    EditText EditTextKeywords;
    EditText EditTextObtainer;
    ToggleButton ToggleButtonShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metadata_edit);
        initializeUI();
    }

    private void initializeUI()
    {
        getDataFromIntent();

        // get handles to the views that we need to change -- change data
        ImageViewImage = (ImageView) findViewById(R.id.imageViewImage);
        EditViewImageName = (EditText) findViewById(R.id.inputName);
        EditViewObtainedDate = (EditText) findViewById(R.id.inputObtainedDate);
        RatingBarImage = (RatingBar) findViewById(R.id.ratingBarImage);
        EditTextInputLocations = (EditText) findViewById(R.id.inputLocations);
        EditTextKeywords = (EditText) findViewById(R.id.inputKeywords);
        EditTextObtainer = (EditText) findViewById(R.id.inputObtainer);
        ToggleButtonShare = (ToggleButton) findViewById(R.id.toggleButtonShare);

        //populate values
        ImageViewImage.setImageResource(imageMetadata.getDrawableImage());
        EditViewImageName.setText(imageMetadata.getName());
        RatingBarImage.setRating(imageMetadata.getRating());
        EditTextInputLocations.setText(imageMetadata.getSourceUrl());
        EditTextKeywords.setText(imageMetadata.getKeywords());
        EditTextObtainer.setText(imageMetadata.getWhoObtained());
        ToggleButtonShare.setChecked(imageMetadata.getShare());

        EditViewObtainedDate.setText(format.format(imageMetadata.getObtainedDate()));
    }

    private void getDataFromIntent() {
        // get the data passed from previous activity
        Intent intent = getIntent();
        ArrayList<ImageMetadata> imageMetadataArrayList = intent.getParcelableArrayListExtra("IMAGE_METADATA");
        imageMetadata = imageMetadataArrayList.get(0);
    }

    @Override
    public void onBackPressed() {
        Log.i("MetadataEditActivity", "Back Button Pressed");
        try {
            saveMetadata();
        } catch (Exception e) {
            //if input error do not go back
            return;
        }
        Intent resultIntent = new Intent();

        ArrayList<ImageMetadata> dataList = new ArrayList<ImageMetadata>();
        dataList.add(imageMetadata);
        resultIntent.putParcelableArrayListExtra("IMAGE_METADATA", dataList);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    private void saveMetadata() throws Exception{
        //validate image name is not blank
        if(EditViewImageName.getText().toString().equals("")) {
            showAlertDialog(getString(R.string.dialog_alert_imput_error_title), getString(R.string.dialog_alert_name_blank));
            throw new Exception();
        } else {
            imageMetadata.setName(EditViewImageName.getText().toString());
        }
        imageMetadata.setRating((int)RatingBarImage.getRating());
        imageMetadata.setSourceUrl(EditTextInputLocations.getText().toString());
        imageMetadata.setKeywords(EditTextKeywords.getText().toString());

        //validate email address
        //https://developer.android.com/reference/android/util/Patterns.html
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher( EditTextObtainer.getText().toString() ).matches()){
            showAlertDialog(getString(R.string.dialog_alert_imput_error_title), getString(R.string.dialog_alert_name_email));
            throw new Exception();
        } else {
            imageMetadata.setWhoObtained(EditTextObtainer.getText().toString());
        }


        imageMetadata.setShare(ToggleButtonShare.isChecked());
        try {
            imageMetadata.setObtainedDate(format.parse(EditViewObtainedDate.getText().toString()));
        } catch (Exception e) {
            showAlertDialog(getString(R.string.dialog_alert_imput_error_title), getString(R.string.dialog_alert_name_date));
            throw new Exception();
        }
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MetadataEditActivity.this).create();
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


    //TODO upgrade EditText date with GUI datapicker
    public void dateEditTextOnClickHandler(View v) {
        //new DatePickerDialog(this);
    }
}
