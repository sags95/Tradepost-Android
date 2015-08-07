package datamanager;

/**
 * Created by sharathsind on 2015-07-15.
 */
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * copied from the official documentation
 */
public class MyVolleySingleton {

    private static MyVolleySingleton mVolleyInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mContext;

    private MyVolleySingleton(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue,
                new MyLruBitmapCache(MyLruBitmapCache.getCacheSize(context))
        );
    }

    public static synchronized MyVolleySingleton getInstance(Context context) {
        if (mVolleyInstance == null) {
            mVolleyInstance = new MyVolleySingleton(context);
        }
        return mVolleyInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // use the application context
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <t> void addToRequestQueue(Request<t> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}