package cos30017.a04p.imagemetadata;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MetadataEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metadata_edit);
        initializeUI();
    }

    private void initializeUI()
    {
        // get the data passed from previous activity
        Bundle dataBundle = getIntent().getExtras();
        String imageName = dataBundle.getString("name");
        Drawable image = ContextCompat.getDrawable(this, dataBundle.getInt("image"));

        // get handles to the views that we need to change -- change data
        EditText TextViewImageName = (EditText) findViewById(R.id.inputName);
        ImageView ImageViewImage = (ImageView) findViewById(R.id.imageViewImage);

        TextViewImageName.setText(imageName);
        ImageViewImage.setImageDrawable(image);
    }

    public void dateEditTextOnClickHandler(View v) {
        //new DatePickerDialog(this);
    }
}
