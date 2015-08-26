package com.sinapp.sharathsind.tradepost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import Model.CustomLinearLayoutManager;
import Model.EmptyRecyclerView;
import Model.OfferProcessAdapter;
import Model.OfferProcessItem;
import datamanager.userdata;

/**
 * Created by HenryChiang on 15-07-18.
 */
public class OfferProcessListingFragment extends Fragment {

    private View rootView,emptyView;
    private OfferProcessDataPassingListener dataPassingListener;
    private EmptyRecyclerView mRecyclerView;
    private OfferProcessAdapter mOfferProcessAdapter;
    private CustomLinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;





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
        emptyView = rootView.findViewById(R.id.offer_process_emptyView);
        mRecyclerView = (EmptyRecyclerView)rootView.findViewById(R.id.offer_process_list);

        //SwipeToRefresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.offer_process_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.ColorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("SwipeToRefresh", "Refreshing");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        ArrayList<OfferProcessItem> itemsArrayList = new ArrayList<OfferProcessItem>();

        for (int i = 0; i < userdata.i.size(); i++) {
            OfferProcessItem offerProcessItem = new OfferProcessItem(userdata.i.get(i).item.getItemname(),userdata.i.get(i).item.getItemid());

            itemsArrayList.add(offerProcessItem);
        }

        mOfferProcessAdapter = new OfferProcessAdapter(itemsArrayList);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (mRecyclerView == null || mRecyclerView.getChildCount() == 0) ?
                                0 : mRecyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(mLayoutManager.findFirstVisibleItemPosition()==0&&topRowVerticalPosition >= 0);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setEmptyView(emptyView);
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
        mLayoutManager = new CustomLinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void saveCheckedTitles(){
        dataPassingListener.passOfferProcessItem(mOfferProcessAdapter.getmData());
    }



}
