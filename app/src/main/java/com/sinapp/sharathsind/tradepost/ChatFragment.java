package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ListView;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Controllers.SendController;
import Model.CustomButton;
import Model.CustomCheckBox;
import Model.CustomEditText;
import Model.CustomTextView;
import Model.MessageAdapter;
import data.MessageClass;
import datamanager.userdata;

/**
 * Created by HenryChiang on 15-05-26.
 */
public class ChatFragment extends Activity {

public void updatemsg(MessageClass m)
{
    this.m.m.add(m);
   this.m.notifyDataSetChanged();

}
    public static boolean isAlive;
   // private View rootView;
    ImageView attachBtn;
    RelativeLayout sendBar, attachBar;
    Button cam, gallery;
    CustomEditText et;
    CustomButton send;
    private Toolbar toolbar;
    private static View headerDatails;

    //for dialog
    int cancel_deal_dialog_layout = R.layout.dialog_cancel_deal;
    LayoutInflater li;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private CustomCheckBox blockUser;
    ListView lv;
    public static int offerid;
    public  static Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_chat);

        Intent i=getIntent();
        intent=i;
        offerid=i.getIntExtra("offerid",0);
        setadapter(false);
        IntentFilter f=new IntentFilter("com.sinapp.sharathsind.chat."+offerid);
        MessageReceiver m=new MessageReceiver();
        this.registerReceiver(m,f);

        //headDatails
        View header = findViewById(R.id.chat_header);
        headerDatails = header.findViewById(R.id.chat_header_detail_bar);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(headerDatails.isShown()){
                    slideUp(getApplicationContext(), headerDatails);
                    headerDatails.setVisibility(View.GONE);
                }else{
                    headerDatails.setVisibility(View.VISIBLE);
                    slideDown(getApplicationContext(),headerDatails);
                }
            }
        });

       // rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        li=getLayoutInflater();

        //toolbar
        //toolbar = (Toolbar)rootView.findViewById(R.id.tool_bar);
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Chat");


        //buttons
        attachBtn = (ImageView)findViewById(R.id.attach_btn);
        cam = (Button)findViewById(R.id.attachment_cam);
        gallery = (Button)findViewById(R.id.attachment_folder);
        sendBar = (RelativeLayout)findViewById(R.id.send_bar);
        attachBar = (RelativeLayout)findViewById(R.id.attach_bar);


        attachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(ChatFragment.this, R.style.PopupMenu);
                PopupMenu popupMenu = new PopupMenu(wrapper, v);
                popupMenu.inflate(R.menu.chat_popup_menu);

                // Force icons to show
                Object menuHelper;
                Class[] argTypes;
                try {
                    Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
                    fMenuHelper.setAccessible(true);
                    menuHelper = fMenuHelper.get(popupMenu);
                    argTypes = new Class[] { boolean.class };
                    menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
                } catch (Exception e) {
                    // Possible exceptions are NoSuchMethodError and NoSuchFieldError
                    //
                    // In either case, an exception indicates something is wrong with the reflection code, or the
                    // structure of the PopupMenu class or its dependencies has changed.
                    //
                    // These exceptions should never happen since we're shipping the AppCompat library in our own apk,
                    // but in the case that they do, we simply can't force icons to display, so log the error and
                    // show the menu normally.

                    Log.w("ERROR", "error forcing menu icons to show", e);
                    popupMenu.show();
                    return;
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_photo_camera:
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, 0);
                                //  Toast.makeText(getActivity().getApplicationContext(), "Take photo Clicked", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item_photo_gallery:
                                 intent = new Intent(
                                        Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
                                // Toast.makeText(getActivity().getApplicationContext(), "choose photo Clicked", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item_cancel:
                                showCancelDealDialog();
                                // Toast.makeText(getActivity().getApplicationContext(), "cancel Clicked", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();


        et = (CustomEditText)findViewById(R.id.send_msg);
        send = (CustomButton)findViewById(R.id.send_btn);


            }
        });

        et = (CustomEditText)findViewById(R.id.send_msg);
        send = (CustomButton)findViewById(R.id.send_btn);


        /*
        //open or close attach bar
        attachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attachBar.getVisibility() != View.VISIBLE) {
                    RelativeLayout.LayoutParams sendBarparams = (RelativeLayout.LayoutParams) sendBar.getLayoutParams();
                    sendBarparams.addRule(Rel31+ativeLayout.ALIGN_PARENT_BOTTOM, 0);
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
        send.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
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



    }
 //   int offerid;
    BroadcastReceiver cv=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
setadapter(true);
        }
    };
    MessageAdapter m;
public void setadapter(Boolean b)
{
    lv= (ListView) findViewById(R.id.listview_chat);
    ArrayList<MessageClass> c=new ArrayList<>();
    SQLiteDatabase db=openOrCreateDatabase("tradepostdb.db", MODE_PRIVATE, null);
    // rootView = inflater.inflate(R.layout.notification_offer, container, false);
    Cursor c1=db.rawQuery("select * from m"+offerid,null);
    c1.moveToFirst();
    while(!c1.isAfterLast()) {
        MessageClass m1 = new MessageClass();

        m1.setMsg(c1.getString(c1.getColumnIndex("msg")));
        //m1.setSeen());
        //m1.setSent(new Date(c1.getString(c1.getColumnIndex("sent"))));

        m1.setUserid(Integer.parseInt(c1.getString(c1.getColumnIndex("userid"))));
  c.add(m1);
        c1.moveToNext();

    }
    c1.close();
    db.close();

    if(b) {

        m.m=c;
        m.notifyDataSetChanged();


    }
    else
    {
        m=new MessageAdapter(this,c);
        lv.setAdapter(m);

    }

}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Intent intent = new Intent(this, PictureMsg.class);
                intent.putExtra("BitmapImage", thumbnail);

            }
        }else{
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


    public void expandHeaderDetails(View v){
        headerDatails.setVisibility(headerDatails.isShown() ? View.GONE : View.VISIBLE);

    }

    public void slideDown(Context ctx, View v) {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.header_chat_slide_down);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);

            }
        }
    }

    public static void slideUp(Context ctx, View v) {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.header_chat_slide_down);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

        private void showCancelDealDialog(){
        final View dialogView = li.inflate(cancel_deal_dialog_layout, null,false);
        builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Want to Cancel The Deal?");
        blockUser = (CustomCheckBox)dialogView.findViewById(R.id.dialog_cancel_deal_checkBox);
        blockUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        builder.setNegativeButton("No", dismissListener);
        builder.setPositiveButton("Yes", blockUserOnClickListener);
        //set custom view.
        builder.setView(dialogView);

        //showing custom dialog.
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }


    private DialogInterface.OnClickListener blockUserOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Log.d("BLOCK", blockUser.isChecked() ? "yes":"no");

        }

    };

    private DialogInterface.OnClickListener dismissListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };



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

