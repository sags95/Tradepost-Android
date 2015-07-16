package Model;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

import java.util.List;

/**
 * Created by HenryChiang on 15-07-13.
 */
public class MyOffersAdapter extends RecyclerView.Adapter<MyOffersAdapter.ViewHolder> {

    private List<MyOffersItem> mData;
    private Context mContext;

    public MyOffersAdapter(List<MyOffersItem> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public MyOffersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_myoffers_received, viewGroup, false);
        MyOffersAdapter.ViewHolder vh = new ViewHolder(v);
        //v.setOnClickListener(mItemClick);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.mItemTitle.setText(mData.get(i).getOfferItemTitle());
        viewHolder.mItemImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.mItemImg.setImageBitmap(mData.get(i).getOfferItemImg());
        if(mData.get(i).getOfferType()==1){
            viewHolder.mUserPic.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.mUserPic.setVisibility(View.VISIBLE);
            viewHolder.mUserPic.setImageBitmap(mData.get(i).getOfferItemUserImg());

        }
        viewHolder.mItemAction.setText(getItemActionStatus(mData.get(i).getOfferItemAction(),viewHolder.mItemAction,viewHolder.mItemImg));

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mItemTitle;
        public ImageView mItemImg, mUserPic;
        public Button mItemAction;


        public ViewHolder(View itemView) {
            super(itemView);
            mItemTitle = (TextView) itemView.findViewById(R.id.myoffer_item_title);
            mItemImg = (ImageView) itemView.findViewById(R.id.myoffers_img);
            mUserPic = (ImageView) itemView.findViewById(R.id.myoffers_user_pic);
            mItemAction = (Button) itemView.findViewById(R.id.myoffers_action_btn);

        }
    }

    public String getItemActionStatus(int itemStatus, Button actionButton, ImageView itemImg){
        String temp="";
        switch (itemStatus){
            case -1:
                temp = "DECLINED";
                actionButton.setTextColor(mContext.getResources().getColor(R.color.red));
                break;
            case 0:
                temp = "PENDING";
                actionButton.setTextColor(mContext.getResources().getColor(R.color.white));
                break;
            case 1:
                temp = "ACCEPTED";
                actionButton.setTextColor(mContext.getResources().getColor(R.color.green));
                break;
        }
        return temp;

    }
}
