package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.parse.codec.binary.Base64;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import datamanager.FileManager;
import webservices.MainWebService;


public class Added_files extends Activity {
ProgressDialog progress;
    public static ArrayList<String>files;
    public static int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_files);
        progress=new ProgressDialog(this);
        progress.setMessage("uploading images");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setCanceledOnTouchOutside(false);
        progress.setProgress(0);
        progress.show();
       new AsyncTask<String,String,String>()
       {
           public SoapPrimitive sendDImage(int id,String im,int pic)
           {
               SoapObject object = new SoapObject("http://webser/", "addimage");
               object.addProperty("itemid", id);
               object.addProperty("pic",pic);
               object.addProperty("image",im);
               return     MainWebService.getMsg(object, "http://192.168.2.15:8084/TDserverWeb/AddItems?wsdl", "http://webser/AddItems/addimageRequest");
           }

           @Override
           protected void onProgressUpdate(String... values) {
               super.onProgressUpdate(values);
               progress.setProgress(Integer.parseInt(values[0]));
           }

           @Override
           protected String doInBackground(String... strings) {
               int i=0;

               for(String f: files)
               {
                   BitmapFactory.Options options=new BitmapFactory.Options();
                   options.inPreferredConfig= Bitmap.Config.RGB_565;
                  Bitmap b= BitmapFactory.decodeFile(f);
                   b=getResizedBitmap(b,960,720);
                   float h=b.getHeight();
                   float w=b.getWidth();
                   ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                   b.compress(Bitmap.CompressFormat.PNG, 70, baos);
                   b.recycle();
                   byte [] b1=baos.toByteArray();
                   ContentValues cv=new ContentValues();
                   String temp= android.util.Base64.encodeToString(b1, android.util.Base64.DEFAULT);
                  b1=null;
                   sendDImage(result, temp, i);
                  // b.recycle();
               ///    publishProgress(String.valueOf((float)files.size()/(float)i+1*100));
                   i++;
               }
               return null;
           }
           public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
               int width = bm.getWidth();
               int height = bm.getHeight();
               float scaleWidth = ((float) newWidth) / width;
               float scaleHeight = ((float) newHeight) / height;
               // CREATE A MATRIX FOR THE MANIPULATION
               Matrix matrix = new Matrix();
               // RESIZE THE BIT MAP
               matrix.postScale(scaleWidth, scaleHeight);

               // "RECREATE" THE NEW BITMAP
               Bitmap resizedBitmap = Bitmap.createBitmap(
                       bm, 0, 0, width, height, matrix, false);
               return resizedBitmap;
           }
           public String get(File file)
           {
               StringBuilder sb=new StringBuilder((int)(file.length()/3*4));
FileInputStream fin = null;
try {
    fin = new FileInputStream(file);
    // Max size of buffer
    int bSize = 3 * 512;
    // Buffer
    byte[] buf = new byte[bSize];
    // Actual size of buffer
    int len = 0;

    while((len = fin.read(buf)) != -1) {
        byte[] encoded = Base64.encodeBase64(buf);

        // Although you might want to write the encoded bytes to another 
        // stream, otherwise you'll run into the same problem again.
        sb.append(new String(buf, 0, len));
    }
} catch(Exception e) {

}

String base64EncodedFile = sb.toString();


return base64EncodedFile;
           }
       }.execute(null,null,null);

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(String res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(res, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig= Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(res, options);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_added_files, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
