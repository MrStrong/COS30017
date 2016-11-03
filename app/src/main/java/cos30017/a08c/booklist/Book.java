package cos30017.a08c.booklist;

/**
 * Created by Daniel on 2/11/2016.
 */

public class Book {
    private String name;
    private Integer rating;
    private Integer drawableID;

    Book(String name, Integer rating, Integer drawableID) {
        this.name = name;
        this.rating = rating;
        this.drawableID = drawableID;
    }

    public String getName() {
        return name;
    }

    public Integer getRating() {
        return rating;
    }

    public Integer getDrawableID() {
        return drawableID;
    }
}
