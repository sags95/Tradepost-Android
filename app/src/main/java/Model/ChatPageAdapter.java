package Model;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinapp.sharathsind.tradepost.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by HenryChiang on 15-06-10.
 */
public class ChatPageAdapter extends RecyclerView.Adapter<ChatPageAdapter.ViewHolder> {

    private List<ChatPageItem> mData;
    private Context context;
    private View.OnClickListener mItemClick;

    public ChatPageAdapter(Context context,List<ChatPageItem> mData) {
        this.mData = mData;
        this.context=context;
    }

    public ChatPageAdapter(List<ChatPageItem> mData, View.OnClickListener mItemClick) {
        this.mData = mData;
        this.mItemClick=mItemClick;
    }

    @Override
    public ChatPageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_chatpage, viewGroup, false);
        v.setTag(mData.get(i).getOfferitem());
        ChatPageAdapter.ViewHolder vh = new ViewHolder(v);
    //    v.setOnClickListener(mItemClick);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.mTextViewItemName.setText(mData.get(i).getTitle());
      viewHolder.mImageViewItemImg.setImageBitmap(mData.get(i).getDrawable());
 viewHolder.user.setText(mData.get(i).getDetails());
        if (mData.get(i).sent!=null) {
            DateFormat fmt = new SimpleDateFormat("MMM dd,yyyy");

            viewHolder.date.setText(fmt.format(mData.get(i).sent));
            fmt = new SimpleDateFormat("h:mm a");

            viewHolder.time.setText(fmt.format(mData.get(i).sent));
        }
        else{
            DateFormat fmt = new SimpleDateFormat("MMM dd,yyyy");

            viewHolder.date.setText(fmt.format(Calendar.getInstance().getTime()));
            fmt = new SimpleDateFormat("h:mm a");

            viewHolder.time.setText(fmt.format(Calendar.getInstance().getTime()));
        }
        }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView mTextViewItemName;
        public CustomTextView user,date,time;
        public ImageView mImageViewItemImg;


        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewItemName = (TextView) itemView.findViewById(R.id.chat_item_title);
            mImageViewItemImg = (ImageView) itemView.findViewById(R.id.chat_item_img);
            user=(CustomTextView)itemView.findViewById(R.id.chat_username_placeholder);
            date=(CustomTextView)itemView.findViewById(R.id.chat_date_date);
            time=(CustomTextView)itemView.findViewById(R.id.chat_date_time);

        }
    }
}
