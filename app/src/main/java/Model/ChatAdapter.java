package Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import datamanager.userdata;

/**
 * Created by HenryChiang on 15-09-08.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private ArrayList<ChatItem> mData;
    private Context context;
    private View.OnClickListener mItemClick;

    public ChatAdapter(Context context, ArrayList<ChatItem> mData) {
        this.mData = mData;
        this.context = context;
    }

    public ChatAdapter(ArrayList<ChatItem> mData, View.OnClickListener mItemClick) {
        this.mData = mData;
        this.mItemClick = mItemClick;
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v=null;
        ViewHolder vh;
        switch (viewType) {
            //user self message
            case 1:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.right_message_view, viewGroup, false);
                v.setOnClickListener(mItemClick);
                break;
            //other user message
            case 2:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.left_message_view, viewGroup, false);
                v.setOnClickListener(mItemClick);
                break;
        }
        vh = new ViewHolder(v, viewType);
        return vh;

    }

    @Override
    public int getItemViewType(int position) {
        if (mData == null) {
            return 0;
        } else {
            //if it the same user, right message (viewType = 1)
            //else, left message (viewType = 2)
            return mData.get(position).getUserId() == userdata.userid ? 1 : 2;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        final ChatItem chatItem = mData.get(i);
        final int viewType = getItemViewType(i);
        viewHolder.bind(chatItem, viewType);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CustomTextView mTextViewItemName;
        public ImageView mImageViewItemImg;


        public ViewHolder(View itemView,int viewType) {
            super(itemView);
            switch (viewType) {
                case 1:
                    mImageViewItemImg = (ImageView) itemView.findViewById(R.id.item_image);
                    break;
                case 2:
                    mImageViewItemImg = (ImageView) itemView.findViewById(R.id.item_image);
                    break;
            }

        }

        public void bind(ChatItem data, int viewType) {

        }

    }
}

