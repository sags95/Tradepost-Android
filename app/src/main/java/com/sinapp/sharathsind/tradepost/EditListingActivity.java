package com.sinapp.sharathsind.tradepost;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.apmem.tools.layouts.FlowLayout;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import Model.CustomEditText;
import Model.CustomSpinnerAdapter;
import Model.CustomTextView;
import Model.LimitedEditText;
import Model.RegisterWebService;
import Model.Variables;
import datamanager.userdata;
import webservices.MainWebService;

/**
 * Created by HenryChiang on 15-09-02.
 */
public class EditListingActivity extends AppCompatActivity {

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
    private CustomTextView tagsCount;
    private ColorStateList oldColors;

    //section1
    private LimitedEditText itemName;
    //section2

    //section3
    private SeekBar seekBar;
    //section4
    private LimitedEditText desEditText;
    //section5

    //section6
    private CustomSpinnerAdapter spinnerAdapter;
public int itemid;
    public ArrayList<String> tags;
    public ArrayList<Bitmap>bits;
    private CustomEditText tagInput;
    Spinner spinner;
ArrayList<Integer>offers;
ArrayList<Integer>userid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_process);
        tagFlowLayout = (FlowLayout) findViewById(R.id.section5_tags);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                offers=new ArrayList<Integer>();
                userid=new ArrayList<Integer>();
                ArrayList<String> itemInfoForEdit = getIntent().getStringArrayListExtra("itemToEdit");
                 itemid=Integer.parseInt(itemInfoForEdit.get(0));
               Cursor cursor= Constants.db.rawQuery("select * from offers where itemid="+itemid,null);

                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {
                    int status=cursor.getInt(cursor.getColumnIndex("status"));
                    if (status==0||status==1) {
                        offers.add(cursor.getInt(cursor.getColumnIndex("Offerid")));
                        userid.add(cursor.getInt(cursor.getColumnIndex("userid")));
                    }
                    cursor.moveToNext();
                }
                SoapObject soapObject=new SoapObject("http://webser/","getOfferDelete");
                soapObject.addProperty("i",itemid);
                Vector res= MainWebService.getMsg1(soapObject,"http://73.37.238.238:8084/TDserverWeb/EditdeleteItem?wsdl","http://webser/EditdeleteItem/getOfferDeleteRequest");
               if(res!=null) {
                   for (Object i : res) {
                       cursor = Constants.db.rawQuery("select * from offers where offerid=" + i, null);
                       cursor.moveToFirst();
                       int status = cursor.getInt(cursor.getColumnIndex("status"));
                       if (status == 0 || status == 1) {
                           offers.add(cursor.getInt(cursor.getColumnIndex("Offerid")));
                           userid.add(cursor.getInt(cursor.getColumnIndex("userid")));
                       }
                   }

               }


              //  int h = res.size();

                return null;
            }
        }.execute(null,null,null);
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Edit Your Item");
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
        itemName = (LimitedEditText) findViewById(R.id.section1_edit);
        itemName.setMaxLines(1);
        itemName.setMaxCharacters(70);
        final CustomTextView itemNameCharCount = (CustomTextView)findViewById(R.id.section1_char_count);
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

        camera.setOnClickListener(camBtnListener);
        folder.setOnClickListener(galleryBtnListener);

        //section 3
        seekBar = (SeekBar)findViewById(R.id.seekBar1);
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
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });



        //section 4
        desEditText = (LimitedEditText) findViewById(R.id.section4_edit);
        desEditText.setMaxLines(5);
        desEditText.setMaxCharacters(250);
        final CustomTextView itemDesCharCount = (CustomTextView)findViewById(R.id.section4_char_count);
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
        tagInput = (CustomEditText) findViewById(R.id.section5_edit);
        ImageView addTags = (ImageView) findViewById(R.id.section5_plus);
        addTags.setOnClickListener(addTagButtonListener);
        tagsCount = (CustomTextView)findViewById(R.id.section5_tag_count);
        oldColors =  tagsCount.getTextColors(); //save original colors

        //using section 6 (Choose a category)
        categories = getResources().getStringArray(R.array.category_array);
        spinner = (Spinner) findViewById(R.id.section6_spinner);
        spinnerAdapter = new CustomSpinnerAdapter(
                this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(getResources().getStringArray(R.array.category_array)));
        spinner.setAdapter(spinnerAdapter);


        //Filling item information for editing
        setItemInfoForEdit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem item;

        item = menu.add("Save");
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

        item = menu.add("Delete");
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);


        return super.onCreateOptionsMenu(menu);
    }
    String result;
    ProgressDialog pg;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getTitle().toString()){
            case "Save":
                delete(itemid);
                final EditText tv=(EditText)findViewById(R.id.section1_edit);
                final    EditText desc=(EditText)findViewById(R.id.section4_edit);
                final     SeekBar s=(SeekBar)findViewById(R.id.seekBar1);
                final      int i=s.getProgress();
                final String title=tv.getText().toString();
                final String description=desc.getText().toString();
                final String cat=spinner.getSelectedItem().toString();

//pg.show();
                new AsyncTask<String,String,String>()
                {
                    boolean cancel;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        if(title==null||title.trim().length()<=0)
                        {
                            new AlertDialog.Builder(EditListingActivity.this)
                                    .setTitle("Error")
                                    .setMessage("item title cannot be empty")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }
                                    })

                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                            cancel=true;
                            return;
                        }
                        if( getAllTagNames(tagFlowLayout).size()<=0)
                        {
                            new AlertDialog.Builder(EditListingActivity.this)
                                    .setTitle("Error")
                                    .setMessage("Please create atleast one tag")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }
                                    })

                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                            cancel=true;
                        }
                        else {
                            pg=ProgressDialog.show(EditListingActivity.this,"Please Wait","adding",false,false);
                            pg.setCancelable(false);
                            pg.setMessage("please wait..");
                        }
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        if(cancel)
                            return;
                        pg.dismiss();
                        Intent i = new Intent(getApplicationContext(),ListingProcessDoneActivity.class);
                        if(s.equals("success")){
                            i.putExtra("isSuccess", true);
                            startActivity(i);
                            finish();

                        }else{
                            i.putExtra("isSuccess", false);
                            startActivity(i);
                        }

                    }

                    @Override
                    protected String doInBackground(String... voids) {
                        if(cancel)
                            return null;
                        try {
                            tags=getAllTagNames(tagFlowLayout);
                            String[] tagarray = new String[tags.size()];

                            for(int i=0;i<tags.size();i++)
                            {
                                tagarray[i]=tags.get(i);
                            }

                            //     int i=s.get;
                            SoapPrimitive r= RegisterWebService.sendDataEdit(title, description, tagarray, bits.toArray(), i, userdata.userid, cat,itemid);
                            result=r.getValue().toString();
                            int i=0;
                            if(bits.size()==0)
                            {
                                Drawable myDrawable = ContextCompat.getDrawable(EditListingActivity.this, R.drawable.sample_img);
                                Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                                bits.add(myLogo);
                            }

                            for(Bitmap b:bits)
                            {
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                b.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] byteArray = stream.toByteArray();
                                sendDImage(Integer.parseInt(result),byteArray,i);

                                i++;
                            }
                            for(String t:tags)
                                sendtag(Integer.parseInt(result),t);
//
                            return "success";

                        } catch (Exception e) {
                            result=e.toString();
                            e.printStackTrace();
                            return "failed";

                        }

                    }
                }.execute(null, null);


                break;
            case "Delete":
                if(offers.size()>0)
                {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                    builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int k=0;
                            for(int i: offers)
                            {
                                SQLiteDatabase db=openOrCreateDatabase("tradepostdb.db", MODE_PRIVATE, null);
                                Cursor       c=Constants.db.rawQuery("select * from login", null);
                                c.moveToFirst();

                                //Variables.email=c.getString(c.getColumnIndex("email"));
                                Variables.username=c.getString(c.getColumnIndex("username"));
                                SoapObject obje=new SoapObject("http://webser/", "sendOfferDeclined");
                                obje.addProperty("msg",Variables.username+" has declined your offer");
                                obje.addProperty("offerid",i);
                                obje.addProperty("userid",userid.get(k));
                                k++;
                                obje.addProperty("username", Variables.username);
                                SoapPrimitive soapPrimitive1=MainWebService.getretryMsg(obje,"http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl","http://webser/OfferWebService/sendOfferDeclineRequest",0);
                                db.execSQL("update offers set status =2 where offerid ="+i);
                                db.close();
                                finish();
                            }
delete(itemid);




                        }
                    });
                    builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setMessage(" Offers exist for this item! Are you sure you want to delete? ");
                    builder.create().show();
                }
                else{
delete(itemid);
                }


                break;
        }

        return super.onOptionsItemSelected(item);

    }
    public SoapPrimitive sendDImage(int id,byte[] im,int pic)
    {
        SoapObject object = new SoapObject("http://webser/", "addimage");
        object.addProperty("itemid", id);
        object.addProperty("pic",pic);
        object.addProperty("image",im);
        return     MainWebService.getMsg(object, "http://73.37.238.238:8084/TDserverWeb/AddItems?wsdl", "http://webser/AddItems/addimageRequest");
    }
    public SoapPrimitive sendtag(int id,String im)
    {
        SoapObject object = new SoapObject("http://webser/", "addtag");
        object.addProperty("itemid", id);

        object.addProperty("tag",im);
        return     MainWebService.getMsg(object, "http://73.37.238.238:8084/TDserverWeb/AddItems?wsdl", "http://webser/AddItems/addtagRequest");
    }
    public void delete(int itemid)
    {
        SoapObject soapObject=new SoapObject("http://webser/","delete");
        soapObject.addProperty("i",itemid);
        SoapPrimitive res= MainWebService.getMsg(soapObject, "http://73.37.238.238:8084/TDserverWeb/EditdeleteItem?wsdl", "http://webser/EditdeleteItem/deleteRequest");


    }

    private void setItemInfoForEdit(){
        ArrayList<String> itemInfoForEdit = getIntent().getStringArrayListExtra("itemToEdit");

        //section1
        itemName.setText(itemInfoForEdit.get(1));
        //section2

        //section3
        seekBar.setProgress(convertCon(itemInfoForEdit.get(3)));
        //section4
        desEditText.setText(itemInfoForEdit.get(2));
        //section5
        String[] tagsToEdit = getIntent().getStringArrayExtra("tagsToEdit");
        for(String tag : tagsToEdit){
            addTagsEditListing(tag);
        }
        //section6
        spinner.setSelection(spinnerAdapter.getPosition(itemInfoForEdit.get(4)));


    }

    public int convertCon(String condition){
        int con=0;
        switch (condition){
            case "POOR":
                con=0;
                break;
            case "FAIR":
                con=1;
                break;
            case "GREAT":
                con=2;
                break;
            case "MINT":
                con=3;
                break;
            case "NEW":
                con=4;
                break;
        }

        return con;
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
                //tags.add(tagInput.getText().toString().trim());
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
        ArrayList<String> tagNames = new ArrayList<>();

        for (int i = 0; i < fl.getChildCount(); i++) {
            View child = fl.getChildAt(i);
            CustomTextView getTagName = (CustomTextView) (child.findViewById(R.id.tag_name));
            tagNames.add(getTagName.getText().toString().trim());
        }
        return tagNames;

    }

    public void addTagsEditListing(String tag) {

        LinearLayout singleTag = (LinearLayout) View.inflate(getApplicationContext(), R.layout.single_tag, null);

        singleTag.setId(TAGS_COUNT);
        //Button for removing Tag
        ImageView cancelTag = (ImageView)singleTag.findViewById(R.id.tag_cancel_btn);
        cancelTag.setId(TAGS_COUNT);
        cancelTag.setOnClickListener(tagCancelButtonListener);

        //Tag Name
        CustomTextView tagName = (CustomTextView) singleTag.findViewById(R.id.tag_name);
        //tagName.setId(TAGS_COUNT);
        tagName.setText(tag.trim());
        //tags.add(tagInput.getText().toString().trim());
        tagName.setTag(tag);
        tagFlowLayout.addView(singleTag);
        tagsCount.setText(String.valueOf(tagFlowLayout.getChildCount()));


    }


    public int DpToPx(int requireDp ){
        int dpValue = requireDp; // margin in dips
        float d = getResources().getDisplayMetrics().density;
        int margin = (int)(dpValue * d); // margin in pixels
        return margin; // margin in pixels

    }

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
}
