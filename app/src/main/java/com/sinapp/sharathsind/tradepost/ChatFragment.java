package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import Controllers.SendController;

/**
 * Created by HenryChiang on 15-05-26.
 */
public class ChatFragment extends Fragment {


    public static boolean isAlive;
    private View rootView;
    ImageView attachBtn;
    RelativeLayout sendBar, attachBar;
    Button cam, gallery;
    EditText et;
    Button send;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        //toolbar
        //toolbar = (Toolbar)rootView.findViewById(R.id.tool_bar);
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Chat");


        //buttons
        attachBtn = (ImageView)rootView.findViewById(R.id.attach_btn);
        cam = (Button)rootView.findViewById(R.id.attachment_cam);
        gallery = (Button)rootView.findViewById(R.id.attachment_folder);
        sendBar = (RelativeLayout)rootView.findViewById(R.id.send_bar);
        attachBar = (RelativeLayout)rootView.findViewById(R.id.attach_bar);

        attachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_photo_camera:
                                Toast.makeText(getActivity().getApplicationContext(), "Take photo Clicked", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item_photo_gallery:
                                Toast.makeText(getActivity().getApplicationContext(), "choose photo Clicked", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item_cancel:
                                Toast.makeText(getActivity().getApplicationContext(), "cancel Clicked", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.inflate(R.menu.chat_popup_menu);

                popupMenu.show();

            }
        });

        et = (EditText)rootView.findViewById(R.id.send_msg);
        send = (Button)rootView.findViewById(R.id.send_btn);


        /*
        //open or close attach bar
        attachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attachBar.getVisibility() != View.VISIBLE) {
                    RelativeLayout.LayoutParams sendBarparams = (RelativeLayout.LayoutParams) sendBar.getLayoutParams();
                    sendBarparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                    sendBar.setLayoutParams(sendBarparams);
                    attachBar.setVisibility(View.VISIBLE);
                } else {
                    RelativeLayout.LayoutParams sendBarparams = (RelativeLayout.LayoutParams) sendBar.getLayoutParams();
                    sendBarparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
                    sendBar.setLayoutParams(sendBarparams);
                    attachBar.setVisibility(View.GONE);
                }
            }
        });
        */

        //send
        SendController sendController = new SendController(this, et);
        send.setOnClickListener(sendController);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (et.getText().toString().length()==0) {
                    send.setEnabled(false);
                    send.setClickable(false);
                    send.setAlpha(0.2f);
                } else {
                    send.setEnabled(true);
                    send.setClickable(true);
                    send.setAlpha(1f);
                }
            }
        });


        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), 1);

            }
        });




        //

        return rootView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Intent intent = new Intent(getActivity(), PictureMsg.class);
                intent.putExtra("BitmapImage", thumbnail);

            }
        }else{
                Uri selectedImageUri = data.getData();
                String[] projection = { MediaStore.MediaColumns.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, projection, null, null, null);
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
            Intent intent = new Intent(getActivity(), PictureMsg.class);
            intent.putExtra("BitmapImage", bm);

            }


        }

    @Override
    public void onStart() {

        super.onStart();
        isAlive = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isAlive = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isAlive = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isAlive = true;
    }


}

