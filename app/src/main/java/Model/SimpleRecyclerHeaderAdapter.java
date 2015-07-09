package Model;

/**
 * Created by HenryChiang on 15-07-07.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleRecyclerHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private LayoutInflater mInflater;
    private ArrayList<String> mItems;
    private View mHeaderView;
    private List<MarketPlaceData> mData;


    public SimpleRecyclerHeaderAdapter(Context context, List<MarketPlaceData> mData, View headerView) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
        this.mHeaderView = headerView;
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return mData.size();
        } else {
            return mData.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_staggered, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
           // ((ItemViewHolder) viewHolder).textView.setText(mItems.get(position - 1));
            final int currentPos = position;
            ((ItemViewHolder) viewHolder).mTextViewItemTitle.setText(mData.get(position).itemTitle);
            if (position % 2 == 0) {
                ((ItemViewHolder) viewHolder).mImageViewItemImg.setImageResource(R.drawable.sample_img);
            } else {
                ((ItemViewHolder) viewHolder).mImageViewItemImg.setImageResource(R.drawable.sample_img3);
            }

            ((ItemViewHolder) viewHolder).mImageViewProPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("user detail at position", "position: " + currentPos);

                }
            });
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        //TextView textView;
        public TextView mTextViewItemTitle;
        public ImageView mImageViewItemImg;
        public ImageView mImageViewProPic;

        public ItemViewHolder(View view) {
            super(view);
            //textView = (TextView) view.findViewById(android.R.id.text1);
            mImageViewProPic = (ImageView)itemView.findViewById(R.id.pro_img);
            mTextViewItemTitle = (TextView) itemView.findViewById(R.id.item_title);
            mImageViewItemImg = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }
}
