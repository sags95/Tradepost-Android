package Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;
import com.sinapp.sharathsind.tradepost.SingleListingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HenryChiang on 15-08-10.
 */
public class MyItemsAdapter extends RecyclerView.Adapter<MyItemsAdapter.ViewHolder> {

    private ArrayList<MyItems> mData;
    private Context context;
    private View.OnClickListener itemClicked;

    public MyItemsAdapter(Context context,ArrayList<MyItems> mData, View.OnClickListener itemClicked) {
        this.mData = mData;
        this.context=context;
        this.itemClicked=itemClicked;
    }

    @Override
    public MyItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_my_items, viewGroup, false);
        MyItemsAdapter.ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(itemClicked);

        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.mItemTitle.setText(mData.get(i).getItemTitle());

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView mItemTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            mItemTitle = (TextView) itemView.findViewById(R.id.myItems_title);

        }
    }
}
