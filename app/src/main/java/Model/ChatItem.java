package Model;

import android.graphics.Bitmap;

/**
 * Created by HenryChiang on 15-09-08.
 */
public class ChatItem {

    private String text,time;
    private Bitmap ImageSent,profilePic;
    private int userId;

    public ChatItem(int userId,String text,String time, Bitmap profilePic){
        this.text=text;
        this.time=time;
        this.profilePic=profilePic;
        this.userId=userId;

    }

    public ChatItem(int userId,String time, Bitmap ImageSent, Bitmap profilePic){
        this.time=time;
        this.ImageSent=ImageSent;
        this.profilePic=profilePic;
        this.userId=userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Bitmap getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Bitmap profilePic) {
        this.profilePic = profilePic;
    }

    public Bitmap getImageSent() {
        return ImageSent;
    }

    public void setImageSent(Bitmap imageSent) {
        ImageSent = imageSent;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
