package Model;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.Constants;
import com.sinapp.sharathsind.tradepost.ProfileActivity;
import com.sinapp.sharathsind.tradepost.R;
import com.sinapp.sharathsind.tradepost.Search;
import com.squareup.picasso.Picasso;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import webservices.FavouriteWebService;

/**
 * Created by HenryChiang on 15-06-10.
 */
public class MarketPlaceStaggeredAdapter extends RecyclerView.Adapter<MarketPlaceStaggeredAdapter.ViewHolder> {

    public static List<MarketPlaceData> mData;
    private View.OnClickListener mItemClick;
    private static Context context;


    public MarketPlaceStaggeredAdapter(){

    }
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
        if(mData==null){
            return 0;
        }else {
            if(mData.get(position).item.tags==null) {
                MarketPlaceData i=mData.get(position);
                i.item.tags=new String[1];
                i.item.tags[0]="notag";
                mData.remove(position);
                mData.add(position,i);

            }
                return mData.get(position).item.tags.length;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v=null;
        ViewHolder vh;
        switch (viewType) {
            case 1:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_staggered_one_tag, viewGroup, false);
                v.setOnClickListener(mItemClick);
                break;
            case 2:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_staggered_two_tags, viewGroup, false);
                v.setOnClickListener(mItemClick);
                break;
            case 3:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_staggered_three_tags, viewGroup, false);
                v.setOnClickListener(mItemClick);
                break;
            case 4:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_staggered_four_tags, viewGroup, false);
                v.setOnClickListener(mItemClick);
                break;
            case 5:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_staggered_five_tags, viewGroup, false);
                v.setOnClickListener(mItemClick);
                break;
        }

        vh = new ViewHolder(v, viewType);
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
        public ImageButton im;
        public ImageView mImageViewProPic;
        public FlowLayout mTagFlowLayout;
        public CustomTextView tag1,tag2,tag3,tag4,tag5;
        public int viewType;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            im = (ImageButton) itemView.findViewById(R.id.image_like_btn);
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
static boolean unfav,fav;
        public void bind(final MarketPlaceData data, int viewType, String daysOffset) {


            //item images
            Uri url1 = Uri.parse("http://73.37.238.238:8084/TDserverWeb/images/items/" + data.item.item.getItemid() + "/0.png");
            Picasso.with(context)
                    .load(url1)
                    .placeholder(R.drawable.image_placeholder)
                    .into(mImageViewItemImg);

            //date
            if(data.isFav)
                im.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_favorite_selected));

            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(data.isFav) {
                 //   view.setEnabled(false);
                       new AsyncTask<Void,Void,Void>()
                       {
                           @Override
                           protected void onPostExecute(Void aVoid) {

                               super.onPostExecute(aVoid);
                               data.isFav=false;
                               im.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_favorite_not_selected));

                           }

                           @Override
                           protected Void doInBackground(Void... params) {
                               Cursor c=Constants.db.rawQuery("select * from fav where itemid="+data.item.item.getItemid(),null);
                               c.moveToFirst();
                               FavouriteWebService.removefavouInts(c.getInt(c.getColumnIndex("id")));
                               c.close();
                               return null;
                           }
                       }.execute(null,null);
                    }
                    else {
                   //     view.setEnabled(false);

                        new AsyncTask<Void,Void,Void>()
                        {
                            @Override
                            protected void onPostExecute(Void aVoid) {

                                super.onPostExecute(aVoid);
                                data.isFav=true;
                                im.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_favorite_selected));

                            }

                            @Override
                            protected Void doInBackground(Void... params) {
                                FavouriteWebService.add(data.item.item.getItemid());
                                return null;
                            }
                        }.execute(null, null);
























                    }
                }
            });
            mItemDateAdded.setText(daysOffset);

            //item title
            mTextViewItemTitle.setText(data.item.item.getItemname());

            //profile picture
            Uri url=Uri.parse("http://73.37.238.238:8084/TDserverWeb/images/" + data.userid + "/profile.png");
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .into(mImageViewProPic);
                    mImageViewProPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), ProfileActivity.class);
                    ArrayList<String> itemProfileClicked = new ArrayList<>();
                    itemProfileClicked.add(0, String.valueOf(data.userid));
                    itemProfileClicked.add(1, data.proUsername);
                    i.putStringArrayListExtra("itemProfileClicked", itemProfileClicked);

                    //
                    i.putExtra("caller","MarketPlace");
                    BitmapDrawable bitmapDrawable = (BitmapDrawable)mImageViewProPic.getDrawable();
                    Bitmap b = bitmapDrawable.getBitmap();
                    i.putExtra("profilePic", b);

                    context.startActivity(i);
                }
            });

            //tags
            switch (viewType) {
                case 1:
                    tag1.setText(data.item.tags[0].toUpperCase());
                    tag1.setOnClickListener(tagOnClickListener);
                    break;
                case 2:
                    tag1.setText(data.item.tags[0].toUpperCase());
                    tag2.setText(data.item.tags[1].toUpperCase());
                    tag1.setOnClickListener(tagOnClickListener);
                    tag2.setOnClickListener(tagOnClickListener);
                    break;
                case 3:
                    tag1.setText(data.item.tags[0].toUpperCase());
                    tag2.setText(data.item.tags[1].toUpperCase());
                    tag3.setText(data.item.tags[2].toUpperCase());
                    tag1.setOnClickListener(tagOnClickListener);
                    tag2.setOnClickListener(tagOnClickListener);
                    tag3.setOnClickListener(tagOnClickListener);
                    break;
                case 4:
                    tag1.setText(data.item.tags[0].toUpperCase());
                    tag2.setText(data.item.tags[1].toUpperCase());
                    tag3.setText(data.item.tags[2].toUpperCase());
                    tag4.setText(data.item.tags[3].toUpperCase());
                    tag1.setOnClickListener(tagOnClickListener);
                    tag2.setOnClickListener(tagOnClickListener);
                    tag3.setOnClickListener(tagOnClickListener);
                    tag4.setOnClickListener(tagOnClickListener);


                    break;
                case 5:
                    tag1.setText(data.item.tags[0].toUpperCase());
                    tag2.setText(data.item.tags[1].toUpperCase());
                    tag3.setText(data.item.tags[2].toUpperCase());
                    tag4.setText(data.item.tags[3].toUpperCase());
                    tag5.setText(data.item.tags[4].toUpperCase());
                    tag1.setOnClickListener(tagOnClickListener);
                    tag2.setOnClickListener(tagOnClickListener);
                    tag3.setOnClickListener(tagOnClickListener);
                    tag4.setOnClickListener(tagOnClickListener);
                    tag5.setOnClickListener(tagOnClickListener);

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

    public void updateList(ArrayList<MarketPlaceData> data) {
        mData = data;
        notifyDataSetChanged();

    }

    public static View.OnClickListener tagOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView temp = (TextView)v;
            Intent intent=new Intent(context, Search.class);
            intent.putExtra("type","tags");
            intent.putExtra(SearchManager.QUERY,temp.getText().toString());
            context.startActivity(intent);
        }
    };
}
