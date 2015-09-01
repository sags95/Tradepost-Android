package com.sinapp.sharathsind.tradepost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Model.ChatPageAdapter;
import Model.ChatPageItem;
import Model.CustomLinearLayoutManager;
import Model.DividerItemDecoration;
import Model.EmptyRecyclerView;
import Model.SimpleSectionedRecyclerViewAdapter;
import Model.SwipeableRecyclerViewTouchListener;

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
        chatFrag = new ChatFragment();

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
        //Sections
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0,"New Message"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(1,"Old Message"));

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

        return rootView;
    }

    public List<ChatPageItem> addItem() {
        List<ChatPageItem> items = new ArrayList<ChatPageItem>();
        items.add(new ChatPageItem("Main Title1", "Secondary Title", getResources().getDrawable(R.drawable.ic_launcher)));
        items.add(new ChatPageItem("Main Title2", "Secondary Title", getResources().getDrawable(R.drawable.ic_launcher)));
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
        mLayoutManager = new CustomLinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public View.OnClickListener ItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container_right, chatFrag);
            transaction.commit();

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
