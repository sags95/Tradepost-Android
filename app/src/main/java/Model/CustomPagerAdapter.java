package Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.NetworkImageView;
import com.sinapp.sharathsind.tradepost.R;
import com.squareup.picasso.Picasso;

/**
 * Created by HenryChiang on 15-06-12.
 */
public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;



   Bitmap[] mResources;
    String[] imagesArray;


    public CustomPagerAdapter(Context context, Bitmap[] imageResources) {
        mContext = context;
        mResources = imageResources;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomPagerAdapter(Context context, String[] imagesArray) {
        mContext = context;
        this.imagesArray=imagesArray;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(mResources!=null){
            return mResources.length;
        }else {
            return imagesArray.length;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        if(imagesArray!=null){
                Picasso.with(mContext).load(Uri.parse(imagesArray[position])).into(imageView);
                container.addView(itemView);

        }else{
            imageView.setImageBitmap(mResources[position]);
            container.addView(itemView);
        }


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}



