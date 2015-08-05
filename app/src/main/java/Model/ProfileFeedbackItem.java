package Model;

/**
 * Created by HenryChiang on 15-08-05.
 */
public class ProfileFeedbackItem {

    private String username;
    private String userFeedback;
    private float ratingBarValue;

    public ProfileFeedbackItem(String name, String feedback, float rating){
        this.username = name;
        this.userFeedback = feedback;
        this.ratingBarValue = rating;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserFeedback() {
        return userFeedback;
    }

    public void setUserFeedback(String userFeedback) {
        this.userFeedback = userFeedback;
    }

    public float getRatingBarValue() {
        return ratingBarValue;
    }

    public void setRatingBarValue(float ratingBarValue) {
        this.ratingBarValue = ratingBarValue;
    }
}
