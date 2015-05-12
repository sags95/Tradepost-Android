package datamanager;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.util.List;

/**
 * Created by sharathsind on 2015-05-01.
 */
public class userdata {
    public boolean checkUser(final String user, final String password, final String email, final boolean isfb, final boolean isGplus, final File profie) {
        boolean b = true;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("user");
        query.whereEqualTo("email", email);
        query.whereEqualTo("isfb", isfb);
        query.whereEqualTo("isgplus", isGplus);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (parseObjects.size() == 0) {
                    addUser(user, password, email, isfb, isGplus, profie);
                    //  return false;
                }


            }
        });

        return b;
    }

    public void addUser(String user, String password, String email, boolean isfb, boolean isGooolePlus, File f) {
        String s;
        ParseObject newUser = new ParseObject("user");
        newUser.put("username", user);
        newUser.put("password", password);
        newUser.put("email", email);
        newUser.put("isfb", isfb);
        newUser.put("isGplus", isGooolePlus);
        newUser.put("profilepicture", f);

        newUser.saveInBackground();


    }


}
