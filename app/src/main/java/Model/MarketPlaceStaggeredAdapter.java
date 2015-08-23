package Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sinapp.sharathsind.tradepost.ProfileActivity;
import com.sinapp.sharathsind.tradepost.R;

import org.apmem.tools.layouts.FlowLayout;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

import datamanager.MyVolleySingleton;

/**
 * Created by HenryChiang on 15-06-10.
 */
public class MarketPlaceStaggeredAdapter extends RecyclerView.Adapter<MarketPlaceStaggeredAdapter.ViewHolder> {

    public static List<MarketPlaceData> mData;
    private View.OnClickListener mItemClick;
    private ViewGroup viewGroup;
    private String[] tags;
    private Context context;
    private int currentPos=0;
    private ViewHolder viewHolder;
    private URL url1;
    private ImageLoader im;



    public MarketPlaceStaggeredAdapter(Context context,List<MarketPlaceData> mData, View.OnClickListener mItemClick) {
        this.mData = mData;
        this.mItemClick=mItemClick;
        this.context=context;

    }

    public MarketPlaceStaggeredAdapter(List<MarketPlaceData> mData) {
        this.mData = mData;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.viewGroup = viewGroup;
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_staggered, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(mItemClick);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int i) {

        viewHolder.mTextViewItemTitle.setText(mData.get(i).item.item.getItemname());



        if(viewHolder.mTagFlowLayout.getChildCount()==0) {
            if(mData.get(i).item.tags!=null) {
                for (String tag : mData.get(i).item.tags) {

                    viewHolder.mTagFlowLayout.addView(addTags(tag));

                }
            }
        }


        try {
            URL url;
            URLConnection  con;
            InputStream is;
            ImageLoader im= MyVolleySingleton.getInstance(context).getImageLoader();


            String url1="http://104.199.135.162:8084/TDserverWeb/images/items/"+mData.get(i).item.item.getItemid()+"/0.png";
            viewHolder.mImageViewItemImg.setImageUrl(url1,im);
           // viewHolder.mImageViewItemImg.setImageBitmap(mData.get(i).itemImgs[0]);


            url=new URL("http://104.199.135.162:8084/TDserverWeb/images/"+mData.get(i).userid+"/profile.png");
            con=url.openConnection();
            is=  con.getInputStream();
            Bitmap b= BitmapFactory.decodeStream(is);
            viewHolder.mImageViewProPic.setImageBitmap(b);
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        viewHolder.mImageViewProPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context.getApplicationContext(), ProfileActivity.class));

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
        public CustomTextView mTextViewItemTitle,mItemDateAdded;
        public NetworkImageView mImageViewItemImg;
        public ImageView mImageViewProPic;
        public FlowLayout mTagFlowLayout;



        public ViewHolder(View itemView) {
            super(itemView);
            mImageViewProPic = (ImageView)itemView.findViewById(R.id.pro_img);
            mTextViewItemTitle = (CustomTextView) itemView.findViewById(R.id.item_title);
            mItemDateAdded = (CustomTextView)itemView.findViewById(R.id.pro_post_time);
            mImageViewItemImg = (NetworkImageView) itemView.findViewById(R.id.item_image);
            mTagFlowLayout = (FlowLayout)itemView.findViewById(R.id.marketplace_tags_layout);


        }
    }

    public CustomTextView addTags(String tag) {

        CustomTextView newTag = new CustomTextView(viewGroup.getContext());
        newTag.setText(tag.toUpperCase());
        newTag.setTextColor(viewGroup.getResources().getColor(R.color.white));
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
        float d = context.getResources().getDisplayMetrics().density;
        int margin = (int)(dpValue * d); // margin in pixels
        return margin; // margin in pixels

    }

    public String dateComparison(Date date){
        Date currentDate = new Date();
        int offset = currentDate.compareTo(date);

        if(offset ==0){
            return "Today";
        }else{
            return offset + " days ago";
        }
    }

}