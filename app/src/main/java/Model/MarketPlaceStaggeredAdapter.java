package Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.ProfileActivity;
import com.sinapp.sharathsind.tradepost.R;

import org.apmem.tools.layouts.FlowLayout;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by HenryChiang on 15-06-10.
 */
public class MarketPlaceStaggeredAdapter extends RecyclerView.Adapter<MarketPlaceStaggeredAdapter.ViewHolder> {

    private List<MarketPlaceData> mData;
    private View.OnClickListener mItemClick;
    private ViewGroup viewGroup;
    private String[] tags;
    private Context context;



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
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final int currentPos = i;
        viewHolder.mTextViewItemTitle.setText(mData.get(i).itemTitle);

        if(viewHolder.mTagFlowLayout.getChildCount()==0) {
            for(int j=0;j<tags.length;j++){

                viewHolder.mTagFlowLayout.addView(addTags(j));

            }
        }

        try {
            URL url=new URL(mData.get(i).itemImage);
           URLConnection  con=url.openConnection();
          InputStream is=  con.getInputStream();


          Bitmap b= BitmapFactory.decodeStream(is);

           viewHolder.mImageViewItemImg.setImageBitmap(b);

is.close();
           url=new URL("http://192.168.2.15:8084/TDserverWeb/images/"+mData.get(i).userid+"/profile.png");
              con=url.openConnection();
             is=  con.getInputStream();
             b= BitmapFactory.decodeStream(is);
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
        public TextView mTextViewItemTitle;
        public ImageView mImageViewItemImg;
        public ImageView mImageViewProPic;
        public FlowLayout mTagFlowLayout;



        public ViewHolder(View itemView) {
            super(itemView);
            mImageViewProPic = (ImageView)itemView.findViewById(R.id.pro_img);
            mTextViewItemTitle = (TextView) itemView.findViewById(R.id.item_title);
            mImageViewItemImg = (ImageView) itemView.findViewById(R.id.item_image);
            mTagFlowLayout = (FlowLayout)itemView.findViewById(R.id.marketplace_tags_layout);


        }
    }

    public TextView addTags(int pos){


        TextView newTag = new TextView(viewGroup.getContext());
        newTag.setText(tags[pos]);
        newTag.setTextColor(viewGroup.getResources().getColor(R.color.white));
        newTag.setTextSize(14);
        newTag.setTypeface(Typeface.DEFAULT_BOLD);
        newTag.setClickable(true);
        newTag.setBackgroundResource(R.drawable.tag_btn_shape);
        newTag.setPadding(8, 8, 8, 8);
        FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,20,20);
        newTag.setLayoutParams(lp);



        return newTag;

    }




}