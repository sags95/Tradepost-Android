package Model;

import com.trade.tradepost.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToolBarAdaptar extends BaseAdapter {
    LayoutInflater inflater;

    public ToolBarAdaptar(Activity c) {
        // TODO Auto-generated constructor stub
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 11;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        convertView = (LinearLayout) inflater.inflate(R.layout.s, null);
        LinearLayout v = (LinearLayout) convertView;
        ImageView im = (ImageView) convertView.findViewById(R.id.profilepic);
        TextView email = (TextView) convertView.findViewById(R.id.emailtext);
        TextView email1 = (TextView) convertView.findViewById(R.id.username);
        email.setText(Variables.email);
        email1.setText(Variables.username);
        RoundImage r = new RoundImage(Variables.profilepic);
        im.setBackground(r);
        return convertView;
    }

}
