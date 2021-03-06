package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import com.sinapp.sharathsind.tradepost.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.internal.CollectionMapper;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import Model.ChatPageAdapter;
import Model.ChatPageItem;
import Model.CustomLinearLayoutManager;
import Model.DividerItemDecoration;
import Model.EmptyRecyclerView;
import Model.RecyclerViewOnClickListener;
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
       new AsyncTask<List<ChatPageItem>,List<ChatPageItem>,List<ChatPageItem>>(){
            @Override
            protected List<ChatPageItem> doInBackground(List<ChatPageItem>... params) {

                return addItem();
            }

            @Override
            protected void onPostExecute(List<ChatPageItem> chatPageItems) {
                mChatPageAdapter = new ChatPageAdapter(getContext(),chatPageItems);
                mRecyclerView.setAdapter(mChatPageAdapter);
                super.onPostExecute(chatPageItems);
            }
        }.execute(null,null);



        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setSwipeRefreshLayout(mSwipeRefreshLayout);
        mRecyclerView.setEmptyView(emptyView);
        applyLinearLayoutManager();



        /*
        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();



        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        final SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(getActivity().getApplicationContext(),R.layout.chat_section,R.id.section_text,mChatPageAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));
        */




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


        mRecyclerView.addOnItemTouchListener(
                new RecyclerViewOnClickListener(getActivity(), new RecyclerViewOnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent i=new Intent(getActivity(),ChatFragment.class);
                        i.putExtra("offerid", offers.get(position));    i.putExtra("userid", userid.get(position));    i.putExtra("username", usernames.get(position));


                        startActivity(i);

                    }
                })
        );

        //mRecyclerView.setAdapter(mSectionedAdapter);


        /*
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(mRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    chatItem.remove(position);
                                    mChatPageAdapter.notifyItemRemoved(position);
                                }
                                mChatPageAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    chatItem.remove(position);
                                    mChatPageAdapter.notifyItemRemoved(position);
                                }
                                mChatPageAdapter.notifyDataSetChanged();
                            }
                        });


        mRecyclerView.addOnItemTouchListener(swipeTouchListener);
        */

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

            connection.disconnect();
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
    ArrayList<Integer>offers;
    ArrayList<Integer>userid;
    ArrayList<String>usernames;
    public List<ChatPageItem> addItem() {
        List<ChatPageItem> items = new ArrayList<ChatPageItem>();
        try {
            SQLiteDatabase db=getActivity().openOrCreateDatabase("tradepostdb.db", getActivity().MODE_PRIVATE, null);
            Cursor cv=db.rawQuery("select * from offers where status=1",null);
            userid=new ArrayList<>();
            usernames=new ArrayList<>();
            offers=new ArrayList<>();
            if(cv.getCount()>0){
                cv.moveToFirst();
                while(!cv.isAfterLast()) {
                    int userid = cv.getInt(cv.getColumnIndex("recieveduserid"));
                    int ruserid = cv.getInt(cv.getColumnIndex("userid"));
                    int itemid = cv.getInt(cv.getColumnIndex("Itemid"));
                    int getuser = userdata.userid != userid ? userid : ruserid;
                    int offerid=cv.getInt(cv.getColumnIndex("Offerid"));
                    offers.add(offerid);
                    SoapObject o = new SoapObject("http://webser/", "getItemnameU");
                    o.addProperty("userid", getuser);
                    this.userid.add(getuser);

                    o.addProperty("itemid",itemid);
                  KvmSerializable s = MainWebService.getMsg2(o, "http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/getItemnameURequest");
                    SoapObject s1=(SoapObject)s;
                    String itemname = null,username = null;
                    if(s1!=null) {
                        username = s1.getProperty(0).toString();
                        itemname = s1.getProperty(1).toString();
                    }
                        this.usernames.add(username);
                    Bitmap d =  getBitmapFromURL("http://73.37.238.238:8084/TDserverWeb/images/" + getuser + "/profile.png");
                  ChatPageItem chatPageItem=new ChatPageItem(username, itemname, d,offerid);
                    Cursor cursor=db.rawQuery("select * from m"+offerid+" ORDER BY msgid DESC LIMIT 1",null);
                    //  int so=userid==Constants.userid?userid:ruserid;
    cursor.moveToFirst();
                    if(cursor.getCount()>0)
                    {
                        try {
                            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                                    .parse(cursor.getString(cursor.getColumnIndex("sent")));
                            chatPageItem.sent=date;

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
    cursor.close();
                    items.add(chatPageItem);

                    cv.moveToNext();
                }
            }
            cv.close();
            db.close();
            Collections.sort(items);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
    private void applyLinearLayoutManager(){
        mLayoutManager = new CustomLinearLayoutManager(getActivity().getApplicationContext());
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
