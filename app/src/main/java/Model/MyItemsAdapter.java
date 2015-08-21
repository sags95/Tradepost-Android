package Model;

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

import java.util.List;

/**
 * Created by HenryChiang on 15-08-10.
 */
public class MyItemsAdapter extends RecyclerView.Adapter<MyItemsAdapter.ViewHolder> {

    private List<MyItems> mData;

    public MyItemsAdapter(List<MyItems> mData) {
        this.mData = mData;
    }

    @Override
    public MyItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_my_items, viewGroup, false);
        MyItemsAdapter.ViewHolder vh = new ViewHolder(v);

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
