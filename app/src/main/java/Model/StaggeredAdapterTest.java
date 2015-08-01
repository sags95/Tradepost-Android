package Model;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

import org.apmem.tools.layouts.FlowLayout;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by HenryChiang on 15-06-10.
 */
public class StaggeredAdapterTest extends RecyclerView.Adapter<StaggeredAdapterTest.ViewHolder> {

    private List<MarketPlaceData> mData;
    private View.OnClickListener mItemClick;
    private ViewGroup viewGroup;
    private int TAGS_COUNT = 0;
    private String[] tagsTest={};


    public StaggeredAdapterTest(List<MarketPlaceData> mData, View.OnClickListener mItemClick, String[] tagsTest) {
        this.mData = mData;
        this.mItemClick=mItemClick;
        this.tagsTest = tagsTest;
    }

    public StaggeredAdapterTest(List<MarketPlaceData> mData) {
        this.mData = mData;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.viewGroup=viewGroup;
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_staggered, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(mItemClick);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final int currentPos = i;

        viewHolder.mTextViewItemTitle.setText(mData.get(i).itemTitle);


        if(viewHolder.mTagFlowLayout.getChildCount()==0) {
            for(int j=0;j<tagsTest.length;j++){

                viewHolder.mTagFlowLayout.addView(addTags(j));

            }
        }



        if (i % 2 == 0) {
            viewHolder.mImageViewItemImg.setImageResource(R.drawable.sample_img);
        } else if(i%3 ==0) {
            viewHolder.mImageViewItemImg.setImageResource(R.drawable.sample_img3);
        }else{
            viewHolder.mImageViewItemImg.setImageResource(R.drawable.sample_img2);
        }

        viewHolder.mImageViewProPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("user detail at position","position: " +currentPos);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewItemTitle;
        public ImageView mImageViewItemImg;
        public ImageView mImageViewProPic;
        public FlowLayout mTagFlowLayout;
      //  public TextView tagView1,tagView2,tagView3,tagView4,tagView5;



        public ViewHolder(View itemView) {
            super(itemView);
            mImageViewProPic = (ImageView)itemView.findViewById(R.id.pro_img);
            mTextViewItemTitle = (TextView) itemView.findViewById(R.id.item_title);
            mImageViewItemImg = (ImageView) itemView.findViewById(R.id.item_image);
            mTagFlowLayout = (FlowLayout)itemView.findViewById(R.id.marketplace_tags_layout);
            /*
            tagView1 = (TextView)itemView.findViewById(R.id.marketplace_tag_1);
            tagView2 = (TextView)itemView.findViewById(R.id.marketplace_tag_2);
            tagView3 = (TextView)itemView.findViewById(R.id.marketplace_tag_3);
            tagView4 = (TextView)itemView.findViewById(R.id.marketplace_tag_4);
            tagView5 = (TextView)itemView.findViewById(R.id.marketplace_tag_5);
            */


        }
    }


    public TextView addTags(int pos){


        TextView newTag = new TextView(viewGroup.getContext());
        newTag.setText(tagsTest[pos]);
        newTag.setTextColor(viewGroup.getResources().getColor(R.color.white));
        newTag.setTextSize(14);
        newTag.setTypeface(Typeface.DEFAULT_BOLD);
        newTag.setClickable(true);
        newTag.setBackgroundResource(R.drawable.tag_btn_shape);
        newTag.setPadding(8, 8, 8, 8);
        FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,20,20);
        newTag.setLayoutParams(lp);



        return newTag;

    }




}