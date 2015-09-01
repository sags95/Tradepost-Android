package Model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

import java.util.ArrayList;

/**
 * Created by HenryChiang on 15-07-19.
 */
public class OfferProcessMainAdapter extends RecyclerView.Adapter<OfferProcessMainAdapter.ViewHolder> {

    private ArrayList<OfferProcessItem> mData;

    public OfferProcessMainAdapter(ArrayList<OfferProcessItem> mData) {
        this.mData = mData;
    }

    @Override
    public OfferProcessMainAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_offer_process_main, viewGroup, false);
        OfferProcessMainAdapter.ViewHolder vh = new ViewHolder(v);
        //v.setOnClickListener(mItemClick);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final int currentPos = i;
        viewHolder.listingItemTitle.setText(mData.get(i).getItemTitle());

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CustomTextView listingItemTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            listingItemTitle = (CustomTextView) itemView.findViewById(R.id.listing_item_title_main);

        }
    }

}

