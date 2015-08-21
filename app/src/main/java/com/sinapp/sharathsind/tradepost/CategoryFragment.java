package com.sinapp.sharathsind.tradepost;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
            R.drawable.sample_img5,
            R.drawable.sample_img6,
            R.drawable.sample_img3,
            R.drawable.sample_img5,
            R.drawable.sample_img6,
            R.drawable.sample_img3,
            R.drawable.sample_img5,
            R.drawable.sample_img6,
            R.drawable.sample_img3,
            R.drawable.sample_img6,
            R.drawable.sample_img5
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(getActivity());
        View v = inflator.inflate(R.layout.toolbar_custom_title, null);
        TextView title1 = (TextView) v.findViewById(R.id.toolbar_title1);
        TextView title2 = (TextView) v.findViewById(R.id.toolbar_title2);
        title1.setText("Categories");
        title2.setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(v);
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
