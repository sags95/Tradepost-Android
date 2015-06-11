package Model;

import android.support.v7.widget.RecyclerView;
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
public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.ViewHolder>{

    private List<MarketPlaceData> mData;

    public StaggeredAdapter(List<MarketPlaceData> mData) {
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_staggeredgrid, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.mTextViewDes.setText(mData.get(i).itemDescription);
        viewHolder.mTextViewTitle.setText(mData.get(i).itemTitle);
        viewHolder.mImageView.setImageResource(R.drawable.sample_img);
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewTitle;
        public TextView mTextViewDes;
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewTitle = (TextView)itemView.findViewById(R.id.title);
            mTextViewDes = (TextView)itemView.findViewById(R.id.description);
            mImageView = (ImageView)itemView.findViewById(R.id.image);
            //mImageView.setImageResource(R.drawable.sample_img);
        }
    }



}
