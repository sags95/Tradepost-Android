package com.sinapp.sharathsind.tradepost;

import android.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import Model.CustomButton;
import datamanager.FileManager;
import datamanager.userdata;
import de.hdodenhof.circleimageview.CircleImageView;
import webservices.MainWebService;

/**
 * Created by HenryChiang on 15-08-25.
 */
public class CreateAccountPicActivity extends AppCompatActivity {
ImageView cam,gal;
    CustomButton create;
    CircleImageView im;
    ProgressDialog pb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_propic);
cam=(ImageView)findViewById(R.id.create_account_img_camera);
gal=(ImageView)findViewById(R.id.create_account_img_folder);
 im=(CircleImageView)findViewById(R.id.create_account_propic);
create=(CustomButton)findViewById(R.id.create_account_signUp_btn);
        cam.setOnClickListener(camBtnListener);
        gal.setOnClickListener(galleryBtnListener);
        Permission permission = new Permission(this, null);
        if (permission.checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || permission.checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            permission.askPermission(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

            //toolbar
        }
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BitmapDrawable bw=(BitmapDrawable)im.getDrawable();
                new AsyncTask<String,String,String>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
pb=new ProgressDialog(CreateAccountPicActivity.this,ProgressDialog.STYLE_SPINNER);
                        pb.show();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        pb.dismiss();
                        startActivity(new Intent(CreateAccountPicActivity.this,Welcome.class));
                    }

                    @Override
                    protected String doInBackground(String... params) {
                        SoapObject obj = new SoapObject("http://webser/", "editprofilepic");
                        obj.addProperty("userid",userdata.userid);
                        obj.addProperty("picture",FileManager.encode(bw.getBitmap()));


                        SoapPrimitive sp = MainWebService.getretryMsg(obj, "http://73.37.238.238:8084/TDserverWeb/getUserName?wsdl", "http://webser/getUserName/editprofilepicRequest", 0);

                        return null;
                    }
                }.execute(null,null,null);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode ==0){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                im.setImageBitmap(imageBitmap);


            }else if(requestCode == 1){
                Uri selectedImageUri = data.getData();
                String[] projection = { MediaStore.MediaColumns.DATA };
                Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);

                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                cursor.close();
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                im.setImageBitmap(bm);
            }
        }
    }
    public View.OnClickListener camBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, 0);
            }
        }
    };

    public View.OnClickListener galleryBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*

            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto , 0);
            */

            Intent intent = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
        }
    };

}
