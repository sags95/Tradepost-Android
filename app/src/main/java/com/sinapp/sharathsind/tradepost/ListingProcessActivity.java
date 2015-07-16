package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apmem.tools.layouts.FlowLayout;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

import Model.RoundImageHelper;
import data.StringVector;
import datamanager.FileManager;
import datamanager.userdata;
import webservices.MainWebService;

/**
 * Created by HenryChiang on 15-05-31.
 */
public class ListingProcessActivity extends AppCompatActivity {

    final int MAX_NUM_TAGS = 5;
    int TAGS_COUNT = 0;
    private FlowLayout tagFlowLayout;
    private LinearLayout singleTagLayout;
    private String[] categories;
    private ImageView camera, folder, itemImg1, itemImg2, itemImg3, itemImg4;
    private int requestCodeCam = 0;
    private int requestCodeGal = 1;
    private int currentImgPos = 0;
    private Toolbar toolbar;


    private EditText tagInput;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_process);
        tagFlowLayout = (FlowLayout) findViewById(R.id.section5_tags);

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitle("Post Your Item");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //section 2
        itemImg1 = (ImageView) findViewById(R.id.section2_item_img1);
        itemImg2 = (ImageView) findViewById(R.id.section2_item_img2);
        itemImg3 = (ImageView) findViewById(R.id.section2_item_img3);
        itemImg4 = (ImageView) findViewById(R.id.section2_item_img4);

        camera = (ImageView) findViewById(R.id.section2_img_camera);
        folder = (ImageView) findViewById(R.id.section2_img_folder);
        Bitmap roundProImg = RoundImageHelper.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_section2_camera));
        camera.setImageBitmap(roundProImg);
        folder.setImageBitmap(roundProImg);
        camera.setOnClickListener(camBtnListener);
        folder.setOnClickListener(galleryBtnListener);

        //section 4
        EditText desEditText = (EditText) findViewById(R.id.section4_edit);


        //section 5
        tagInput = (EditText) findViewById(R.id.section5_edit);
        ImageView addTags = (ImageView) findViewById(R.id.section5_plus);
        addTags.setOnClickListener(addTagButtonListener);

        //using section 6 (Choose a category) plus button for testing
        ImageView testingBtn = (ImageView) findViewById(R.id.section6_plus);
        testingBtn.setOnClickListener(testingBtnListener);
        categories = getResources().getStringArray(R.array.category_array);
        Spinner spinner = (Spinner) findViewById(R.id.section6_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinner.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem item;

        item = menu.add("POST");
        item.setIcon(R.drawable.ic_toolbar_search);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getTitle().toString()) {
            case "POST": {
                Toast.makeText(getApplicationContext(), "POST?", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public View.OnClickListener addTagButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (tagInput.getText().length() > 0 && tagFlowLayout.getChildCount() < MAX_NUM_TAGS) {
                singleTagLayout = (LinearLayout) View.inflate(getApplicationContext(), R.layout.single_tag, null);
                singleTagLayout.setId(TAGS_COUNT);
                tagFlowLayout.addView(singleTagLayout);

                //Button for removing Tag
                ImageView cancelTag = (ImageView) findViewById(TAGS_COUNT).findViewById(R.id.tag_cancel_btn);
                //cancelTag.setId(TAGS_COUNT);
                cancelTag.setOnClickListener(tagCancelButtonListener);

                //Tag Name
                TextView tagName = (TextView) findViewById(TAGS_COUNT).findViewById(R.id.tag_name);
                //tagName.setId(TAGS_COUNT);
                tagName.setText(tagInput.getText().toString().trim());
                //tagName.setTag(tagInput.getText().toString());

                tagInput.setText("");
                TAGS_COUNT++;
                Log.d("Child Added", "Add 1, total: " + String.valueOf(tagFlowLayout.getChildCount()));
            }

        }
    };

    public View.OnClickListener tagCancelButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tagFlowLayout.removeView((View) v.getParent());
            Log.d("Child Removed", "Remove 1, total: " + String.valueOf(tagFlowLayout.getChildCount()));


        }

    };

    public ArrayList<String> getAllTagNames(FlowLayout fl) {
        ArrayList<String> tagNamesArrayList = new ArrayList<>();


        for (int i = 0; i < fl.getChildCount(); i++) {
            View child = fl.getChildAt(i);

            TextView getTagName = (TextView) (child.findViewById(R.id.tag_name));
            tagNamesArrayList.add(getTagName.getText().toString());
        }
        return tagNamesArrayList;

    }

    public View.OnClickListener testingBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<String> TagNames = getAllTagNames(tagFlowLayout);

            for (int i = 0; i < TagNames.size(); i++) {
                Log.d("Tag Name", ": " + TagNames.get(i));
            }
        }


    };

    public View.OnClickListener testingBtnListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Clicked", "You CLicked");

        }


    };

    public View.OnClickListener camBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, requestCodeCam);
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
            startActivityForResult( Intent.createChooser(intent, "Select File"), requestCodeGal);
        }
    };

    public void sendDataToServer(String itemTitle, String descrpition, String[] tags, Bitmap[] images, int condition, int userid, String category) {
        StringVector imageArray = new StringVector();
        int i = 0;
        for (Bitmap image : images) {
            imageArray.add(FileManager.encode(image));

            i++;

        }
        StringVector tag = new StringVector();
        for (String s : tags) {
            tag.add(s);
        }
        SoapObject object = new SoapObject("http://webser/", "additem");
        object.addProperty("itemname", itemTitle);
        object.addProperty("latitude", userdata.latitude);
        object.addProperty("latitude", userdata.longitude);
        object.addProperty("userid", userdata.userid);
        object.addProperty("category", category);
        object.addProperty("images", imageArray);
        object.addProperty("tags", tag);

        MainWebService.getMsg(object, "http://192.168.2.15:8084/TDserverWeb/Chat?wsdl", "");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK) {
            if(requestCode ==0){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                setImage(imageBitmap);

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
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                setImage(bm);
            }
        }
    }

    public void setImage(Bitmap imageBitmap){
        if(currentImgPos<5) {
            switch (currentImgPos) {
                case 0:
                    itemImg1.setImageBitmap(imageBitmap);
                    break;
                case 1:
                    itemImg2.setImageBitmap(imageBitmap);
                    break;
                case 2:
                    itemImg3.setImageBitmap(imageBitmap);
                    break;
                case 3:
                    itemImg4.setImageBitmap(imageBitmap);
                    break;
            }
            currentImgPos++;

        }
    }
}