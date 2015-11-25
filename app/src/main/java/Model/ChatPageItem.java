package Model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.Date;

public class ChatPageItem  implements  Comparable {
    private String mTitle;
    private String mDetails;
    private Bitmap mDrawable;
private  int offeritem;
public Date sent;
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

    @Override
    public int compareTo(Object another) {

        if(sent ==null)
        {
            if(((ChatPageItem)another).sent==null)
            {
                return 0;
            }
            else
            {
                return -1;
            }

        }
        if(((ChatPageItem)another).sent==null)
        {
            return 1;

        }
        return    sent.compareTo(((ChatPageItem)another).sent);
    }
}