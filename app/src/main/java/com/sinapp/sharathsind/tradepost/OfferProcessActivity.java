package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

import Model.OfferProcessItem;

/**
 * Created by HenryChiang on 15-06-23.
 */
public class OfferProcessActivity extends AppCompatActivity implements OfferProcessDataPassingListener{

    private String[] sample = {"item1","item2","item3","item4","item5","item6","item7"};
    private Toolbar mToolbar;
    private FrameLayout frameContainer;
    private Fragment mainFragment, listingFragment;
    private FragmentManager fragmentManager;

    private Bundle tempBundle;

public static  int userid,itemid;
    public  static String iteamname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_process);

        tempBundle = new Bundle();

        //get Intent;
        Intent i= getIntent();
        ArrayList<String> itemDetailsToOffer = i.getStringArrayListExtra("itemToOfferProcess");
userid=Integer.parseInt(itemDetailsToOffer.get(2));

itemid=Integer.parseInt(itemDetailsToOffer.get(0));
        iteamname=itemDetailsToOffer.get(1);

        //
        fragmentManager = getSupportFragmentManager();

        //initialize fragments
        mainFragment = new OfferProcessMainFragment();
        listingFragment = new OfferProcessListingFragment();

        //
        frameContainer = (FrameLayout)findViewById(R.id.container);

        //ToolBar set up
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.ColorPrimary));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getTitle().toString()) {
            case "Submit Offer": {
                Toast.makeText(getApplicationContext(), "Submit?", Toast.LENGTH_SHORT).show();
                ((OfferProcessMainFragment)mainFragment).submitOffer();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed(){
        Fragment ft = getSupportFragmentManager().findFragmentById(R.id.container);

        if(ft instanceof OfferProcessListingFragment){
            if(!tempBundle.isEmpty()) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment newFrag = new OfferProcessMainFragment();
                newFrag.setArguments(tempBundle);
                transaction.replace(R.id.container, newFrag);
                Log.d("go to fragment", "go to main fragment amount not null");

                transaction.commit();

            }else{
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment newFrag = new OfferProcessMainFragment();
                transaction.replace(R.id.container, newFrag);
                Log.d("go to fragment", "go to main fragment null");

                transaction.commit();

            }

            /*
            transaction.replace(R.id.container, mainFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            */

        }else if(ft instanceof OfferProcessMainFragment){
            Log.d("go to fragment", "on main fragment");
            super.onBackPressed();
        }

    }


    @Override
    public void passString(String string,int condition){
        if(tempBundle.getString("Amount")!=null){
            Log.d("remove amount","amount to be removed: " + tempBundle.getString("Amount"));
            tempBundle.remove("Amount");
        }

        if(condition==1) {
            Log.d("add amount", "amount to be added: " + string);
            tempBundle.putString("Amount", string);
            Log.d("add amount", "added: " + tempBundle.getString("Amount"));
        }

        /*
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, newFrag);
        transaction.addToBackStack(null);
        transaction.commit();
        */
    }

    @Override
    public void passInt(int i){

    }

    @Override
    public void passBitmap(Bitmap bm){
    }

    @Override
    public void passOfferProcessItem(ArrayList<OfferProcessItem> offerProcessItemArrayList){
        if(tempBundle.getSerializable("title selected")!=null){
            tempBundle.remove("title selected");
            Log.d("pass selected titles", "delete previous bundle");


        }
        OfferProcessMainFragment.ITEMID =new ArrayList<>();
        for(OfferProcessItem o:offerProcessItemArrayList)
        {
            OfferProcessMainFragment.ITEMID.add(o.itemid);
        }

        tempBundle.putSerializable("title selected",offerProcessItemArrayList);
        Log.d("pass selected titles", "passing and put into bundle");


    }


}
