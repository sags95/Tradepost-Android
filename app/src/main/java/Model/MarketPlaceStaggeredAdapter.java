package Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sinapp.sharathsind.tradepost.ProfileActivity;
import com.sinapp.sharathsind.tradepost.R;
import com.squareup.picasso.Picasso;

import org.apmem.tools.layouts.FlowLayout;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import datamanager.MyVolleySingleton;

/**
 * Created by HenryChiang on 15-06-10.
 */
public class MarketPlaceStaggeredAdapter extends RecyclerView.Adapter<MarketPlaceStaggeredAdapter.ViewHolder> {

    public static List<MarketPlaceData> mData;
    private View.OnClickListener mItemClick;
    private static Context context;


    public MarketPlaceStaggeredAdapter(Context context,List<MarketPlaceData> mData, View.OnClickListener mItemClick) {
        this.mData = mData;
        this.mItemClick=mItemClick;
        this.context=context;

    }

    public MarketPlaceStaggeredAdapter(List<MarketPlaceData> mData) {
        this.mData = mData;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).item.tags.length;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        ViewHolder vh=null;
        switch (viewType) {
            case 1: //This would be the header view in my Recycler
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_staggered_one_tag, viewGroup, false);
                vh = new ViewHolder(v, viewType);
                v.setOnClickListener(mItemClick);
                break;
            case 2: //This would be the header view in my Recycler
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_staggered_two_tags, viewGroup, false);
                vh = new ViewHolder(v, viewType);
                v.setOnClickListener(mItemClick);

                break;
            case 3: //This would be the header view in my Recycler
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_staggered_three_tags, viewGroup, false);
                vh = new ViewHolder(v, viewType);
                v.setOnClickListener(mItemClick);

                break;
            case 4: //This would be the header view in my Recycler
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_staggered_four_tags, viewGroup, false);
                vh = new ViewHolder(v, viewType);
                v.setOnClickListener(mItemClick);

                break;
            case 5: //This would be the header view in my Recycler
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_staggered_five_tags, viewGroup, false);
                vh = new ViewHolder(v, viewType);
                v.setOnClickListener(mItemClick);

        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,int i) {

        final MarketPlaceData marketPlaceData = mData.get(i);
        final int viewType=getItemViewType(i);
        viewHolder.bind(marketPlaceData, viewType, daysBetween(mData.get(i).item.item.getDateadded()));
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CustomTextView mTextViewItemTitle, mItemDateAdded;
        public ImageView mImageViewItemImg;
        public ImageView mImageViewProPic;
        public FlowLayout mTagFlowLayout;
        public CustomTextView tag1,tag2,tag3,tag4,tag5;
        public int viewType;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            switch (viewType){
                case 1:
                    mImageViewProPic = (ImageView) itemView.findViewById(R.id.pro_img);
                    mTextViewItemTitle = (CustomTextView) itemView.findViewById(R.id.item_title);
                    mItemDateAdded = (CustomTextView) itemView.findViewById(R.id.pro_post_time);
                    mImageViewItemImg = (ImageView) itemView.findViewById(R.id.item_image);
                    mTagFlowLayout = (FlowLayout) itemView.findViewById(R.id.marketplace_tags_layout);
                    tag1 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag1);
                    this.viewType=viewType;
                    break;
                case 2:
                    mImageViewProPic = (ImageView) itemView.findViewById(R.id.pro_img);
                    mTextViewItemTitle = (CustomTextView) itemView.findViewById(R.id.item_title);
                    mItemDateAdded = (CustomTextView) itemView.findViewById(R.id.pro_post_time);
                    mImageViewItemImg = (ImageView) itemView.findViewById(R.id.item_image);
                    mTagFlowLayout = (FlowLayout) itemView.findViewById(R.id.marketplace_tags_layout);
                    tag1 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag1);
                    tag2 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag2);
                    this.viewType=viewType;
                    break;
                case 3:
                    mImageViewProPic = (ImageView) itemView.findViewById(R.id.pro_img);
                    mTextViewItemTitle = (CustomTextView) itemView.findViewById(R.id.item_title);
                    mItemDateAdded = (CustomTextView) itemView.findViewById(R.id.pro_post_time);
                    mImageViewItemImg = (ImageView) itemView.findViewById(R.id.item_image);
                    mTagFlowLayout = (FlowLayout) itemView.findViewById(R.id.marketplace_tags_layout);
                    tag1 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag1);
                    tag2 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag2);
                    tag3 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag3);
                    this.viewType=viewType;
                    break;
                case 4:
                    mImageViewProPic = (ImageView) itemView.findViewById(R.id.pro_img);
                    mTextViewItemTitle = (CustomTextView) itemView.findViewById(R.id.item_title);
                    mItemDateAdded = (CustomTextView) itemView.findViewById(R.id.pro_post_time);
                    mImageViewItemImg = (ImageView) itemView.findViewById(R.id.item_image);
                    mTagFlowLayout = (FlowLayout) itemView.findViewById(R.id.marketplace_tags_layout);
                    tag1 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag1);
                    tag2 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag2);
                    tag3 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag3);
                    tag4 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag4);
                    this.viewType=viewType;
                    break;
                case 5:
                    mImageViewProPic = (ImageView) itemView.findViewById(R.id.pro_img);
                    mTextViewItemTitle = (CustomTextView) itemView.findViewById(R.id.item_title);
                    mItemDateAdded = (CustomTextView) itemView.findViewById(R.id.pro_post_time);
                    mImageViewItemImg = (ImageView) itemView.findViewById(R.id.item_image);
                    mTagFlowLayout = (FlowLayout) itemView.findViewById(R.id.marketplace_tags_layout);
                    tag1 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag1);
                    tag2 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag2);
                    tag3 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag3);
                    tag4 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag4);
                    tag5 = (CustomTextView)itemView.findViewById(R.id.marketplace_tag5);
                    this.viewType=viewType;
                    break;

            }



        }

        public void bind(MarketPlaceData data, int viewType, String daysOffset) {


            //item images
            Uri url1 = Uri.parse("http://104.199.135.162:8084/TDserverWeb/images/items/" + data.item.item.getItemid() + "/0.png");
            Picasso.with(context)
                    .load(url1)
                    .placeholder(R.drawable.sample_img3)
                    .into(mImageViewItemImg);

            //date
            mItemDateAdded.setText(daysOffset);

            //item title
            mTextViewItemTitle.setText(data.item.item.getItemname());

            //profile picture
            Uri url=Uri.parse("http://104.199.135.162:8084/TDserverWeb/images/" + data.userid + "/profile.png");
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.sample_img)
                    .into(mImageViewProPic);
            mImageViewProPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context.getApplicationContext(), ProfileActivity.class));
                }
            });

            //tags
            switch (viewType) {
                case 1:
                    tag1.setText(data.item.tags[0].toUpperCase());
                    break;
                case 2:
                    tag1.setText(data.item.tags[0].toUpperCase());
                    tag2.setText(data.item.tags[1].toUpperCase());
                    break;
                case 3:
                    tag1.setText(data.item.tags[0].toUpperCase());
                    tag2.setText(data.item.tags[1].toUpperCase());
                    tag3.setText(data.item.tags[2].toUpperCase());
                    break;
                case 4:
                    tag1.setText(data.item.tags[0].toUpperCase());
                    tag2.setText(data.item.tags[1].toUpperCase());
                    tag3.setText(data.item.tags[2].toUpperCase());
                    tag4.setText(data.item.tags[3].toUpperCase());

                    break;
                case 5:
                    tag1.setText(data.item.tags[0].toUpperCase());
                    tag2.setText(data.item.tags[1].toUpperCase());
                    tag3.setText(data.item.tags[2].toUpperCase());
                    tag4.setText(data.item.tags[3].toUpperCase());
                    tag5.setText(data.item.tags[4].toUpperCase());
                    break;
            }

        }
    }

    public static Calendar getDatePart(Date date){
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }

    public static String daysBetween(Date startDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(new Date());

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }

        String daysOffset;
        if((int)daysBetween==0){
            daysOffset = "Today";

        }else if((int)daysBetween==1){
            daysOffset = (int)daysBetween + " day ago";

        }else{
            daysOffset = (int)daysBetween + " days ago";

        }
        return daysOffset;
    }
}