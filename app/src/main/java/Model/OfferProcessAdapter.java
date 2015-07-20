package Model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.sinapp.sharathsind.tradepost.R;

import java.util.ArrayList;

/**
 * Created by HenryChiang on 15-06-10.
 */
public class OfferProcessAdapter extends RecyclerView.Adapter<OfferProcessAdapter.ViewHolder> {

    private ArrayList<OfferProcessItem> mData;

    public OfferProcessAdapter(ArrayList<OfferProcessItem> mData) {
        this.mData = mData;
    }

    @Override
    public OfferProcessAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_offer_process, viewGroup, false);
        OfferProcessAdapter.ViewHolder vh = new ViewHolder(v);
        //v.setOnClickListener(mItemClick);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final int currentPos = i;
        viewHolder.listingItemTitle.setText(mData.get(i).getItemTitle());
        viewHolder.listingCheckBox.setChecked(mData.get(i).isSelected());
        viewHolder.listingCheckBox.setTag(mData.get(i));

        viewHolder.listingCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                OfferProcessItem offerProcessItem = (OfferProcessItem) cb.getTag();

                offerProcessItem.setIsSelected(cb.isChecked());
                mData.get(currentPos).setIsSelected(cb.isChecked());
                Log.d("checkbox inside adapter", mData.get(currentPos).getItemTitle() + " is "
                        + cb.isChecked());
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView listingItemTitle;
        public CheckBox listingCheckBox;


        public ViewHolder(View itemView) {
            super(itemView);
            listingItemTitle = (TextView) itemView.findViewById(R.id.listing_item_title);
            listingCheckBox = (CheckBox) itemView.findViewById(R.id.listing_checkbox);

        }
    }

    public ArrayList<OfferProcessItem> getmData(){
        return  mData;
    }
}
