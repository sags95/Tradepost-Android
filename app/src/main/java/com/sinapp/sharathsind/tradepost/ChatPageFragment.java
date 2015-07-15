package com.sinapp.sharathsind.tradepost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.ChatPageAdapter;
import Model.ChatPageItem;
import Model.DividerItemDecoration;
import Model.NavigationDrawerAdapter;
import Model.NavigationItem;
import Model.RecyclerViewOnClickListener;

/**
 * Created by HenryChiang on 15-07-03.
 */
public class ChatPageFragment extends Fragment {

    private View rootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ChatPageAdapter mChatPageAdapter;
    private Fragment chatFrag;
    private ChatPageItem item;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chatpage, container, false);
        chatFrag = new ChatFragment();

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.chatpage_list);

        final List<ChatPageItem> chatItem = addItem();
        mChatPageAdapter = new ChatPageAdapter(chatItem);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mChatPageAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerViewOnClickListener(getActivity(), new RecyclerViewOnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(),"you click" + position,Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.container_right, chatFrag);
                        transaction.commit();
                    }
                })
        );

        applyLinearLayoutManager();

        return rootView;
    }

    public List<ChatPageItem> addItem() {
        List<ChatPageItem> items = new ArrayList<ChatPageItem>();
        items.add(new ChatPageItem("Main Title1","Secondary Title", getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new ChatPageItem("Main Title2","Secondary Title", getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new ChatPageItem("Main Title3","Secondary Title", getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new ChatPageItem("Main Title4", "Secondary Title", getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new ChatPageItem("Main Title5", "Secondary Title", getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new ChatPageItem("Main Title6", "Secondary Title", getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new ChatPageItem("Main Title7", "Secondary Title", getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new ChatPageItem("Main Title8", "Secondary Title", getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new ChatPageItem("Main Title9", "Secondary Title", getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new ChatPageItem("Main Title10", "Secondary Title", getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new ChatPageItem("Main Title11", "Secondary Title", getResources().getDrawable(R.drawable.ic_launcher)));

        return items;
    }
    private void applyLinearLayoutManager(){
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of ChatPageFragment");
        super.onResume();
    }


    @Override
    public void onPause(){
        Log.e("DEBUG", "onPause of ChatPageFragment");
        super.onPause();
    }

    @Override
    public void onStop(){
        Log.e("DEBUG", "onStop of ChatPageFragment");
        super.onStop();
    }

    @Override
    public void onDestroyView(){
        Log.e("DEBUG", "onDestroyView of ChatPageFragment");
        super.onDestroyView();
    }

    @Override
    public void onDestroy(){
        Log.e("DEBUG", "onDestroy of ChatPageFragment");
        super.onDestroy();

    }

    @Override
    public void onDetach(){
        Log.e("DEBUG", "onDetach of ChatPageFragment");
        super.onDetach();
    }




}
