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
                .inflate(R.layout.list_item_my_offers_new, viewGroup, false);
        MyOffersAdapter.ViewHolder vh = new ViewHolder(v);
        //v.setOnClickListener(mItemClick);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.mItemTitle.setText(mData.get(i).getOffersItemTitle());
        viewHolder.mItemImg.setImageBitmap(mData.get(i).getOffersItemImg());
        viewHolder.mOffersCount.setText(String.valueOf(mData.get(i).getOffersCount()));

        switch (mData.get(i).getOffersCount()){
            case 1:
                viewHolder.mFlowLayout.addView(addTags("Hello1"));
                break;
            case 2:
                viewHolder.mFlowLayout.addView(addTags("Hello1"));
                viewHolder.mFlowLayout.addView(addTags("Hello2"));
                break;
            case 3:
                viewHolder.mFlowLayout.addView(addTags("Hello1"));
                viewHolder.mFlowLayout.addView(addTags("Hello2"));
                viewHolder.mFlowLayout.addView(addTags("Hello3"));
                break;

            case 4:
                viewHolder.mFlowLayout.addView(addTags("Hello1"));
                viewHolder.mFlowLayout.addView(addTags("Hello2"));
                viewHolder.mFlowLayout.addView(addTags("Hello3"));
                viewHolder.mFlowLayout.addView(addTags("Hello4"));
                break;

            case 5:
                viewHolder.mFlowLayout.addView(addTags("Hello1"));
                viewHolder.mFlowLayout.addView(addTags("Hello2"));
                viewHolder.mFlowLayout.addView(addTags("Hello3"));
                viewHolder.mFlowLayout.addView(addTags("Hello4"));
                viewHolder.mFlowLayout.addView(addTags("Hello5"));
                break;


        }




    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public CustomTextView mItemTitle, mOffersCount;
        public ImageView mItemImg;
        public FlowLayout mFlowLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemTitle = (CustomTextView) itemView.findViewById(R.id.myOffers_itemTitle);
            mItemImg = (ImageView) itemView.findViewById(R.id.myOffers_itemImg);
            mOffersCount = (CustomTextView) itemView.findViewById(R.id.myOffers_offersCount);
            mFlowLayout=(FlowLayout)itemView.findViewById(R.id.myOffers_flowLayout);

        }
    }

    private CustomTextView addTags(String tag){

        CustomTextView newTag = new CustomTextView(mContext);
        newTag.setText(tag.toUpperCase());
        newTag.setTextColor(mContext.getResources().getColor(R.color.white));
        newTag.setTextSize(10f);
        newTag.setClickable(true);
        newTag.settingOpenSansLight();
        newTag.setBackgroundResource(R.drawable.tag_btn_shape);
        newTag.setPadding(DpToPx(4),DpToPx(4),DpToPx(4),DpToPx(4));
        FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, DpToPx(8),DpToPx(4));

        newTag.setLayoutParams(lp);

        return newTag;
    }

    private int DpToPx(int requireDp ){
        float d = mContext.getResources().getDisplayMetrics().density;
        return (int)(requireDp * d); // margin in pixels

    }


}
