package Model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class ChatPageItem {
    private String mTitle;
    private String mDetails;
    private Bitmap mDrawable;
private  int offeritem;

    public int getOfferitem() {
        return offeritem;
    }

    public void setOfferitem(int offeritem) {
        this.offeritem = offeritem;
    }

    public ChatPageItem(String text, String details, Bitmap drawable,int of) {
        this.mTitle = details;
        this.mDetails = text;
        this.mDrawable = drawable;
        offeritem=of;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        this.mDetails = details;
    }

    public Bitmap getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Bitmap drawable) {
        this.mDrawable = drawable;
    }
}