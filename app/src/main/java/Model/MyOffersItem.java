package Model;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by HenryChiang on 15-07-13.
 */
public class MyOffersItem {

    private String offerItemTitle;
    private Bitmap offerItemImg, offerItemUserImg;
    private int offerType,offerItemAction;

    public MyOffersItem(String offerItemTitle, Bitmap offerItemUserImg, Bitmap offerItemImg, int offerItemAction, int offerType){
        this.offerItemTitle = offerItemTitle;
        this.offerItemUserImg = offerItemUserImg;
        this.offerItemImg = offerItemImg;
        this.offerItemAction = offerItemAction;
        this.offerType = offerType;
    }

    public String getOfferItemTitle() {
        return offerItemTitle;
    }

    public void setOfferItemTitle(String offerItemTitle) {
        this.offerItemTitle = offerItemTitle;
    }

    public Bitmap getOfferItemImg() {
        return offerItemImg;
    }

    public void setOfferItemImg(Bitmap offerItemImg) {
        this.offerItemImg = offerItemImg;
    }

    public Bitmap getOfferItemUserImg() {
        return offerItemUserImg;
    }

    public void setOfferItemUserImg(Bitmap offerItemUserImg) {
        this.offerItemUserImg = offerItemUserImg;
    }

    public int getOfferItemAction() {
        return offerItemAction;
    }

    public void setOfferItemAction(int offerItemAction) {
        this.offerItemAction = offerItemAction;
    }

    public int getOfferType() {return offerType;}

    public void setOfferType(int offerType) {this.offerType = offerType;}


}
