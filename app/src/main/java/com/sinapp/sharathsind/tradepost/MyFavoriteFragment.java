package com.sinapp.sharathsind.tradepost;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.CustomLinearLayoutManager;
import Model.DividerItemDecoration;
import Model.EmptyRecyclerView;
import Model.MyFavoriteAdapter;
import Model.MyFavoriteItem;
import Model.MyOffersItem;
import Model.SwipeableRecyclerViewTouchListener;
import datamanager.ItemResult;
import datamanager.userdata;
import webservices.FavouriteWebService;
import webservices.ItemWebService;

/**
 * Created by HenryChiang on 15-08-11.
 */
public class MyFavoriteFragment extends Fragment {

    private View rootView,emptyView;
    private EmptyRecyclerView mRecyclerView;
    private MyFavoriteAdapter myFavoriteAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CustomLinearLayoutManager mLayoutManager;
    final List<MyFavoriteItem> myItems  = addItem("hello");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(getActivity());
        View v = inflator.inflate(R.layout.toolbar_custom_title, null);
        TextView title1 = (TextView) v.findViewById(R.id.toolbar_title1);
        TextView title2 = (TextView) v.findViewById(R.id.toolbar_title2);
        title1.setText("My Favorites");
        title2.setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(v);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_favorites, container, false);
        emptyView = rootView.findViewById(R.id.myFav_emptyView);
        mRecyclerView = (EmptyRecyclerView)rootView.findViewById(R.id.myFav_recyclerview);

        //SwipeToRefresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.myFav_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.ColorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("SwipeToRefresh", "Refreshing");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

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
                mSwipeRefreshLayout.setEnabled(mLayoutManager.findFirstVisibleItemPosition() == 0 && topRowVerticalPosition >= 0);
                super.onScrolled(recyclerView, dx, dy);
            }
        });



        myFavoriteAdapter = new MyFavoriteAdapter(myItems);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setEmptyView(emptyView);
        mRecyclerView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mRecyclerView.setAdapter(myFavoriteAdapter);
        applyLinearLayoutManager();

        /*
        mRecyclerView.addOnItemTouchListener(
                new RecyclerViewOnClickListener(getActivity(), new RecyclerViewOnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                })
        );
        */

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(mRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }
public void remove( int id){
    Cursor c=Constants.db.rawQuery("select * from fav where itemid="+id,null);
    c.moveToFirst();

    FavouriteWebService.removefavouInts(c.getInt(c.getColumnIndex("id")));
    c.close();
}
                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                  MyFavoriteItem item=  myItems.get(position);
                                    myItems.remove(position);
                                    remove(item.getIr().item.getItemid());
                                    myFavoriteAdapter.notifyItemRemoved(position);
                                }
                                myFavoriteAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    MyFavoriteItem item=  myItems.get(position);
                                    myItems.remove(position);
                                    remove(item.getIr().item.getItemid());
                                    myFavoriteAdapter.notifyItemRemoved(position);
                                }
                                myFavoriteAdapter.notifyDataSetChanged();
                            }
                        });

        mRecyclerView.addOnItemTouchListener(swipeTouchListener);


        return rootView;
    }

    public List<MyFavoriteItem> addItem(String title) {
        List<MyFavoriteItem> items = new ArrayList<MyFavoriteItem>();
//        SQLiteDatabase db=getActivity().openOrCreateDatabase("tradepostdb.db", getActivity().MODE_PRIVATE, null);
        Cursor c=Constants.db.rawQuery("select * from fav ",null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            int itemid=c.getInt(c.getColumnIndex("itemid"));
            ItemResult ir= ItemWebService.getItem(itemid);
            Bitmap bitmap=MyOffersTabTwo.getBitmapFromURL("http://73.37.238.238:8084/TDserverWeb/images/items/" + itemid + "/0.png");


            int count=0;
if(ir!=null) {
    MyFavoriteItem item = new MyFavoriteItem(ir);
    item.setItemTitle(ir.item.getItemname());
    item.setItemBitmap(bitmap);
    items.add(item);

}
            c.moveToNext();
        }



c.close();

        return items;
    }
    private void applyLinearLayoutManager(){
        mLayoutManager = new CustomLinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
}
