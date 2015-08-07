package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apmem.tools.layouts.FlowLayout;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.RegisterWebService;
import Model.RoundImageHelper;
import Model.Variables;
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
ArrayList<String>imfiles;

    public ArrayList<String> tags;
public ArrayList<Bitmap>bits;
    private EditText tagInput;
    Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_process);
        tagFlowLayout = (FlowLayout) findViewById(R.id.section5_tags);
imfiles=new ArrayList<>();
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Post Your Item");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tags=new ArrayList<String>();
        //bits=new ArrayList<Bitmap>();
        //section 2
        itemImg1 = (ImageView) findViewById(R.id.section2_item_img1);
        itemImg2 = (ImageView) findViewById(R.id.section2_item_img2);
        itemImg3 = (ImageView) findViewById(R.id.section2_item_img3);
        itemImg4 = (ImageView) findViewById(R.id.section2_item_img4);
///spinner=(Spinner)findViewById(R.id.sp)
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
         spinner = (Spinner) findViewById(R.id.section6_spinner);
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
String result;
 ProgressDialog pg;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getTitle().toString()) {
            case "POST": {
             final   EditText tv=(EditText)findViewById(R.id.section1_edit);
            final    EditText desc=(EditText)findViewById(R.id.section4_edit);
           final     SeekBar s=(SeekBar)findViewById(R.id.seekBar1);
         final      int i=s.getProgress();
                final String title=tv.getText().toString();
                final String description=desc.getText().toString();
                final String cat=spinner.getSelectedItem().toString();
                pg=ProgressDialog.show(ListingProcessActivity.this,"pleasewait","adding",false,false);
                pg.setCancelable(false);
                pg.setMessage("please wait..");
//pg.show();
               new AsyncTask<String,String,String>()
                {

                   @Override
                   protected void onPostExecute(String s) {
                       super.onPostExecute(s);
                       pg.dismiss();
                   }

                   @Override
                    protected String doInBackground(String... voids) {
                try {
String []tagarray=new String[tags.size()];
         for(int i=0;i<tags.size();i++)
         {
             tagarray[i]=tags.get(i);
         }
               //     int i=s.get;
                    SoapPrimitive r= RegisterWebService. sendDataToServer(title,description,tagarray,null,i, userdata.userid,cat,0);
                    result=r.getValue().toString();
                    int i=0;
                    Added_files.result=Integer.parseInt(result);
                    Added_files.files=imfiles;


      for(String t:tags)
          sendtag(Integer.parseInt(result),t);
//
startActivity(new Intent(ListingProcessActivity.this,Added_files.class));
                    finish();
                } catch (Exception e) {
                   result=e.toString();
                    e.printStackTrace();
                }
                return "";
                    }
                }.execute(null,null);
        //        a.execute(" ","","");

     //           Toast.makeText(getApplicationContext(), "POST?", Toast.LENGTH_SHORT).show();
       //         break;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    public SoapPrimitive sendtag(int id,String im)
    {
        SoapObject object = new SoapObject("http://webser/", "addtag");
        object.addProperty("itemid", id);

        object.addProperty("tag",im);
        return     MainWebService.getMsg(object, "http://192.168.2.15:8084/TDserverWeb/AddItems?wsdl", "http://webser/AddItems/addtagRequest");
    }

    public View.OnClickListener addTagButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (tagInput.getText().length() > 0 && tagFlowLayout.getChildCount() < MAX_NUM_TAGS) {
                singleTagLayout = (LinearLayout) View.inflate(getApplicationContext(), R.layout.single_tag, null);
                singleTagLayout.setId(TAGS_COUNT);
                //Button for removing Tag
                ImageView cancelTag = (ImageView)singleTagLayout.findViewById(R.id.tag_cancel_btn);
                cancelTag.setId(TAGS_COUNT);
                cancelTag.setOnClickListener(tagCancelButtonListener);

                //Tag Name
                TextView tagName = (TextView) singleTagLayout.findViewById(R.id.tag_name);
                //tagName.setId(TAGS_COUNT);
                tagName.setText(tagInput.getText().toString().trim());
                tags.add(tagInput.getText().toString().trim());
                tagName.setTag(tagInput.getText().toString());

                tagInput.setText("");
                TAGS_COUNT++;
                tagFlowLayout.addView(singleTagLayout);
                Log.d("Child Added", "Add 1, total: " + String.valueOf(tagFlowLayout.getChildCount()));

            }

        }
    };

    public View.OnClickListener tagCancelButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tagFlowLayout.removeView((View) v.getParent());
            //tags.add(((TextView) v).getText().toString().trim());
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
          Uri  fileUri = getOutputMediaFileUri();
            takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT,fileUri);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, requestCodeCam);
            }
        }
    };
    static File file;
    private  Uri getOutputMediaFileUri(){
        file=getOutputMediaFile();
        imfiles.add(file.toString());
        return Uri.fromFile(file);
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be s1o+hared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
                Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }

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

    private static final String SOAP_ACTION = "http://webser/AddItems/additemRequest";
    private static final String METHOD_NAME = "additem";
    private static final String NAMESPACE = "http://webser/";
    private static final String URL ="http://192.168.2.15:8084/TDserverWeb/AddItems?wsdl";
    public SoapPrimitive sendDataToServer(String itemTitle, String descrpition, String[] tags, Object[] images, int condition, int userid, String category) {

        SoapObject object = new SoapObject(NAMESPACE, METHOD_NAME);
        object.addProperty("itemname", itemTitle);
        object.addProperty("description",descrpition);
        object.addProperty("latitude", userdata.latitude);
        object.addProperty("longitude", userdata.longitude);
        object.addProperty("userid", userdata.userid);
        object.addProperty("category", category);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
        headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
        envelope.setOutputSoapObject(object);
        MarshalFloat m=new MarshalFloat();
        m.register(envelope);
        new MarshalBase64().register(envelope);
        HttpTransportSE ht = new HttpTransportSE( URL);
        try {
            ht.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            //  String res = response.ge().toString();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return MainWebService.getMsg(object, "http://192.168.2.15:8084/TDserverWeb/AddItems?wsdl", "http://webser/AddItems/additemRequest");

    }
    public static Bitmap lessResolution (String filePath, int width, int height) {
        int reqHeight = height;
        int reqWidth = width;
        BitmapFactory.Options options = new BitmapFactory.Options();

        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK) {
            if(requestCode ==0){
            //    Bundle extras = data.getExtras();
                Bitmap imageBitmap = lessResolution(file.getAbsolutePath(),100,100);
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
          //  bits.add(imageBitmap);
            currentImgPos++;

        }
    }
}