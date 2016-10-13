package cos30017.a04p.imagemetadata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Daniel on 8/10/2016.
 */

public class imageMetadata implements Parcelable {
    private int drawableImage; //required
    private String name; //required
    private String sourceUrl;
    private String keywords;
    private Date obtainedDate;
    private Boolean share;
    private String whoObtained; //required
    private int rating;

    /**
     * Required fields only constructor
     * @param drawableImage reference to image to draw
     * @param name Name of the image (cannot be empty)
     * @param whoObtained Who obtained this image? (has to be email address, cannot be empty)
     * @throws IllegalArgumentException throws exception for invalid data input
     */
    public imageMetadata(int drawableImage, String name, String whoObtained) throws IllegalArgumentException {
        setDrawableImage(drawableImage);
        setName(name);
        setWhoObtained(whoObtained);
    }

    /**
     * Populate all fields from constrictor
     * @param drawableImage
     * @param name
     * @param sourceUrl
     * @param keywords
     * @param obtainedDate
     * @param share
     * @param whoObtained
     * @param rating
     * @throws IllegalArgumentException throws exception for invalid data input
     */
    public imageMetadata(int drawableImage, String name, String sourceUrl, String keywords, Date obtainedDate, Boolean share, String whoObtained, int rating) throws IllegalArgumentException {
        setDrawableImage(drawableImage);
        setName(name);
        setSourceUrl(sourceUrl);
        setKeywords(keywords);
        setObtainedDate(obtainedDate);
        setShare(share);
        setWhoObtained(whoObtained);
        setRating(rating);
    }

    public int getDrawableImage() {
        return drawableImage;
    }

    public void setDrawableImage(int drawableImage) {
        this.drawableImage = drawableImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Date getObtainedDate() {
        return obtainedDate;
    }

    public void setObtainedDate(Date obtainedDate) {
        this.obtainedDate = obtainedDate;
    }

    public Boolean getShare() {
        return share;
    }

    public void setShare(Boolean share) {
        this.share = share;
    }

    public String getWhoObtained() {
        return whoObtained;
    }

    public void setWhoObtained(String whoObtained) {
        this.whoObtained = whoObtained;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    /** The following block of code parcels / unparcels data for distribution between activities */

    /** Describe the contents in the parcel --
     * interface forces implementation */
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(drawableImage);
        out.writeString(name);
        out.writeString(sourceUrl);
        out.writeString(keywords);
        out.writeLong(obtainedDate.getTime()); //no date type in Parcelable so convert to long
        out.writeInt(share ? 1 : 0); //if share == true, int == 1. since only boolean array in Parcelable
        out.writeString(whoObtained);
        out.writeInt(rating);
    }

    public static final Parcelable.Creator<imageMetadata> CREATOR = new Parcelable.Creator<imageMetadata>() {
        @Override
        public imageMetadata createFromParcel(Parcel in) {
            return new imageMetadata(in);
        }

        @Override
        public imageMetadata[] newArray(int size) {
            return new imageMetadata[size];
        }
    };

    /** Private constructor called internally by Parcelable Creator */
    private imageMetadata(Parcel in) {
        drawableImage = in.readInt();
        name = in.readString();
        sourceUrl = in.readString();
        keywords = in.readString();
        obtainedDate = new Date(in.readLong()); //no date type in Parcelable so convert to long
        share = in.readInt() != 0; //use int since only boolean array in Parcelable
        whoObtained = in.readString();
        rating = in.readInt();
    }
}
