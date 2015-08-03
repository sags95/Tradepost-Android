package com.sinapp.sharathsind.tradepost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import Model.OfferProcessAdapter;
import Model.OfferProcessItem;

/**
 * Created by HenryChiang on 15-07-18.
 */
public class OfferProcessListingFragment extends Fragment {

    private View rootView;
    private OfferProcessDataPassingListener dataPassingListener;
    private RecyclerView mRecyclerView;
    private OfferProcessAdapter mOfferProcessAdapter;
    private RecyclerView.LayoutManager mLayoutManager;




    //testing
    private String[] sample = {"item1","item2","item3","item4","item5","item6","item7"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("From Your Listing");
        dataPassingListener = (OfferProcessDataPassingListener)getActivity();





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_offer_process_listing, container, false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.offer_process_list);

        ArrayList<OfferProcessItem> itemsArrayList = new ArrayList<OfferProcessItem>();

        for (int i = 1; i <= 15; i++) {
            OfferProcessItem offerProcessItem = new OfferProcessItem("Item Title" + i);

            itemsArrayList.add(offerProcessItem);
        }

        mOfferProcessAdapter = new OfferProcessAdapter(itemsArrayList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mOfferProcessAdapter);
        applyLinearLayoutManager();

       // offerListView = (ListView)rootView.findViewById(R.id.offer_process_listview);
       // offerListView.setAdapter(new OfferListViewAdapter(getActivity(), sample));




        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.offer_process_listing_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle().toString()) {
            case "Save": {
                Log.d("in listing fragment", "go to main fragment");
                saveCheckedTitles();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }


    private void applyLinearLayoutManager(){
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void saveCheckedTitles(){
        dataPassingListener.passOfferProcessItem(mOfferProcessAdapter.getmData());
    }



}
