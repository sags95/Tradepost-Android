package Model;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by HenryChiang on 15-07-13.
 */
public class MyOffersItem {

    private String offersItemTitle;
    private Bitmap offersItemImg;
    private int offersCount;


    public MyOffersItem(String offersItemTitle,Bitmap offersItemImg, int offersCount){
        this.offersItemTitle = offersItemTitle;
        this.offersItemImg = offersItemImg;
        this.offersCount=offersCount;
    }

    public String getOffersItemTitle() {
        return offersItemTitle;
    }

    public void setOffersItemTitle(String offerItemTitle) {
        this.offersItemTitle = offerItemTitle;
    }

    public Bitmap getOffersItemImg() {
        return offersItemImg;
    }

    public void setOffersItemImg(Bitmap offerItemImg) {
        this.offersItemImg = offerItemImg;
    }


    public int getOffersCount() {
        return offersCount;
    }

    public void setOffersCount(int offersCount) {
        this.offersCount = offersCount;
    }
}
