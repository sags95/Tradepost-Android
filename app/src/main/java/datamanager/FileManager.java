package datamanager;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by sharathsind on 2015-06-07.
 */
public class FileManager {
    public static boolean createdir(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return f.mkdir();


        }
        return false;


    }

    public static boolean deleteFile(String file) {
        File f = new File(file);
        if(!f.exists()) {
return false;
        }
      return f.delete();

    }
public static Bitmap decode(String encoded)
{
   byte[] b= Base64.decode(encoded,Base64.DEFAULT);
    Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
  return bitmap;


}
    public static  String encode(Bitmap profilepic)
    {
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        profilepic.compress(Bitmap.CompressFormat.JPEG,70, baos);
        byte [] b1=baos.toByteArray();
        ContentValues cv=new ContentValues();
        String temp= Base64.encodeToString(b1, Base64.DEFAULT);
return temp;
    }

}
