package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Model.ChatPageAdapter;
import Model.ChatPageItem;
import Model.CustomLinearLayoutManager;
import Model.DividerItemDecoration;
import Model.EmptyRecyclerView;
import Model.SimpleSectionedRecyclerViewAdapter;
import Model.SwipeableRecyclerViewTouchListener;
import datamanager.userdata;
import webservices.MainWebService;

/**
 * Created by HenryChiang on 15-07-03.
 */
public class ChatPageFragment extends Fragment {

    private View rootView,emptyView;
    private EmptyRecyclerView mRecyclerView;
    private ChatPageAdapter mChatPageAdapter;
    private Fragment chatFrag;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CustomLinearLayoutManager mLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chatpage, container, false);
        emptyView = rootView.findViewById(R.id.chatpage_emptyView);
      //  chatFrag = new ChatFragment();

        //SwipeToRefresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.chatpage_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.ColorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                 Log.d("SwipeToRefresh", "Refreshing");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        mRecyclerView = (EmptyRecyclerView)rootView.findViewById(R.id.chatpage_list);

        final List<ChatPageItem> chatItem = addItem();

        mChatPageAdapter = new ChatPageAdapter(chatItem,ItemClickListener);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mRecyclerView.setEmptyView(emptyView);
        applyLinearLayoutManager();


        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();



        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        final SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(getActivity().getApplicationContext(),R.layout.chat_section,R.id.section_text,mChatPageAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));


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


        /*
        mRecyclerView.addOnItemTouchListener(
                new RecyclerViewOnClickListener(getActivity(), new RecyclerViewOnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.container_right, chatFrag);
                        transaction.commit();
                    }
                })
        );
        */

        mRecyclerView.setAdapter(mSectionedAdapter);



        return rootView;
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
    public List<ChatPageItem> addItem() {
        List<ChatPageItem> items = new ArrayList<ChatPageItem>();
        SQLiteDatabase db=getActivity().openOrCreateDatabase("tradepostdb.db", getActivity().MODE_PRIVATE, null);
        Cursor cv=db.rawQuery("select * from offers where status=1",null);
        if(cv.getCount()>0){
        cv.moveToFirst();
        while(!cv.isAfterLast()) {
            int userid = cv.getInt(cv.getColumnIndex("recieveduserid"));
            int ruserid = cv.getInt(cv.getColumnIndex("userid"));
            int itemid = cv.getInt(cv.getColumnIndex("Itemid"));
            int getuser = userdata.userid != userid ? userid : ruserid;
            SoapObject o = new SoapObject("http://webser/", "getItemnameU");
            o.addProperty("userid",userid);
            o.addProperty("itemid",itemid);
            SoapPrimitive s = MainWebService.getretryMsg(o, "http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/getItemnameURequest", 0);
            String username = s.getValue().toString().split("/,")[0].replace("username:", " ");
            String itemname = s.getValue().toString().split("/,")[1].replace("itemname:", " ");
            Drawable d = new BitmapDrawable(getResources(), getBitmapFromURL("http://73.37.238.238:8084/TDserverWeb/images/" + getuser + "/profile.png"));
            items.add(new ChatPageItem(username, itemname, d));
 cv.moveToNext();
        }
        }
        cv.close();
        db.close();
        return items;
    }
    private void applyLinearLayoutManager(){
        mLayoutManager = new CustomLinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public View.OnClickListener ItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
int offerid=Integer.parseInt(v.getTag().toString());


            Intent i=new Intent(getActivity(),ChatFragment.class);
            i.putExtra("offerid",offerid);
            startActivity(i);

        }
    };

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
