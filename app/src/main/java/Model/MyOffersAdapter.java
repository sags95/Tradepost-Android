package Model;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;
import com.sinapp.sharathsind.tradepost.SingleListingActivity;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HenryChiang on 15-07-13.
 */
public class MyOffersAdapter extends RecyclerView.Adapter<MyOffersAdapter.ViewHolder> {

    private List<MyOffersItem> mData;
    private View.OnClickListener mItemClick;

    public MyOffersAdapter(List<MyOffersItem> mData,  View.OnClickListener mItemClick) {
        this.mData = mData;
        this.mItemClick = mItemClick;
    }

    public MyOffersAdapter(List<MyOffersItem> mData){
        this.mData = mData;

    }
    @Override
    public MyOffersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v=null;
        ViewHolder vh;

        switch (viewType) {
            case 1:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_my_offers_one_tag, viewGroup, false);
                v.setOnClickListener(mItemClick);
                break;
            case 2:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_my_offers_two_tags, viewGroup, false);
                v.setOnClickListener(mItemClick);
                break;
            case 3:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_my_offers_three_tags, viewGroup, false);
                v.setOnClickListener(mItemClick);
                break;
            case 4:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_my_offers_four_tags, viewGroup, false);
                v.setOnClickListener(mItemClick);
                break;
            case 5:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_my_offers_five_tags, viewGroup, false);
                v.setOnClickListener(mItemClick);
                break;
        }

        vh = new ViewHolder(v, viewType);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.mItemTitle.setText(mData.get(i).getOffersItemTitle());
        viewHolder.mItemImg.setImageBitmap(mData.get(i).getOffersItemImg());
        viewHolder.mOffersCount.setText(String.valueOf(mData.get(i).getOffersCount()));
       viewHolder.mItemTitle.setTag(String.valueOf(mData.get(i).getItemid()));
        final int viewType=getItemViewType(i);

        switch (viewType) {
            case 1:
                viewHolder.tag1.setText("Sample".toUpperCase());
                break;
            case 2:
                viewHolder.tag1.setText("Sample".toUpperCase());
                viewHolder.tag2.setText("Sample".toUpperCase());
                break;
            case 3:
                viewHolder.tag1.setText("Sample".toUpperCase());
                viewHolder.tag2.setText("Sample".toUpperCase());
                viewHolder.tag3.setText("Sample".toUpperCase());
                break;
            case 4:
                viewHolder.tag1.setText("Sample".toUpperCase());
                viewHolder.tag2.setText("Sample".toUpperCase());
                viewHolder.tag3.setText("Sample".toUpperCase());
                viewHolder.tag4.setText("Sample".toUpperCase());
                break;
            case 5:
                viewHolder.tag1.setText("Sample".toUpperCase());
                viewHolder.tag2.setText("Sample".toUpperCase());
                viewHolder.tag3.setText("Sample".toUpperCase());
                viewHolder.tag4.setText("Sample".toUpperCase());
                viewHolder.tag5.setText("Sample".toUpperCase());
                break;
        }


    }

    @Override
    public int getItemViewType(int position) {
        if(mData==null){
            return 0;
        }else {
            return mData.get(position).getOffersCount();
        }
    }


    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CustomTextView mItemTitle, mOffersCount;
        public ImageView mItemImg;
        public FlowLayout mFlowLayout;
        public CustomTextView tag1, tag2, tag3, tag4, tag5;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            mItemTitle = (CustomTextView) itemView.findViewById(R.id.myOffers_itemTitle);
            mItemImg = (ImageView) itemView.findViewById(R.id.myOffers_itemImg);
            mOffersCount = (CustomTextView) itemView.findViewById(R.id.myOffers_offersCount);
            mFlowLayout = (FlowLayout) itemView.findViewById(R.id.myOffers_flowLayout);

            switch (viewType) {
                case 1:
                    tag1 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag1);
                    break;
                case 2:
                    tag1 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag1);
                    tag2 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag2);
                    break;
                case 3:
                    tag1 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag1);
                    tag2 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag2);
                    tag3 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag3);
                    break;
                case 4:
                    tag1 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag1);
                    tag2 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag2);
                    tag3 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag3);
                    tag4 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag4);
                    break;
                case 5:
                    tag1 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag1);
                    tag2 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag2);
                    tag3 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag3);
                    tag4 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag4);
                    tag5 = (CustomTextView) itemView.findViewById(R.id.myOffers_tag5);
                    break;


            }
        }
    }
}
