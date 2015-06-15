package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import Controllers.SendController;

/**
 * Created by HenryChiang on 15-05-26.
 */
public class ChatActivity extends NewToolBar {

    public static boolean isAlive;
    ImageView attachBtn;
    RelativeLayout sendBar, attachBar;
    Button cam, gallery;
    EditText et;
    Button send;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_chat_room);


        attachBtn = (ImageView) findViewById(R.id.attach_btn);
        cam = (Button) findViewById(R.id.attachment_cam);
        gallery = (Button) findViewById(R.id.attachment_folder);
        sendBar = (RelativeLayout) findViewById(R.id.send_bar);
        attachBar = (RelativeLayout) findViewById(R.id.attach_bar);

        //open or close attach bar
        attachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(attachBar.getVisibility() != View.VISIBLE) {
                    RelativeLayout.LayoutParams sendBarparams = (RelativeLayout.LayoutParams) sendBar.getLayoutParams();
                    sendBarparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                    sendBar.setLayoutParams(sendBarparams);
                    attachBar.setVisibility(View.VISIBLE);
                }else {
                    RelativeLayout.LayoutParams sendBarparams = (RelativeLayout.LayoutParams) sendBar.getLayoutParams();
                    sendBarparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
                    sendBar.setLayoutParams(sendBarparams);
                    attachBar.setVisibility(View.GONE);
                }
            }
        });


        et = (EditText) findViewById(R.id.send_msg);
        SendController sendController = new SendController(this, et);
        send = (Button) findViewById(R.id.send_btn);
        send.setOnClickListener(sendController);
        cam.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                       Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                       startActivityForResult(intent, 0);


                                   }
                               }

        );
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(   Intent.createChooser(intent, "Select File"),1);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Intent intent = new Intent(this, PictureMsg.class);
                intent.putExtra("BitmapImage", thumbnail);

            }
        }
else{
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
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
            Intent intent = new Intent(this, PictureMsg.class);
            intent.putExtra("BitmapImage", bm);

            }


        }

    @Override
    protected void onStart() {

        super.onStart();
        isAlive = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isAlive = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isAlive = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isAlive = true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isAlive = true;
    }
}

