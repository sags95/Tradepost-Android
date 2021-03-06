package com.sinapp.sharathsind.tradepost;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Model.EmptyRecyclerView;
import Model.MyOffersAdapter;
import Model.MyOffersItem;
import Model.ProfileFeedbackAdapter;
import Model.ProfileFeedbackItem;

/**
 * Created by HenryChiang on 15-08-05.
 */
public class ProfileFeedbackFragment extends Fragment {


    private View rootView,emptyView;
    private EmptyRecyclerView mRecyclerView;
    private ProfileFeedbackAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile_feedback, container, false);
        emptyView = rootView.findViewById(R.id.profile_feedback_emptyView);
        mRecyclerView = (EmptyRecyclerView)rootView.findViewById(R.id.profile_feedback_recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.profile_feedback_swipe_refresh_layout);

        final List<ProfileFeedbackItem> feedbackItems = addItem("Sample User Name","no Comment",3.5f);
        //final List<ProfileFeedbackItem> feedbackItems = null;

        mAdapter = new ProfileFeedbackAdapter(feedbackItems);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setEmptyView(emptyView);
        mRecyclerView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mRecyclerView.setAdapter(mAdapter);
        applyLinearLayoutManager();

        return rootView;
    }

    public List<ProfileFeedbackItem> addItem(String username, String feedback, float rating) {
        List<ProfileFeedbackItem> items = new ArrayList<ProfileFeedbackItem>();

        items.add(new ProfileFeedbackItem(username, feedback, 1.5f));
        items.add(new ProfileFeedbackItem(username, feedback, 2.0f));
        items.add(new ProfileFeedbackItem(username, feedback, 4.5f));
        items.add(new ProfileFeedbackItem(username, feedback, 3.5f));
        items.add(new ProfileFeedbackItem(username, feedback, 5f));
        items.add(new ProfileFeedbackItem(username, feedback, 1.0f));
        items.add(new ProfileFeedbackItem(username, feedback, 2.5f));




        return items;
    }

    private void applyLinearLayoutManager() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

}


