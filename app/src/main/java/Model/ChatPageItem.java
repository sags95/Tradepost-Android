package Model;

import android.graphics.drawable.Drawable;

public class ChatPageItem {
    private String mTitle;
    private String mDetails;
    private Drawable mDrawable;
private  int offeritem;

    public int getOfferitem() {
        return offeritem;
    }

    public void setOfferitem(int offeritem) {
        this.offeritem = offeritem;
    }

    public ChatPageItem(String text, String details, Drawable drawable,int of) {
        this.mTitle = text;
        this.mDetails = details;
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

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
    }
}