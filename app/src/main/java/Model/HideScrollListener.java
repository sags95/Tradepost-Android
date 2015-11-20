package Model;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;

import com.sinapp.sharathsind.tradepost.R;

/**
 * Created by admin on 2015-11-11.
 */
public abstract class HideScrollListener extends RecyclerView.OnScrollListener {
    private static int HIDE_THRESHOLD = 0;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    public HideScrollListener(Context context) {
        getToolbarHeight(context);
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
    }

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