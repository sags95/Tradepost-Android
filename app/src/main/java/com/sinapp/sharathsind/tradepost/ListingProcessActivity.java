package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.ColorStateList;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.CustomSpinnerAdapter;
import Model.CustomTextView;
import Model.LimitedEditText;
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
    private Uri mImageUri;
    private TextView tagsCount;
    private ColorStateList oldColors;




    public ArrayList<String> tags;
public ArrayList<Bitmap>bits;
    private EditText tagInput;
    Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_process);
        tagFlowLayout = (FlowLayout) findViewById(R.id.section5_tags);

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Post Your Item");
        toolbar.setTitleTextColor(getResources().getColor(R.color.ColorPrimary));
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
        bits=new ArrayList<Bitmap>();

        //section 1
        LimitedEditText itemName = (LimitedEditText) findViewById(R.id.section1_edit);
        itemName.setMaxLines(1);
        itemName.setMaxCharacters(70);
        final TextView itemNameCharCount = (TextView)findViewById(R.id.section1_char_count);
        itemName.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                itemNameCharCount.setText(String.valueOf(s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        });

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

        //section 3
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar1);
        final LinearLayout seekBarLi = (LinearLayout)findViewById(R.id.seekBar_layout);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                for(int i=0;i<seekBarLi.getChildCount();i++){
                    if(i!=progress){
                        CustomTextView temp = (CustomTextView)seekBarLi.getChildAt(i);
                        temp.setTextColor(oldColors);
                    }
                }
                CustomTextView temp2 =(CustomTextView)seekBarLi.getChildAt(progress);
                temp2.setTextColor(getResources().getColor(R.color.fab_primaryColor));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //section 4
        LimitedEditText desEditText = (LimitedEditText) findViewById(R.id.section4_edit);
        desEditText.setMaxLines(5);
        desEditText.setMaxCharacters(250);
        final TextView itemDesCharCount = (TextView)findViewById(R.id.section4_char_count);
        desEditText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                itemDesCharCount.setText(String.valueOf(s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        });

        //section 5
        tagInput = (EditText) findViewById(R.id.section5_edit);
        ImageView addTags = (ImageView) findViewById(R.id.section5_plus);
        addTags.setOnClickListener(addTagButtonListener);
        tagsCount = (TextView)findViewById(R.id.section5_tag_count);
        oldColors =  tagsCount.getTextColors(); //save original colors

        //using section 6 (Choose a category)
        categories = getResources().getStringArray(R.array.category_array);
        spinner = (Spinner) findViewById(R.id.section6_spinner);
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(
                this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(getResources().getStringArray(R.array.category_array)));
        spinner.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem item;

        item = menu.add("POST");
        item.setIcon(R.drawable.ic_send);
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
                pg=ProgressDialog.show(ListingProcessActivity.this,"Please Wait","adding",false,false);
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
                    SoapPrimitive r= RegisterWebService.sendDataToServer(title,description,tagarray,bits.toArray(),i, userdata.userid,cat);
                    result=r.getValue().toString();
                    int i=0;
 for(Bitmap b:bits)
 {

 sendDImage(Integer.parseInt(result),FileManager.encode(b),i);

i++;
 }
      for(String t:tags)
          sendtag(Integer.parseInt(result),t);
//

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

    public SoapPrimitive sendDImage(int id,String im,int pic)
    {
        SoapObject object = new SoapObject("http://webser/", "addimage");
        object.addProperty("itemid", id);
        object.addProperty("pic",pic);
        object.addProperty("image",im);
        return     MainWebService.getMsg(object, "http://104.199.135.162:8084/TDserverWeb/AddItems?wsdl", "http://webser/AddItems/addimageRequest");
    }
    public SoapPrimitive sendtag(int id,String im)
    {
        SoapObject object = new SoapObject("http://webser/", "addtag");
        object.addProperty("itemid", id);

        object.addProperty("tag",im);
        return     MainWebService.getMsg(object, "http://104.199.135.162:8084/TDserverWeb/AddItems?wsdl", "http://webser/AddItems/addtagRequest");
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
                CustomTextView tagName = (CustomTextView) singleTagLayout.findViewById(R.id.tag_name);
                //tagName.setId(TAGS_COUNT);
                tagName.setText(tagInput.getText().toString().trim());
                tags.add(tagInput.getText().toString().trim());
                tagName.setTag(tagInput.getText().toString());

                tagInput.setText("");
                TAGS_COUNT++;
                tagFlowLayout.addView(singleTagLayout);
                tagsCount.setText(String.valueOf(tagFlowLayout.getChildCount()));
                Log.d("Child Added", "Add 1, total: " + String.valueOf(tagFlowLayout.getChildCount()));

            }

        }
    };

    public View.OnClickListener tagCancelButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tagFlowLayout.removeView((View) v.getParent());
            //tags.add(((TextView) v).getText().toString().trim());
            tagsCount.setText(String.valueOf(tagFlowLayout.getChildCount()));
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
            File photo=null;
            try {
                // place where to store camera taken picture
                photo = createTemporaryFile("temp", ".jpg");
                photo.delete();
            } catch (Exception e) {
                Log.v("camera", "Can't create file to take picture!");
                Toast.makeText(getApplicationContext(), "Please check SD card! Image shot is impossible!", Toast.LENGTH_SHORT);
            }
            mImageUri = Uri.fromFile(photo);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
            //start camera intent

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, requestCodeCam);
            }

        }
    };

    private File createTemporaryFile(String part, String ext) throws Exception
    {
        File tempDir= Environment.getExternalStorageDirectory();
        tempDir=new File(tempDir.getAbsolutePath()+"/.temp/");
        if(!tempDir.exists())
        {
            tempDir.mkdir();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    public Bitmap grabImage()
    {
        this.getContentResolver().notifyChange(mImageUri, null);
        ContentResolver cr = this.getContentResolver();
        Bitmap bitmap=null;
        try
        {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
            Log.d("cam", "Failed to load", e);
        }

        return bitmap;
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
    private static final String URL ="http://104.199.135.162:8084/TDserverWeb/AddItems?wsdl";
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


        return MainWebService.getMsg(object, "http://104.199.135.162:8084/TDserverWeb/AddItems?wsdl", "http://webser/AddItems/additemRequest");

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK) {
            if(requestCode ==0){
               /*
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                setImage(imageBitmap);
                bits.add(imageBitmap);
                */
                setImage(grabImage());
                bits.add(grabImage());
                //... some code to inflate/create/find appropriate ImageView to place grabbed image

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
            bits.add(imageBitmap);
            currentImgPos++;

        }
    }
}