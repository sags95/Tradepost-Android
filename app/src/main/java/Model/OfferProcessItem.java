package Model;

import java.io.Serializable;

/**
 * Created by HenryChiang on 15-07-19.
 */
public class OfferProcessItem  implements Serializable {

    private String itemTitle;

    private boolean isSelected;

    public OfferProcessItem (String itemTitle){
        this.itemTitle = itemTitle;
    }

    public OfferProcessItem (String itemTitle, boolean isSelected){
        this.itemTitle = itemTitle;
        this.isSelected = isSelected;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }


}
