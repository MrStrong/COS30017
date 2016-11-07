package cos30017.a08c.booklist;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Book> bookArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
    }

    private void initializeUI() {
        createBooks();

        //use custom row adapter for ListView
        //ListView listView = (ListView) findViewById(R.id.bookList);
        //listView.setAdapter(new RowIconAdapter());

        //use RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter and input data
        mAdapter = new MyAdapter(bookArrayList);
        mRecyclerView.setAdapter(mAdapter);

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

//android doc method
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<Book> mBooks;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageViewRowIcon;
            private TextView textViewRowLabel;
            private RatingBar ratingBarRowRating;
            private Book book;


            public ViewHolder(View v) {
                super(v);

                imageViewRowIcon = (ImageView) v.findViewById(R.id.row_icon);
                textViewRowLabel = (TextView) v.findViewById(R.id.row_label);
                ratingBarRowRating = (RatingBar) v.findViewById(R.id.row_ratingbar);
            }

            public void bindBook(Book book) {
                this.book = book;

                //imageViewRowIcon.setImageResource(book.getDrawableID());
                textViewRowLabel.setText(book.getName());
                ratingBarRowRating.setRating(book.getRating());
                new imageLoader(book.getDrawableID()).execute(this);
            }
        }

        // Use an AsyncTask to load the slow images in a background thread
        //TODO change from ViewHolder to ImageView to make more generic as it's only being used for the icon
        class imageLoader extends AsyncTask<ViewHolder, Void, Bitmap> {
            ViewHolder v;
            int drawableID;

            public imageLoader(int drawableID) {
                this.drawableID = drawableID;
            }

            @Override
            protected Bitmap doInBackground(ViewHolder... params) {
                v = params[0];

                return ((BitmapDrawable)getDrawable(drawableID)).getBitmap();
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                //super.onPostExecute(result);
//                if (v.position == position) {
//                    // If this item hasn't been recycled already, hide the
//                    // progress and set and show the image
//                    v.progress.setVisibility(View.GONE);
//                    v.icon.setVisibility(View.VISIBLE);
//                    v.icon.setImageBitmap(result);
//
//                }
                v.imageViewRowIcon.setImageBitmap(result);
            }
        }//.execute(holder);



        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<Book> mBooks) {
            this.mBooks = mBooks;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.bindBook(mBooks.get(position));

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mBooks.size();
        }
    }
    


//lecture recycle method
//    /**
//     * custom row adapter for ListView
//     */
//    class RowIconAdapter extends ArrayAdapter<String> {
//        public RowIconAdapter() {
//            super(MainActivity.this, R.layout.listrow, R.id.row_label, getBookNames());
//        }
//
//        public View getView(int pos, View cView, ViewGroup parent)
//        {
//
//           // if (cView == null){
//                //lecture method
//                MenuInflater mInflater = getMenuInflater();
//                cView = mInflater.inflate(R.layout.listrow, parent, false);
//                //cView = super.getView(pos, cView, parent); //default row
//          //  }
//
//
//
//            //set additional fields
//            ImageView icon = (ImageView) cView.findViewById(R.id.row_icon);
//            RatingBar ratingBar = (RatingBar) cView.findViewById(R.id.row_ratingbar);
//
//            icon.setImageResource(bookArrayList.get(pos).getDrawableID());
//            ratingBar.setRating(bookArrayList.get(pos).getRating());
//
//
//            String bookName = bookArrayList.get(pos).getName();
//            Log.i("RowIconAdapter", "BookName Position"+pos+" "+bookName);
//
//            return cView;
//        }
//    }


// create and destroy method
//    /**
//     * custom row adapter for ListView
//     */
//    class RowIconAdapter extends ArrayAdapter<String> {
//        public RowIconAdapter() {
//            super(MainActivity.this, R.layout.listrow, R.id.row_label, getBookNames());
//        }
//
//        public View getView(int pos, View cView, ViewGroup parent)
//        {
//            View row = super.getView(pos, cView, parent);
//
//            //set additional fields
//            ImageView icon = (ImageView) row.findViewById(R.id.row_icon);
//            RatingBar ratingBar = (RatingBar) row.findViewById(R.id.row_ratingbar);
//
//            icon.setImageResource(bookArrayList.get(pos).getDrawableID());
//            ratingBar.setRating(bookArrayList.get(pos).getRating());
//
//
//            String bookName = bookArrayList.get(pos).getName();
//            Log.i("RowIconAdapter", "BookName Position"+pos+" "+bookName);
//
//            return row;
//        }
//    }
}
