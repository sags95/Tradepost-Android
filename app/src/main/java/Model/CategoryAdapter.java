package Model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

import java.util.ArrayList;

/**
 * Created by HenryChiang on 15-07-20.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private String[] mData;
    private int[] imgRes;

    public CategoryAdapter(String[] mData, int[] imgRes) {
        this.mData = mData;
        this.imgRes = imgRes;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_category, viewGroup, false);
        CategoryAdapter.ViewHolder vh = new ViewHolder(v);
        //v.setOnClickListener(mItemClick);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final int currentPos = i;
        viewHolder.categoryTitle.setText(mData[i]);
        viewHolder.categoryImg.setImageResource(imgRes[i]);



    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.length : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView categoryTitle;
        public ImageView categoryImg;


        public ViewHolder(View itemView) {
            super(itemView);
            categoryTitle = (TextView) itemView.findViewById(R.id.category_text);
            categoryImg = (ImageView) itemView.findViewById(R.id.category_img);

        }
    }

}
