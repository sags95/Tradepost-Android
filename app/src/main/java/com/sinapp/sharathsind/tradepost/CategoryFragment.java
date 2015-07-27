package com.sinapp.sharathsind.tradepost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import Model.CategoryAdapter;
import Model.ChatPageAdapter;
import Model.DividerItemDecoration;

/**
 * Created by HenryChiang on 15-07-20.
 */
public class CategoryFragment extends Fragment {

    private View rootView;
    private RecyclerView mRecyclerView;
    private CategoryAdapter mCategoryAdapter;
    private GridLayoutManager mGridLayoutManager;
    private String[] categories;
    int[] imgRes = {
            R.drawable.sample_img3,
            R.drawable.sample_img2,
            R.drawable.sample_img,
            R.drawable.sample_img3,
            R.drawable.sample_img2,
            R.drawable.sample_img,
            R.drawable.sample_img3,
            R.drawable.sample_img2,
            R.drawable.sample_img,
            R.drawable.sample_img3,
            R.drawable.sample_img2,
            R.drawable.sample_img
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Category");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_category, container, false);

        categories = getResources().getStringArray(R.array.category_array);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.category_recyclerview);

        mCategoryAdapter = new CategoryAdapter(categories,imgRes);
        mRecyclerView.setHasFixedSize(true);
        applyGridLayoutManager();


        return rootView;
    }

    private void applyGridLayoutManager() {
        mGridLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mCategoryAdapter);

    }



}