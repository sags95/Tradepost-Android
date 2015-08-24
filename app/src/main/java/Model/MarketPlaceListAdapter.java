package Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.etsy.android.grid.util.DynamicHeightTextView;
import com.sinapp.sharathsind.tradepost.ProfileActivity;
import com.sinapp.sharathsind.tradepost.R;
import com.sinapp.sharathsind.tradepost.SingleListingActivity;

import org.apmem.tools.layouts.FlowLayout;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import datamanager.MyVolleySingleton;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HenryChiang on 15-08-23.
 */
public class MarketPlaceListAdapter extends ArrayAdapter<MarketPlaceData> {

    private static final String TAG = "MarketPlaceListAdapter";

    static class ViewHolder {
        DynamicHeightImageView dynamicImg;
        CustomTextView itemDateAdded,itemTitle;
        ImageView itemProPic;
        NetworkImageView itemImg;
        FlowLayout fl;
        CardView cv;
    }

    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    public static ArrayList<MarketPlaceData> mData;
    private View.OnClickListener mItemClick;
    private Bitmap itemImg;



    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();



    public MarketPlaceListAdapter(final Context context, int layoutResId,int textViewId, ArrayList<MarketPlaceData> mData,View.OnClickListener mItemClick) {
        super(context,layoutResId,textViewId ,mData);
        mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
        this.mData=mData;
        this.mItemClick=mItemClick;

    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_marketplace, parent, false);
            vh = new ViewHolder();

            vh.cv = (CardView)convertView.findViewById(R.id.marketplace_card_view);
            vh.itemTitle = (CustomTextView) convertView.findViewById(R.id.item_title);
            vh.itemDateAdded = (CustomTextView) convertView.findViewById(R.id.pro_post_time);
            vh.itemImg = (NetworkImageView)convertView.findViewById(R.id.item_image);
            vh.fl = (FlowLayout)convertView.findViewById(R.id.marketplace_tags_layout);
            vh.itemProPic = (CircleImageView)convertView.findViewById(R.id.pro_img);

            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        //
        vh.itemTitle.setText(mData.get(position).item.item.getItemname());
        vh.itemDateAdded.setText(String.valueOf(mData.get(position).item.item.getDateadded()));

        //
        vh.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Log.d("child position", String.valueOf(position));

                        Intent i = new Intent(getContext().getApplicationContext(), SingleListingActivity.class);
                        ArrayList<String> clickedItemDetails = new ArrayList<>();
                        clickedItemDetails.add(0, String.valueOf(position));

                        //profile picture
                        BitmapDrawable bitmapDrawable = (BitmapDrawable)((ImageView) v.findViewById(R.id.pro_img)).getDrawable();
                        Bitmap b =bitmapDrawable.getBitmap();
                        i.putExtra("profilePic",b);

                        i.putStringArrayListExtra("itemClicked", clickedItemDetails);
                        getContext().startActivity(i);

            }
        });

        //
        vh.itemProPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext().getApplicationContext(), ProfileActivity.class));

            }
        });

        //Images
        try {

            //vh.cv.setOnClickListener(mItemClick);

            ImageLoader im= MyVolleySingleton.getInstance(getContext()).getImageLoader();
            String url1="http://104.199.135.162:8084/TDserverWeb/images/items/"+mData.get(position).item.item.getItemid()+"/0.png";
            vh.itemImg.setImageUrl(url1, im);


            //vh.itemImg.setImageBitmap(mData.get(position).itemImgs[0]);


            URL url;
            URLConnection con;
            InputStream is;
            url=new URL("http://104.199.135.162:8084/TDserverWeb/images/"+mData.get(position).userid+"/profile.png");
            con=url.openConnection();
            is=  con.getInputStream();
            Bitmap b= BitmapFactory.decodeStream(is);
            vh.itemProPic.setImageBitmap(b);
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Tags
        if(vh.fl.getChildCount()==0) {
            if(mData.get(position).item.tags!=null) {
                for (String tag : mData.get(position).item.tags) {
                    vh.fl.addView(addTags(tag));

                }
            }
        }


        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }


    public CustomTextView addTags(String tag) {

        CustomTextView newTag = new CustomTextView(getContext());
        newTag.setText(tag.toUpperCase());
        newTag.setTextColor(getContext().getResources().getColor(R.color.white));
        newTag.setTextSize(10);
        newTag.setClickable(true);
        newTag.settingOpenSansLight();
        newTag.setBackgroundResource(R.drawable.tag_btn_shape);
        newTag.setPadding(DpToPx(4), DpToPx(4), DpToPx(4), DpToPx(4));
        FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,DpToPx(4), DpToPx(8));

        newTag.setLayoutParams(lp);



        return newTag;

    }


    public int DpToPx(int requireDp ){
        int dpValue = requireDp; // margin in dips
        float d = getContext().getResources().getDisplayMetrics().density;
        int margin = (int)(dpValue * d); // margin in pixels
        return margin; // margin in pixels

    }

    public View.OnClickListener listingItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };

}



