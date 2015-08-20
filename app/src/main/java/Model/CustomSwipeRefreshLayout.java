package Model;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by HenryChiang on 15-08-11.
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {


    private OnChildScrollUpListener mScrollListenerNeeded;

    public static interface OnChildScrollUpListener {
        public boolean canChildScrollUp();
    }

    public CustomSwipeRefreshLayout(Context context) {
        super(context);
    }
    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Listener that controls if scrolling up is allowed to child views or not
     */
    public void setOnChildScrollUpListener(OnChildScrollUpListener listener) {
        mScrollListenerNeeded = listener;
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollListenerNeeded == null) {
            Log.e(CustomSwipeRefreshLayout.class.getSimpleName(), "listener is not defined!");
        }
        return mScrollListenerNeeded == null ? false : mScrollListenerNeeded.canChildScrollUp();
    }
}