package Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sinapp.sharathsind.tradepost.ProfileActivity;
import com.sinapp.sharathsind.tradepost.R;
import com.sinapp.sharathsind.tradepost.SingleListingActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HenryChiang on 15-08-23.
 */
public class MarketPlaceListAdapter extends ArrayAdapter<MarketPlaceData> {

    private static final String TAG = "MarketPlaceListAdapter";

    static class ViewHolder {
        CustomTextView itemDateAdded,itemTitle;
        ImageView itemProPic;
        ImageView itemImg;
        com.wefika.flowlayout.FlowLayout fl;
        CardView cv;
        CustomTextView[] textViews = new CustomTextView[5];
        boolean isTagsSet=false;
    }

    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    public static ArrayList<MarketPlaceData> mData;
    private View.OnClickListener mItemClick;



    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();



    public MarketPlaceListAdapter(final Context context, int layoutResId,int textViewId, ArrayList<MarketPlaceData> mData,View.OnClickListener mItemClick) {
        super(context, layoutResId, textViewId, mData);
        mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
        this.mData=mData;
        this.mItemClick=mItemClick;

    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_marketplace, parent, false);
            vh = new ViewHolder();

            vh.cv = (CardView)convertView.findViewById(R.id.marketplace_card_view);
            vh.itemTitle = (CustomTextView) convertView.findViewById(R.id.item_title);
            vh.itemDateAdded = (CustomTextView) convertView.findViewById(R.id.pro_post_time);
            vh.itemImg = (ImageView)convertView.findViewById(R.id.item_image);
            vh.fl = (com.wefika.flowlayout.FlowLayout)convertView.findViewById(R.id.marketplace_tags_layout);
            vh.itemProPic = (CircleImageView)convertView.findViewById(R.id.pro_img);
            vh.textViews[0] = (CustomTextView)convertView.findViewById(R.id.marketplace_tag1);
            vh.textViews[1] = (CustomTextView)convertView.findViewById(R.id.marketplace_tag2);
            vh.textViews[2] = (CustomTextView)convertView.findViewById(R.id.marketplace_tag3);
            vh.textViews[3] = (CustomTextView)convertView.findViewById(R.id.marketplace_tag4);
            vh.textViews[4] = (CustomTextView)convertView.findViewById(R.id.marketplace_tag5);



            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        //

        vh.itemTitle.setText(mData.get(position).item.item.getItemname());
        vh.itemDateAdded.setText(String.valueOf(mData.get(position).item.item.getDateadded()));

        //
        vh.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("child position", String.valueOf(position));
                Intent i = new Intent(getContext().getApplicationContext(), SingleListingActivity.class);
                ArrayList<String> clickedItemDetails = new ArrayList<>();
                clickedItemDetails.add(0, String.valueOf(position));

                        //profile picture
                BitmapDrawable bitmapDrawable = (BitmapDrawable)((ImageView) v.findViewById(R.id.pro_img)).getDrawable();
                Bitmap b =bitmapDrawable.getBitmap();
                i.putExtra("profilePic",b);

                i.putStringArrayListExtra("itemClicked", clickedItemDetails);
                getContext().startActivity(i);

            }
        });

        //
        vh.itemProPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext().getApplicationContext(), ProfileActivity.class));

            }
        });

        //Images
        try {

            Uri url1 = Uri.parse("http://73.37.238.238:8084/TDserverWeb/images/items/" + mData.get(position).item.item.getItemid() + "/0.png");
            Picasso.with(getContext())
                    .load(url1)
                    .placeholder(R.drawable.sample_img3)
                    .into(vh.itemImg);


            Uri url=Uri.parse("http://73.37.238.238:8084/TDserverWeb/images/" + mData.get(position).userid + "/profile.png");
            Picasso.with(getContext())
                    .load(url)
                    .placeholder(R.drawable.sample_img)
                    .into(vh.itemProPic);

        } catch (Exception e) {
            e.printStackTrace();
        }


        //Tags
        /*
        if(vh.fl.getChildCount()==0) {
            if (mData.get(position).item.tags != null) {
                for (String tag : mData.get(position).item.tags) {
                    vh.fl.addView(addTags(tag));

                }
            }
        }
        */

        Log.e("tags count", "Position: " + position + " ,Tagscount: " + String.valueOf(mData.get(position).item.tags.length));
        /*
            switch (mData.get(position).item.tags.length) {
                case 1:
                    vh.textViews[0] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag1);
                    vh.textViews[0].setText(mData.get(position).item.tags[0].toUpperCase());
                    vh.textViews[0].setVisibility(View.VISIBLE);
                    break;
                case 2:
                    vh.textViews[0] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag1);
                    vh.textViews[1] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag2);
                    vh.textViews[0].setText(mData.get(position).item.tags[0].toUpperCase());
                    vh.textViews[1].setText(mData.get(position).item.tags[1].toUpperCase());
                    vh.textViews[0].setVisibility(View.VISIBLE);
                    vh.textViews[1].setVisibility(View.VISIBLE);
                    break;
                case 3:
                    vh.textViews[0] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag1);
                    vh.textViews[1] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag2);
                    vh.textViews[2] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag3);
                    vh.textViews[0].setText(mData.get(position).item.tags[0].toUpperCase());
                    vh.textViews[1].setText(mData.get(position).item.tags[1].toUpperCase());
                    vh.textViews[2].setText(mData.get(position).item.tags[2].toUpperCase());
                    vh.textViews[0].setVisibility(View.VISIBLE);
                    vh.textViews[1].setVisibility(View.VISIBLE);
                    vh.textViews[2].setVisibility(View.VISIBLE);

                    break;
                case 4:
                    vh.textViews[0] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag1);
                    vh.textViews[1] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag2);
                    vh.textViews[2] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag3);
                    vh.textViews[3] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag4);
                    vh.textViews[0].setText(mData.get(position).item.tags[0].toUpperCase());
                    vh.textViews[1].setText(mData.get(position).item.tags[1].toUpperCase());
                    vh.textViews[2].setText(mData.get(position).item.tags[2].toUpperCase());
                    vh.textViews[3].setText(mData.get(position).item.tags[3].toUpperCase());

                    vh.textViews[0].setVisibility(View.VISIBLE);
                    vh.textViews[1].setVisibility(View.VISIBLE);
                    vh.textViews[2].setVisibility(View.VISIBLE);
                    vh.textViews[3].setVisibility(View.VISIBLE);

                    break;
                case 5:
                    vh.textViews[0] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag1);
                    vh.textViews[1] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag2);
                    vh.textViews[2] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag3);
                    vh.textViews[3] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag4);
                    vh.textViews[4] = (CustomTextView) convertView.findViewById(R.id.marketplace_tag5);
                    vh.textViews[0].setText(mData.get(position).item.tags[0].toUpperCase());
                    vh.textViews[1].setText(mData.get(position).item.tags[1].toUpperCase());
                    vh.textViews[2].setText(mData.get(position).item.tags[2].toUpperCase());
                    vh.textViews[3].setText(mData.get(position).item.tags[3].toUpperCase());
                    vh.textViews[4].setText(mData.get(position).item.tags[4].toUpperCase());

                    vh.textViews[0].setVisibility(View.VISIBLE);
                    vh.textViews[1].setVisibility(View.VISIBLE);
                    vh.textViews[2].setVisibility(View.VISIBLE);
                    vh.textViews[3].setVisibility(View.VISIBLE);
                    vh.textViews[4].setVisibility(View.VISIBLE);

                    break;
            }
        */






        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }


    public CustomTextView addTags(String tag) {

        CustomTextView newTag = new CustomTextView(getContext());
        newTag.setText(tag.toUpperCase());
        newTag.setTextColor(getContext().getResources().getColor(R.color.white));
        newTag.setTextSize(10);
        newTag.setClickable(true);
        newTag.settingOpenSansLight();
        newTag.setBackgroundResource(R.drawable.tag_btn_shape);
        newTag.setPadding(DpToPx(4), DpToPx(4), DpToPx(4), DpToPx(4));
        com.wefika.flowlayout.FlowLayout.LayoutParams lp = new com.wefika.flowlayout.FlowLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,DpToPx(4), DpToPx(8));

        newTag.setLayoutParams(lp);



        return newTag;

    }


    public int DpToPx(int requireDp ){
        int dpValue = requireDp; // margin in dips
        float d = getContext().getResources().getDisplayMetrics().density;
        int margin = (int)(dpValue * d); // margin in pixels
        return margin; // margin in pixels

    }


}



