package Model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

/**
 * Created by HenryChiang on 15-06-23.
 */
public class OfferListViewAdapter extends BaseAdapter {

    LayoutInflater inflater;
    private String[] sampleData;
    Context context;



    public OfferListViewAdapter(Activity a,String[] sample) {
        // TODO Auto-generated constructor stub
        this.sampleData=sample;
        this.context=a;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return sampleData.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return sampleData[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.offer_list_item_row, null);
        }
        TextView itemName=(TextView)convertView.findViewById(R.id.offer_item_name);
        itemName.setText(sampleData[position]);

        return convertView;
    }

}
