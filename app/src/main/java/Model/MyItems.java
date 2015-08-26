package Model;

/**
 * Created by HenryChiang on 15-08-10.
 */
public class MyItems {

    private String itemTitle;
    private int itemId;
    private int userId;
    private String[] itemTags;


    public MyItems(String itemTitle,int itemId,int userId){
        this.itemTitle=itemTitle;
        this.itemId=itemId;
        this.userId=userId;

    }
    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }


}
