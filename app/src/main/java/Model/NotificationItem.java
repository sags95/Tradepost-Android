package Model;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

/**
 * Created by HenryChiang on 15-07-12.
 */
public class NotificationItem {

    private String userPlaceholder, msg;
    private int viewType;
    private String[] defaultMsg = {
            "has offered you an item.",
            "has accepted your offer.",
            "has declined your offer.",
            "has delivered feedback.",
            "Tradepost custom alert."
    };

    //viewType:
    // 0 = trade offer.
    // 1 = trade accepted.
    // 2 = trade declined.
    // 3 = feedback.
    // 4 = tradepost custom alert.
    // 5

    public NotificationItem (String userPlaceholder, int viewType){
        this.userPlaceholder = userPlaceholder;
        this.viewType = viewType;

    }

    //not used.
    public String setMsg(){
        String tempMsg="";
        // create a bold StyleSpan to be used on the SpannableStringBuilder
        switch (viewType){
            case 0:
                tempMsg = defaultMsg[0];
                break;
            case 1:
                tempMsg = defaultMsg[1];
                break;
            case 2:
                tempMsg = defaultMsg[2];
                break;
            case 3:
                tempMsg = defaultMsg[3];
                break;
            case 4:
                tempMsg = defaultMsg[4];
                break;
        }

        SpannableStringBuilder sb = new SpannableStringBuilder(userPlaceholder);
        sb.setSpan(new StyleSpan(Typeface.ITALIC), 0, userPlaceholder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
        this.msg = sb.toString();

        return sb.toString();
    }

    public String getMsg() {
        return msg;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getUserPlaceholder() {
        return userPlaceholder;
    }

    public void setUserPlaceholder(String userPlaceholder) {
        this.userPlaceholder = userPlaceholder;
    }

    public String getDefaultMsg(int i) {
        return defaultMsg[i];
    }
}
