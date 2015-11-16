package Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

import java.util.ArrayList;

/**
 * Created by HenryChiang on 2015-11-15.
 */
public class MyOffersDetailsAdapter extends RecyclerView.Adapter<MyOffersDetailsAdapter.ViewHolder> {

    private ArrayList<MyOffersItem> mData;
    private Context context;
    private View.OnClickListener itemClicked;

    public MyOffersDetailsAdapter(Context context,ArrayList<MyOffersItem> mData, View.OnClickListener itemClicked) {
        this.mData = mData;
        this.context=context;
        this.itemClicked=itemClicked;
    }

    @Override
    public MyOffersDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_my_offers_details, viewGroup, false);
        MyOffersDetailsAdapter.ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(itemClicked);

        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.mItemTitle.setText(mData.get(i).getOffersItemTitle());

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public CustomTextView mItemTitle, mUsername, mExtraCash, mStatus;
        public ImageView mItemImg, mProfileImg;



        public ViewHolder(View itemView) {
            super(itemView);
            mItemTitle = (CustomTextView) itemView.findViewById(R.id.myOffers_details_itemTitle);
            mUsername = (CustomTextView) itemView.findViewById(R.id.myOffers_details_username);
            mExtraCash = (CustomTextView) itemView.findViewById(R.id.myOffers_details_cash);
            mStatus = (CustomTextView) itemView.findViewById(R.id.myOffers_details_status);

            mItemImg = (ImageView) itemView.findViewById(R.id.myOffers_details_itemImg);
            mProfileImg = (ImageView) itemView.findViewById(R.id.myOffers_details_profileImg);




        }
    }
}
