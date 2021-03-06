package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import Model.CustomButton;
import Model.CustomEditText;
import Model.CustomTextView;
import Model.OfferProcessAdapter;
import Model.OfferProcessItem;
import datamanager.FileManager;
import datamanager.userdata;
import webservices.OfferWebService;

/**
 * Created by HenryChiang on 15-07-18.
 */
public class OfferProcessMainFragment extends Fragment {

    private View rootView;
    private CustomButton addCashBtn, addItemBtn;
    private ImageView camBtn, galBtn;
    private CustomEditText addCashEdit;
    public static  CustomEditText g;
    private FragmentManager fragmentManager;
    private ImageView newItemImg;
    private OfferProcessDataPassingListener dataPassingListener;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<OfferProcessItem> tempList;
    private String itemText;
    public int item;
    public static String itemname;
    private CustomTextView itemTitle;
public static ArrayList<Integer> ITEMID;
public static boolean b,b1;
static int cash ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
ITEMID=new ArrayList<>();
        dataPassingListener = (OfferProcessDataPassingListener)getActivity();
        fragmentManager = getActivity().getSupportFragmentManager();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Make An Offer");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_offer_process_main, container, false);
g=(CustomEditText)rootView.findViewById(R.id.offer_process_new_img_title);
        //
        addCashBtn = (CustomButton)rootView.findViewById(R.id.offer_addcash);
        addCashBtn.setOnClickListener(addCashBtnListener);
        addCashEdit = (CustomEditText)rootView.findViewById(R.id.offer_addcash_edit);
addCashEdit.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
cash=Integer.parseInt(addCashEdit.getText().toString());


    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
});
        //
    g.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
    itemname=s.toString();
        }
    });
        addItemBtn = (CustomButton)rootView.findViewById(R.id.offer_process_addItem_btn);
        addItemBtn.setOnClickListener(addItemOnClickListener);

        //
        camBtn = (ImageView)rootView.findViewById(R.id.offer_process_cam_btn);
        galBtn = (ImageView)rootView.findViewById(R.id.offer_process_gallery_btn);
        camBtn.setOnClickListener(camBtnListener);
        galBtn.setOnClickListener(galleryBtnListener);
        newItemImg = (ImageView)rootView.findViewById(R.id.offer_process_new_img);

        //
        itemTitle = (CustomTextView)rootView.findViewById(R.id.offer_process_list_main);

        /*
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.offer_process_list_main);
        tempList = new ArrayList<OfferProcessItem>();
        tempList.add(new OfferProcessItem("sample"));
        mOfferProcessMainAdapter = new OfferProcessMainAdapter(tempList);
        mRecyclerView.setAdapter(mOfferProcessMainAdapter);
        mRecyclerView.setHasFixedSize(true);
        applyLinearLayoutManager();
        */



        Bundle args = getArguments();
        if(args!=null ){
            if(args.getString("Amount")!=null) {
                    addCashEdit.setVisibility(View.VISIBLE);
                    addCashEdit.setText(args.getString("Amount"));
                addCashBtn.setText("REMOVE CASH");
            }

            if(args.getSerializable("title selected")!=null){
                String data="";
                ArrayList<OfferProcessItem> offerProcessItems = (ArrayList<OfferProcessItem>) args.getSerializable("title selected");
                tempList = offerProcessItems;

                for(int i =0;i<offerProcessItems.size();i++){
                    if(offerProcessItems.get(i).isSelected()){
                        data = data + "\n" + offerProcessItems.get(i).getItemTitle();
                        tempList.add(new OfferProcessItem(offerProcessItems.get(i).toString(),offerProcessItems.get(i).itemid));
ITEMID.add(offerProcessItems.get(i).itemid);
                        itemTitle.setText(data);
                    }
                }

                Log.d("titles that checked", data);

            }

        }

        //



        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.offer_process_main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode ==0){
                Bitmap thumbnail = grabImage();

                newItemImg.setImageBitmap(thumbnail);
                OfferProcessActivity.bit=thumbnail;
                  b=true;

            }else if(requestCode == 1){
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
                newItemImg.setImageBitmap(bm);
                cursor.close();
                OfferProcessActivity.bit=bm;
            b=true;
            }
        }
    }
    public void submitOffer()
    {
     String  itemA=itemname;
        if(b && (itemA==null||itemA.length()<=0))
        {
            return;
        }
        else if(!b&(itemA!=null&&itemA.trim().length()>=1))
        {

        return;

        }

        int[] itemid=new int[ITEMID.size()];
        String s=addCashBtn.getText().toString();
      if( !b1)
      {
          cash=0;
      }
        else{
          cash=Integer.parseInt(addCashEdit.getText().toString());
      }
        String img=null;
       if(b)
        img= FileManager.encode(((BitmapDrawable) newItemImg.getDrawable()).getBitmap());
        int i=0;
        for(int item: ITEMID)
        {
            itemid[i]=item;
            i++;
        }


            OfferWebService of=new OfferWebService();
        of.sendOffer(itemid, userdata.userid, OfferProcessActivity.userid, OfferProcessActivity.itemid, cash, img, OfferProcessActivity.iteamname, itemA);
    }

    public View.OnClickListener addCashBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(addCashEdit.getVisibility()==View.INVISIBLE) {
                addCashEdit.setVisibility(View.VISIBLE);

                addCashBtn.setText("REMOVE CASH");
                b1=true;
            }else{
                addCashEdit.setText("");
                addCashEdit.setVisibility(View.INVISIBLE);
                addCashBtn.setText("ADD CASH");
                b1=false;
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
Uri mImageUri;
    public Bitmap grabImage()
    {
        getActivity().getContentResolver().notifyChange(mImageUri, null);
        ContentResolver cr = getActivity().getContentResolver();

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageUri.getPath(), options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(mImageUri.getPath(), options);


        return bm;
    }

    public View.OnClickListener camBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //takePictureIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            File photo=null;
            try {
                // place where to store camera taken picture
                photo = createTemporaryFile("temp", ".jpg");
                photo.delete();
            } catch (Exception e) {
                Log.v("camera", "Can't create file to take picture!");
            //Toast.makeText(getApplicationContext(), "Please check SD card! Image shot is impossible!", Toast.LENGTH_SHORT);
            }
            mImageUri = Uri.fromFile(photo);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
            //start camera intent

            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
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

    public View.OnClickListener addItemOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(addCashEdit.getVisibility()==View.VISIBLE&&addCashEdit.getText().length()>0){
                dataPassingListener.passString(addCashEdit.getText().toString(), 1);
            }else if(addCashEdit.getVisibility()==View.GONE){
                dataPassingListener.passString("",0);
            }
            Fragment newFrag = new OfferProcessListingFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, newFrag);
            transaction.commit();

            /*
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, listingFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            */


        }
    };

}

