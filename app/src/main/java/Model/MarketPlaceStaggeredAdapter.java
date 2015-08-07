package Model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.sinapp.sharathsind.tradepost.R;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import datamanager.MyVolleySingleton;

/**
 * Created by HenryChiang on 15-06-10.
 */
public class MarketPlaceStaggeredAdapter extends RecyclerView.Adapter<MarketPlaceStaggeredAdapter.ViewHolder> {

public static List<MarketPlaceData> mData;
    private View.OnClickListener mItemClick;
private Activity a;

    public MarketPlaceStaggeredAdapter(List<MarketPlaceData> mData, View.OnClickListener mItemClick,Activity a) {
        this.mData = mData;
        this.mItemClick=mItemClick;
        this.a=a;
    }

    public MarketPlaceStaggeredAdapter(List<MarketPlaceData> mData) {
        this.mData = mData;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_staggered, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(mItemClick);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final int currentPos = i;
     //   final int currentPos = i;
        viewHolder.mTextViewItemTitle.setText(mData.get(i).itemTitle);
        try {


            ImageLoader mImageLoader;
            NetworkImageView mNetworkImageView;

            // Get the NetworkImageView that will display the image.
            //  mNetworkImageView = (NetworkImageView) findViewById(R.id.networkImageView);

            // Get the ImageLoader through your singleton class.
            mImageLoader = MyVolleySingleton.getInstance(a).getImageLoader();

            // Set the URL of the image that should be loaded into this view, and
            // specify the ImageLoader that will be used to make the request.bnh
            viewHolder.mImageViewItemImg.setImageUrl(mData.get(i).itemImage, mImageLoader);
            final  ImageView im=viewHolder.mImageViewProPic;
            // viewHolder.mImageViewProPic.setImageUrl("http://192.168.2.15:8084/TDserverWeb/images/"+mData.get(i).userid+"/profile.png",mImageLoader);
            ImageRequest request = new ImageRequest("http://192.168.2.15:8084/TDserverWeb/images/"+mData.get(i).userid+"/profile.png",
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            im.setImageBitmap(bitmap);
                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            //mImageView.setImageResource(R.drawable.image_load_error);
                            Toast.makeText(a,error.getMessage(),Toast.LENGTH_LONG);
                        }
                    });

// Access the RequestQueue through your singleton class.
            MyVolleySingleton.getInstance(a).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }




        viewHolder.mImageViewProPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("user detail at position","position: " +currentPos);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewItemTitle;
        public NetworkImageView mImageViewItemImg;
        public ImageView mImageViewProPic;


        public ViewHolder(View itemView) {
            super(itemView);
            mImageViewProPic = (ImageView)itemView.findViewById(R.id.pro_img);
            mTextViewItemTitle = (TextView) itemView.findViewById(R.id.item_title);
            mImageViewItemImg = (NetworkImageView) itemView.findViewById(R.id.item_image);

        }
    }




}