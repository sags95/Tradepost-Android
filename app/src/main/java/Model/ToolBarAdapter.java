package Model;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

import java.util.ArrayList;

public class ToolBarAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<Variables> variablesArrayList;



    public ToolBarAdapter(Activity c, ArrayList<Variables> variablesArrayList) {
        // TODO Auto-generated constructor stub
        this.inflater = LayoutInflater.from(c);
        this.variablesArrayList=variablesArrayList;
    }

    /*private view holder class*/

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return variablesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return variablesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        /*
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.s, null);
            holder = new ViewHolder();
            holder.emailtext = (TextView) convertView.findViewById(R.id.emailtext);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.im = (ImageView) convertView.findViewById(R.id.profilepic);
            convertView.setTag(holder);
        }
        else



            convertView = inflater.inflate(R.layout.s, null);

        ImageView item_pic=(ImageView)convertView.findViewById(R.id.item_pic);
        TextView item_name=(TextView)convertView.findViewById(R.id.item_name);
        //name.setText(itemsmodel.get(position).getItemName());
        //img.setImageResource(itemsmodel.get(position).getItemImage());
        item_name.setText("sample");
        item_pic.setImageResource(R.drawable.ic_launcher);

        return convertView;*/


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.s, null);
        }
        ImageView img=(ImageView)convertView.findViewById(R.id.item_pic);
        TextView name=(TextView)convertView.findViewById(R.id.item_name);
        name.setText(variablesArrayList.get(position).getDrawerItemName());
        img.setImageBitmap(variablesArrayList.get(position).getDrawerItemImage());

        return convertView;
    }


}
