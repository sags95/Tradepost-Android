package Model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HenryChiang on 15-06-10.
 */
public class StaggeredAdapter extends HeaderRecyclerViewAdapter {

    private List<MarketPlaceData> mData;
    private View.OnClickListener mItemClick;
    private int[] headerImgRes;
    private final static int NUM_IMAGES_MP = 4;
    private List<ImageView> dots;
    private Context mContext;


    public StaggeredAdapter(Context mContext,List<MarketPlaceData> mData,View.OnClickListener mItemClick, int[] headerImgRes) {
        this.mData = mData;
        this.mItemClick=mItemClick;
        this.headerImgRes = headerImgRes;
        this.mContext=mContext;
    }


    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType){
        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.header_staggered, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, //width
                ViewGroup.LayoutParams.WRAP_CONTENT);//height
                v.setLayoutParams(lp);
        RecyclerView.ViewHolder vh = new StaggeredHeaderViewHolder(v);
        return vh;

    }
    @Override
    public  void onBindHeaderView(RecyclerView.ViewHolder holder, int position){

    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_staggered, viewGroup, false);
        RecyclerView.ViewHolder vh = new StaggeredItemViewHolder(v);
        v.setOnClickListener(mItemClick);
        return vh;
    }


    @Override
    public  void onBindBasicItemView(RecyclerView.ViewHolder viewHolder, int i){
        final int currentPos = i;

        ((StaggeredItemViewHolder) viewHolder).mTextViewItemTitle.setText(mData.get(i).itemTitle);
        //((StaggeredItemViewHolder) viewHolder).mTextViewProUsername.setText(mData.get(i).proUsername);
        if (i % 2 == 0) {
            //viewHolder.mImageViewItemImg.setImageResource(R.drawable.sample_img);
            ((StaggeredItemViewHolder) viewHolder).mImageViewItemImg.setImageResource(R.drawable.sample_img);

        } else {
            //viewHolder.mImageViewItemImg.setImageResource(R.drawable.sample_img3);
            ((StaggeredItemViewHolder) viewHolder).mImageViewItemImg.setImageResource(R.drawable.sample_img3);

        }
        /*
        viewHolder.mImageViewProPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("user detail at position","position: " +currentPos);

            }
        });
    */
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        return 0;
    }


    class StaggeredItemViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView mTextViewItemTitle;
        public ImageView mImageViewItemImg;
        public ImageView mImageViewProPic;


        public StaggeredItemViewHolder(View itemView) {
            super(itemView);
            mImageViewProPic = (ImageView)itemView.findViewById(R.id.pro_img);
            mTextViewItemTitle = (TextView) itemView.findViewById(R.id.item_title);
            mImageViewItemImg = (ImageView) itemView.findViewById(R.id.item_image);

        }
    }

    class StaggeredHeaderViewHolder extends RecyclerView.ViewHolder {
        //image slider viewer
        public CustomPagerAdapter mCustomPagerAdapter;
        private ViewPager mViewPager;

        public StaggeredHeaderViewHolder(View itemView) {
            super(itemView);
            mViewPager = (ViewPager) itemView.findViewById(R.id.pager_staggered);
            Log.d("viewpager", String.valueOf(itemView.findViewById(R.id.pager_staggered)));
            mCustomPagerAdapter = new CustomPagerAdapter(itemView.getContext(), headerImgRes);
            mViewPager.setAdapter(mCustomPagerAdapter);

            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    selectDot(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            dots = new ArrayList<>();
            LinearLayout dotsLayout = (LinearLayout) itemView.findViewById(R.id.dots_staggered);

            for (int i = 0; i < NUM_IMAGES_MP; i++) {
                ImageView dot = new ImageView(mContext);
                if (i == 0) {
                    dot.setImageDrawable(mContext.getApplicationContext().getDrawable(R.drawable.pager_dot_selected));
                } else {
                    dot.setImageDrawable(mContext.getApplicationContext().getDrawable(R.drawable.pager_dot_not_selected));
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(3, 0, 3, 0);

                dotsLayout.addView(dot, params);

                // dotsLayout.addView(dot);
                dots.add(dot);
            }
        }

    }


        public boolean useHeader() {
            return true;
        }

        public int getBasicItemCount() {
            return mData.size();
        }

        public int getBasicItemType(int position) {
            return position;
        }


    public void selectDot(int idx) {
        Resources res = mContext.getApplicationContext().getResources();
        for (int i = 0; i < NUM_IMAGES_MP; i++) {
            int drawableId = (i == idx) ? (R.drawable.pager_dot_selected) : (R.drawable.pager_dot_not_selected);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);
        }
    }


}
