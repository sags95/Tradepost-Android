package Model;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.gms.plus.model.people.Person;
import com.sinapp.sharathsind.tradepost.Constants;
import com.sinapp.sharathsind.tradepost.R;
import  android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


import data.MessageClass;

/**
 * Created by sharathsind on 2015-06-06.
 */
public class MessageAdapter extends BaseAdapter{
  public   ArrayList<MessageClass>m;
    Activity b;
    public MessageAdapter(Activity a,ArrayList<MessageClass>mes)
    {
        b=a;
        m=mes;


    }
    @Override
    public int getCount() {
        return m.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageClass m1=m.get(position);
        LayoutInflater inflater=b.getLayoutInflater();
        int userid=m1.getUserid();
        View v;
        ImageView v1;
        TextView v2;
        if(userid== Constants.userid)
        {
            v = inflater.inflate(R.layout.right_message_view, parent, false);
            v2= (TextView) v.findViewById(R.id.Right_msg);
            v1=(ImageView)v.findViewById(R.id.image_msg);
            if(m1.getPicmsg()!=null)
            {

          v1.setImageBitmap(m1.getPicmsg());


            }
            else{
                v2.setText(m1.getMsg());
            }

        }
        else{
            v = inflater.inflate(R.layout.left_message_view, parent, false);
            v2= (TextView) v.findViewById(R.id.Left_msg);
           v1=(ImageView)v.findViewById(R.id.image_msg);
            if(m1.getPicmsg()!=null)
            {

                v1.setImageBitmap(m1.getPicmsg());


            }
            else{
                v2.setText(m1.getMsg());
            }
        }
        return v;
    }
}
