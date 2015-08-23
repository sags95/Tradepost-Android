package datamanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sharathsind on 2015-06-09.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    final String tag="db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "appStorage.db";

    //------------------------------ ACRONYM TABLE --------------------------------------

    public static final String marketPlace_listing_table = "marketPlace_listing_table";

    //ACRONYM TABLE COLUMNS names
    public static final String listing_itemId = "listing_itemId";
    public static final String listing_itemTitle = "listing_itemTitle";
    public static final String listing_itemImg = "listing_itemImg";
    public static final String listing_userId = "listing_userId";
    public static final String listing_userPic = "listing_userPic";
    public static final String listing_time = "listing_time";
    public static final String listing_tags = "listing_tags";




    private static final String create_marketPlace_listing = "create table " + marketPlace_listing_table + "(" + listing_itemId + " varchar primary key," +
            listing_itemTitle + " text not null," + listing_itemImg + " BLOB," +  listing_userId + " text not null," + listing_tags + " text not null," + listing_time + " datetime not null," + listing_userPic + " BLOB" + ")";

    //------------------------------------------------------------------------------------



    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context)
    {
        // Create Database itself
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(tag, "DatabaseUtil: Creating the database - constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(tag,"DatabaseUtil: Inside onCreate() ");
        db.execSQL(create_marketPlace_listing);
        Log.d(tag,"DatabaseUtil: Creating the Table MarketPlace Listing \n SQL:" + create_marketPlace_listing);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(DatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        Log.d(tag,"DatabaseUtil: Starting to destroy data ");

        db.execSQL("DROP TABLE IF EXISTS " + marketPlace_listing_table);
        Log.d(tag, "DatabaseUtil: Finished to destroy data ");

        onCreate(db);
        Log.d(tag, "DatabaseUtil: Finished to calling onCreate");
    }
}
