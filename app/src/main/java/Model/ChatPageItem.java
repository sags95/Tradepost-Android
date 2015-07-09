package Model;

import android.graphics.drawable.Drawable;

public class ChatPageItem {
    private String mTitle;
    private String mDetails;
    private Drawable mDrawable;

    public ChatPageItem(String text, String details, Drawable drawable) {
        this.mTitle = text;
        this.mDetails = details;
        this.mDrawable = drawable;
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