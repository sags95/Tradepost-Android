package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

import Model.OfferProcessAdapter;
import Model.OfferProcessItem;
import datamanager.userdata;
import webservices.OfferWebService;

/**
 * Created by HenryChiang on 15-07-18.
 */
public class OfferProcessMainFragment extends Fragment {

    private View rootView;
    private Button addCashBtn, addItemBtn;
    private ImageButton camBtn, galBtn;
    private EditText addCashEdit;
    private FragmentManager fragmentManager;
    private ImageView newItemImg;
    private OfferProcessDataPassingListener dataPassingListener;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<OfferProcessItem> tempList;
    private String itemText;
    public int item;
    public String itemname;
    private TextView itemTitle;
public static ArrayList<Integer> ITEMID;




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

        //
        addCashBtn = (Button)rootView.findViewById(R.id.offer_addcash);
        addCashBtn.setOnClickListener(addCashBtnListener);
        addCashEdit = (EditText)rootView.findViewById(R.id.offer_addcash_edit);

        //
        addItemBtn = (Button)rootView.findViewById(R.id.offer_process_addItem_btn);
        addItemBtn.setOnClickListener(addItemOnClickListener);

        //
        camBtn = (ImageButton)rootView.findViewById(R.id.offer_process_cam_btn);
        galBtn = (ImageButton)rootView.findViewById(R.id.offer_process_gallery_btn);
        camBtn.setOnClickListener(camBtnListener);
        galBtn.setOnClickListener(galleryBtnListener);
        newItemImg = (ImageView)rootView.findViewById(R.id.offer_process_new_img);

        //
        itemTitle = (TextView)rootView.findViewById(R.id.offer_process_list_main);

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
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                newItemImg.setImageBitmap(imageBitmap);


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
            }
        }
    }
    public void submitOffer()
    {
        int cash;
        int[] itemid=new int[ITEMID.size()];
        int i=0;
        for(int item: ITEMID)
        {
            itemid[i]=item;
            i++;
        }

        if(addCashEdit.getVisibility()==View.VISIBLE)
         cash =Integer.parseInt(addCashEdit.getText().toString());
        else
            cash=0;
        OfferWebService of=new OfferWebService();
        of.sendOffer(itemid, userdata.userid,OfferProcessActivity.userid,OfferProcessActivity.itemid,cash,null,OfferProcessActivity.iteamname);
    }

    public View.OnClickListener addCashBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(addCashEdit.getVisibility()==View.INVISIBLE) {
                addCashEdit.setVisibility(View.VISIBLE);

                addCashBtn.setText("REMOVE CASH");
            }else{
                addCashEdit.setText("");
                addCashEdit.setVisibility(View.INVISIBLE);
                addCashBtn.setText("ADD CASH");
            }


        }
    };

    public View.OnClickListener camBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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

