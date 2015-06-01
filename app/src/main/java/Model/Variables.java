package Model;


import android.graphics.Bitmap;

public class Variables {
    public static Bitmap profilepic;
    public static boolean isFirstime;
    public static String username, email, id;
    private String drawerItemName;
    private Bitmap drawerItemImage;


    public Variables (String drawerItemName, Bitmap drawerItemImage){
        this.drawerItemName=drawerItemName;
        this.drawerItemImage=drawerItemImage;
    }

    public static Bitmap getProfilepic() {
        return profilepic;
    }


    public static void setProfilepic(Bitmap profilepic) {
        Variables.profilepic = profilepic;
    }

    public String getDrawerItemName() {
        return drawerItemName;
    }

    public Bitmap getDrawerItemImage() {
        return drawerItemImage;
    }

    public void setDrawerItemName(String drawerItemName) {
        this.drawerItemName = drawerItemName;
    }

    public void setDrawerItemImage(Bitmap drawerItemImage) {
        this.drawerItemImage = drawerItemImage;
    }

}

