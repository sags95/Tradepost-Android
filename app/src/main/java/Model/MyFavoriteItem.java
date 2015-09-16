package Model;

import android.graphics.Bitmap;

import datamanager.ItemResult;

/**
 * Created by HenryChiang on 15-08-11.
 */
public class MyFavoriteItem {

    private String itemTitle;
    private Bitmap itemBitmap;
    private boolean favoriteStatus;
ItemResult ir;

    public ItemResult getIr() {
        return ir;
    }

    public void setIr(ItemResult ir) {
        this.ir = ir;
    }

    public MyFavoriteItem(String itemTitle,Bitmap itemBitmap, boolean favoriteStatus){
        this.itemTitle=itemTitle;
        this.itemBitmap=itemBitmap;
        this.favoriteStatus=favoriteStatus;
    }

    public MyFavoriteItem(String itemTitle){
        this.itemTitle=itemTitle;

    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public void setItemBitmap(Bitmap itemBitmap) {
        this.itemBitmap = itemBitmap;
    }

    public Bitmap getItemBitmap() {
        return itemBitmap;
    }

    public boolean isFavoriteStatus() {
        return favoriteStatus;
    }

    public void setFavoriteStatus(boolean favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }

}
