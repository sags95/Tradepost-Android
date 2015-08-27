package Model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

/**
 * Created by HenryChiang on 15-08-11.
 */
public class MyFavoriteAdapter extends RecyclerView.Adapter<MyFavoriteAdapter.ViewHolder> {

    private List<MyFavoriteItem> mData;

    public MyFavoriteAdapter(List<MyFavoriteItem> mData) {
        this.mData = mData;
    }
    private ViewGroup viewGroup;
    private String[] tagsTest = {"Android","iphone","laptop","honda","chair"};


    @Override
    public MyFavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.viewGroup=viewGroup;
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_my_favorite, viewGroup, false);
        MyFavoriteAdapter.ViewHolder vh = new ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.mItemTitle.setText(mData.get(i).getItemTitle());
        if (i % 2 == 0) {
            viewHolder.mItemImg.setImageResource(R.drawable.sample_img5);
        } else if(i%3 ==0) {
            viewHolder.mItemImg.setImageResource(R.drawable.sample_img3);
        }else if(i%5 ==0) {
            viewHolder.mItemImg.setImageResource(R.drawable.sample_img6);
        }else{
            viewHolder.mItemImg.setImageResource(R.drawable.sample_img2);
        }

        if(tagsTest.length!=0) {
            if (viewHolder.mTagFlowLayout.getChildCount() == 0) {
                for (int j = 0; j < tagsTest.length; j++) {

                    viewHolder.mTagFlowLayout.addView(addTags(j));

                }
            }
        }


    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView mItemTitle;
        public ImageView mItemImg;
        public FlowLayout mTagFlowLayout;




        public ViewHolder(View itemView) {
            super(itemView);
            mItemTitle = (TextView) itemView.findViewById(R.id.myFav_itemTitle);
            mItemImg = (ImageView)itemView.findViewById(R.id.myFav_itemImg);
            mTagFlowLayout = (FlowLayout)itemView.findViewById(R.id.myFav_tags_layout);


        }
    }

    public CustomTextView addTags(int pos) {

        CustomTextView newTag = new CustomTextView(viewGroup.getContext());
        newTag.setText(tagsTest[pos].toUpperCase());
        newTag.setTextColor(viewGroup.getResources().getColor(R.color.white));
        newTag.setTextSize(11);
        newTag.setClickable(true);
        newTag.settingOpenSansLight();
        newTag.setBackgroundResource(R.drawable.tag_btn_shape);
        newTag.setPadding(10, 8, 10, 8);
        FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,10,20);
        newTag.setLayoutParams(lp);



        return newTag;

    }

}