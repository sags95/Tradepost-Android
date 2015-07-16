package Model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.sinapp.sharathsind.tradepost.R;

import java.util.List;

/**
 * Created by HenryChiang on 15-05-25.
 */

// Not Used

public class MarketPlaceDataAdapter extends ArrayAdapter<MarketPlaceData> {

        Activity activity;
        int resource;
        List<MarketPlaceData> datas;

        public MarketPlaceDataAdapter(Activity activity, int resource, List<MarketPlaceData> objects) {
            super(activity, resource, objects);

            this.activity = activity;
            this.resource = resource;
            this.datas = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final DealHolder holder;

            if (convertView == null) {
                LayoutInflater inflater = activity.getLayoutInflater();
                convertView = inflater.inflate(resource, parent, false);

                holder = new DealHolder();
                //holder.image = (ImageView)convertView.findViewById(R.id.image);
                //holder.title = (TextView)convertView.findViewById(R.id.pro_username);
                holder.description = (TextView)convertView.findViewById(R.id.item_title);

                convertView.setTag(holder);
            }
            else {
                holder = (DealHolder) convertView.getTag();
            }

            final MarketPlaceData data = datas.get(position);
            holder.image.setImageResource(R.drawable.sample_img);

            holder.image.setHeightRatio(1.0);
            holder.title.setText(data.proUsername);
            holder.description.setText(data.itemTitle);

            return convertView;
        }

        static class DealHolder {
            DynamicHeightImageView image;
            TextView title;
            TextView description;
        }
    }

