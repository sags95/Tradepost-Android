package Model;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

import java.util.List;

/**
 * Created by HenryChiang on 15-07-12.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationItem> mData;

    public NotificationAdapter(List<NotificationItem> mData) {
        this.mData = mData;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final int currentPos = i;

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_notification, viewGroup, false);
        NotificationAdapter.ViewHolder vh = new ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final int currentPos = i;

        SpannableStringBuilder sb = new SpannableStringBuilder(mData.get(i).getUserPlaceholder() + " " + mData.get(i).getDefaultMsg(mData.get(i).getViewType()));
        sb.setSpan(new StyleSpan(Typeface.BOLD), 0, mData.get(i).getUserPlaceholder().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //viewHolder.mImageViewImg
        //viewHolder.mImageViewItemImg.setImageDrawable(mData.get(i).getDrawable());
        viewHolder.mTextViewMsg.setText(sb);
        viewHolder.itemView.setTag(mData.get(i).getViewType());
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public CustomTextView mTextViewMsg;
        public ImageView mImageViewImg;


        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewMsg = (CustomTextView) itemView.findViewById(R.id.noti_user_placeholder);
            mImageViewImg = (ImageView) itemView.findViewById(R.id.noti_img_placeholder);

        }
    }
}
