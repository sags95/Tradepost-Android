package Model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

import java.util.List;

/**
 * Created by HenryChiang on 15-06-10.
 */
public class MarketPlaceStaggeredAdapter extends RecyclerView.Adapter<MarketPlaceStaggeredAdapter.ViewHolder> {

    private List<MarketPlaceData> mData;
    private View.OnClickListener mItemClick;


    public MarketPlaceStaggeredAdapter(List<MarketPlaceData> mData, View.OnClickListener mItemClick) {
        this.mData = mData;
        this.mItemClick=mItemClick;
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
        viewHolder.mTextViewItemTitle.setText(mData.get(i).itemTitle);
        if (i % 2 == 0) {
            viewHolder.mImageViewItemImg.setImageResource(R.drawable.sample_img);
        } else if(i%3 ==0) {
            viewHolder.mImageViewItemImg.setImageResource(R.drawable.sample_img3);
        }else{
            viewHolder.mImageViewItemImg.setImageResource(R.drawable.sample_img2);
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
        public ImageView mImageViewItemImg;
        public ImageView mImageViewProPic;


        public ViewHolder(View itemView) {
            super(itemView);
            mImageViewProPic = (ImageView)itemView.findViewById(R.id.pro_img);
            mTextViewItemTitle = (TextView) itemView.findViewById(R.id.item_title);
            mImageViewItemImg = (ImageView) itemView.findViewById(R.id.item_image);

        }
    }




}