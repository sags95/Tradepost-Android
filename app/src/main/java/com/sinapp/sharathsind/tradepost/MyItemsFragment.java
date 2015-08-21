package com.sinapp.sharathsind.tradepost;

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
import Model.EmptyRecyclerView;
import Model.MyItems;
import Model.MyItemsAdapter;
import Model.RecyclerViewOnClickListener;

/**
 * Created by HenryChiang on 15-08-10.
 */
public class MyItemsFragment extends Fragment {

    private View rootView,emptyView;
    private EmptyRecyclerView mRecyclerView;
    private MyItemsAdapter myItemsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CustomLinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(getActivity());
        View v = inflator.inflate(R.layout.toolbar_custom_title, null);
        TextView title1 = (TextView) v.findViewById(R.id.toolbar_title1);
        TextView title2 = (TextView) v.findViewById(R.id.toolbar_title2);
        title1.setText("My Items");
        title2.setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(v);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_items, container, false);
        emptyView = rootView.findViewById(R.id.myItems_emptyView);
        mRecyclerView = (EmptyRecyclerView)rootView.findViewById(R.id.myItems_recyclerview);

        final List<MyItems> myItems  = addItem("hello");

        //SwipeToRefresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.myItems_swipe_refresh_layout);
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



        myItemsAdapter = new MyItemsAdapter(myItems);
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

        mRecyclerView.setAdapter(myItemsAdapter);
        applyLinearLayoutManager();


        return rootView;
    }

    public List<MyItems> addItem(String title) {
        List<MyItems> items = new ArrayList<MyItems>();
        items.add(new MyItems(title));
        items.add(new MyItems("User"));
        items.add(new MyItems("Long Long title"));
        items.add(new MyItems("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyItems("Super Long Item Title"));
        items.add(new MyItems("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyItems("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyItems("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyItems("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyItems("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyItems("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyItems("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyItems("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyItems("Very Very Very Long Item Title is Created By Henry"));
        items.add(new MyItems("Very Very Very Long Item Title is Created By Henry"));



        return items;
    }
    private void applyLinearLayoutManager(){
        mLayoutManager = new CustomLinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
