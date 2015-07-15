package Model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinapp.sharathsind.tradepost.R;

import java.util.List;

/**
 * Created by HenryChiang on 15-06-10.
 */
public class ChatPageAdapter extends RecyclerView.Adapter<ChatPageAdapter.ViewHolder> {

    private List<ChatPageItem> mData;

    public ChatPageAdapter(List<ChatPageItem> mData) {
        this.mData = mData;
    }

    @Override
    public ChatPageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_chatpage, viewGroup, false);
        ChatPageAdapter.ViewHolder vh = new ViewHolder(v);
        //v.setOnClickListener(mItemClick);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final int currentPos = i;
        viewHolder.mTextViewItemName.setText(mData.get(i).getTitle());
        viewHolder.mTextViewItemDetail.setText(mData.get(i).getDetails());
        //viewHolder.mImageViewItemImg.setImageDrawable(mData.get(i).getDrawable());
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView mTextViewItemName;
        public TextView mTextViewItemDetail;
        public ImageView mImageViewItemImg;


        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewItemName = (TextView) itemView.findViewById(R.id.chat_item_name);
            mTextViewItemDetail = (TextView) itemView.findViewById(R.id.chat_item_detail);
            mImageViewItemImg = (ImageView) itemView.findViewById(R.id.chat_item_img);

        }
    }
}
