package Model;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.sinapp.sharathsind.tradepost.R;

/**
 * Created by admin on 2015-11-11.
 */
public abstract class HideScrollListener extends RecyclerView.OnScrollListener {
    private static int ITEM_LEFT_TO_LOAD_MORE=0 ;
    private static int HIDE_THRESHOLD = 0;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 8; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private StaggeredGridLayoutManager mLinearLayoutManager;
    private int mOnScreenItems;
    private int mTotalItemsInList,mPreviousTotal;
    private boolean mLoadingItems;
    private int mVisibleThreshold;
    private int pastVisiblesItems;
    private boolean isLoadingMore;
    private Object mOnMoreListener;

    public HideScrollListener(Context context,StaggeredGridLayoutManager m) {
        getToolbarHeight(context);

        m=mLinearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            onHide();
            controlsVisible = false;
            scrolledDistance = 0;
        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            onShow();
            controlsVisible = true;
            scrolledDistance = 0;
        }

        if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
            scrolledDistance += dy;
        }
    StaggeredGridLayoutManager mLayoutManager =(StaggeredGridLayoutManager)recyclerView.getLayoutManager();
        if(dy > 0) //check for scroll down
        {
          processOnMore(recyclerView);
        }
/*
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;

            onLoadMore(current_page);

            loading = true;
        }*/

    }
    private void processOnMore(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int lastVisibleItemPosition = getLastVisibleItemPosition(layoutManager);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItem = getLastVisibleItemPosition(layoutManager);

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && totalItemCount
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;

            onLoadMore(current_page);

            loading = true;
        }
/*        if (((totalItemCount - lastVisibleItemPosition) <= ITEM_LEFT_TO_LOAD_MORE ||
                (totalItemCount - lastVisibleItemPosition) ==0  && totalItemCount > visibleItemCount)
                && !isLoadingMore) {

            isLoadingMore = true;
onLoadMore(1);
        }*/
    }
    private int[] lastScrollPositions;
    private int getLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int lastVisibleItemPosition = -1;
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        if (lastScrollPositions == null)
            lastScrollPositions = new int[staggeredGridLayoutManager.getSpanCount()];

        staggeredGridLayoutManager.findLastVisibleItemPositions(lastScrollPositions);
        lastVisibleItemPosition = findMax(lastScrollPositions);
        return lastVisibleItemPosition;
        }
    private int findMax(int[] lastPositions) {
        int max = Integer.MAX_VALUE;
        for (int value : lastPositions) {
            if (value <max)
                max = value;
        }
        return max;
    }
    public abstract void onLoadMore(int current_page);

    public abstract void onHide();
    public abstract void onShow();

    public static void getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        HIDE_THRESHOLD = toolbarHeight;
    }

}