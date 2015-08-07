package Model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sinapp.sharathsind.tradepost.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HenryChiang on 15-06-10.
 */
public class ProfileFeedbackAdapter extends RecyclerView.Adapter<ProfileFeedbackAdapter.ViewHolder> {

    private List<ProfileFeedbackItem> mData;

    public ProfileFeedbackAdapter(List<ProfileFeedbackItem> mData) {
        this.mData = mData;
    }

    @Override
    public ProfileFeedbackAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_profile_feedback, viewGroup, false);
        ProfileFeedbackAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final int currentPos = i;
        viewHolder.profileFeedbackUserName.setText(mData.get(i).getUsername());
        viewHolder.profileFeedbackRatingBar.setRating(mData.get(i).getRatingBarValue());
        //viewHolder.mImageViewItemImg.setImageDrawable(mData.get(i).getDrawable());
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView profileFeedbackUserName,profileFeedbackText;
        public RatingBar profileFeedbackRatingBar;
        public ImageView profileFeedbackUserImg;



        public ViewHolder(View itemView) {
            super(itemView);
            profileFeedbackUserName = (TextView) itemView.findViewById(R.id.profile_feedback_username_placeholder);
            profileFeedbackText = (TextView) itemView.findViewById(R.id.drawer_feedback_text);
            profileFeedbackRatingBar = (RatingBar) itemView.findViewById(R.id.profile_feedback_ratingbar);
            profileFeedbackUserImg = (ImageView) itemView.findViewById(R.id.profile_feedback_userImg_placeholder);

        }
    }
}
