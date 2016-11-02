package cos30017.a06p.booklist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Book> bookArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
    }

    private void initializeUI() {
        createBooks();

        //use custom row adapter for ListView
        ListView listView = (ListView) findViewById(R.id.bookList);
        listView.setAdapter(new RowIconAdapter());
    }

    /**
     * create dummy data for list
     */
    private void createBooks() {
        bookArrayList.add(new Book(getString(R.string.book1_name), 4, R.drawable.the_blood_mirror));
        bookArrayList.add(new Book(getString(R.string.book2_name), 5, R.drawable.the_four_lengendary));
        bookArrayList.add(new Book(getString(R.string.book3_name), 3, R.drawable.the_girl_on_the_train));
        bookArrayList.add(new Book(getString(R.string.book4_name), 3, R.drawable.the_whistler));
        bookArrayList.add(new Book(getString(R.string.book5_name), 2, R.drawable.the_wrong_sideof_goodby));
        bookArrayList.add(new Book(getString(R.string.book6_name), 3, R.drawable.this_was_a_man));
        bookArrayList.add(new Book(getString(R.string.book7_name), 5, R.drawable.a_distant_journy));
        bookArrayList.add(new Book(getString(R.string.book8_name), 5, R.drawable.small_great_things));
        bookArrayList.add(new Book(getString(R.string.book9_name), 5, R.drawable.badd_motherf_cker));
        bookArrayList.add(new Book(getString(R.string.book10_name), 5, R.drawable.the_subtle_art_of_not_giving_a_f_ck));
        bookArrayList.add(new Book(getString(R.string.book11_name), 4, R.drawable.try_hard));
        bookArrayList.add(new Book(getString(R.string.book12_name), 5, R.drawable.miss_peregrines_home_for_peculair_children));
        bookArrayList.add(new Book(getString(R.string.book13_name), 4, R.drawable.pharaoh));
        bookArrayList.add(new Book(getString(R.string.book14_name), 4, R.drawable.his_erotic_obsession));
        bookArrayList.add(new Book(getString(R.string.book15_name), 0, R.drawable.no_mans_land));
        bookArrayList.add(new Book(getString(R.string.book16_name), 4, R.drawable.kept));
    }

    private String[] getBookNames() {
        String[] bookNames = new String[bookArrayList.size()];

        for (int i=0; i < bookArrayList.size(); i++){
            bookNames[i] = bookArrayList.get(i).getName();
        }
        return bookNames;
    }


    /**
     * custom row adapter for ListView
     */
    class RowIconAdapter extends ArrayAdapter<String> {
        public RowIconAdapter() {
            super(MainActivity.this, R.layout.listrow, R.id.row_label, getBookNames());
        }

        public View getView(int pos, View cView, ViewGroup parent)
        {
            View row = super.getView(pos, cView, parent);

            //set additional fields
            ImageView icon = (ImageView) row.findViewById(R.id.row_icon);
            RatingBar ratingBar = (RatingBar) row.findViewById(R.id.row_ratingbar);

            icon.setImageResource(bookArrayList.get(pos).getDrawableID());
            ratingBar.setRating(bookArrayList.get(pos).getRating());


            String bookName = bookArrayList.get(pos).getName();
            Log.i("RowIconAdapter", "BookName Position"+pos+" "+bookName);

            return row;
        }
    }
}
