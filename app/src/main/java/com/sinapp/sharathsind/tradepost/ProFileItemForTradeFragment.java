package com.sinapp.sharathsind.tradepost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Model.CustomLinearLayoutManager;
import Model.EmptyRecyclerView;
import Model.MyFavoriteAdapter;
import Model.MyFavoriteItem;
import Model.RecyclerViewOnClickListener;

/**
 * Created by HenryChiang on 15-08-11.
 */
public class ProFileItemForTradeFragment extends Fragment {


    private View rootView,emptyView;
    private EmptyRecyclerView mRecyclerView;
    private MyFavoriteAdapter itemForTradeAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private android.support.v7.widget.LinearLayoutManager mLayoutManager;
    final List<MyFavoriteItem> myItems  = addItem("hello");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_itemfortrade, container, false);
        emptyView = rootView.findViewById(R.id.itemForTrade_emptyView);
        mRecyclerView = (EmptyRecyclerView)rootView.findViewById(R.id.itemForTrade_recyclerview);

        //SwipeToRefresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.itemForTrade_swipe_refresh_layout);
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
                mSwipeRefreshLayout.setEnabled(mLayoutManager.findFirstVisibleItemPosition()==0&&topRowVerticalPosition >= 0);
                super.onScrolled(recyclerView, dx, dy);
            }
        });



        itemForTradeAdapter = new MyFavoriteAdapter(myItems);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setEmptyView(emptyView);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerViewOnClickListener(getActivity(), new RecyclerViewOnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                })
        );

        mRecyclerView.setAdapter(itemForTradeAdapter);
        applyLinearLayoutManager();


        return rootView;
    }

    public List<MyFavoriteItem> addItem(String title) {
        List<MyFavoriteItem> items = new ArrayList<MyFavoriteItem>();
        items.add(new MyFavoriteItem(title));
        items.add(new MyFavoriteItem("User"));
        items.add(new MyFavoriteItem("Long Long title"));
        items.add(new MyFavoriteItem("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyFavoriteItem("Super Long Item Title"));
        items.add(new MyFavoriteItem("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyFavoriteItem("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyFavoriteItem("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyFavoriteItem("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyFavoriteItem("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyFavoriteItem("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyFavoriteItem("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyFavoriteItem("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyFavoriteItem("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyFavoriteItem("Very Very Very Long Item Title is Created By Henry"));




        return items;
    }
    private void applyLinearLayoutManager(){
        mLayoutManager = new CustomLinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

}
