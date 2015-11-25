package Model;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;
import com.sinapp.sharathsind.tradepost.Search;

import java.util.ArrayList;

/**
 * Created by HenryChiang on 15-07-20.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private String[] mData;
    private int[] imgRes;
static Context context;
    public CategoryAdapter(String[] mData, int[] imgRes,Context  c) {
        this.mData = mData;
        this.imgRes = imgRes;
    context=c;
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
        final String t=viewHolder.categoryTitle.getText().toString();
        viewHolder.categoryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Search.class);
                intent.putExtra("type", "cat");
                intent.putExtra(SearchManager.QUERY,t );
                context.startActivity(intent);
            }
        });


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
