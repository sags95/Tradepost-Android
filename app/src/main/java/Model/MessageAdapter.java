package Model;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.gms.plus.model.people.Person;
import com.sinapp.sharathsind.tradepost.Constants;
import com.sinapp.sharathsind.tradepost.R;
import com.squareup.picasso.Picasso;

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
            if(m1.msgpath!=null&&m1.msgpath.length()<0)
            {
                v2.setVisibility(View.GONE);
                v1.setVisibility(View.VISIBLE);
                Uri url1 = Uri.parse("http://73.37.238.238:8084/TDserverWeb/images/"+m1.msgpath);
                Picasso.with(b)
                        .load(url1)
                        .into(v1);
                //v1.setImageBitmap(m1.getPicmsg());


            }
            else{
              ShowMessageTask sh=new ShowMessageTask(v2);
                sh.execute(m1.getMsg());
              //  v2.setText(m1.getMsg());
            }

        }
        else{
            v = inflater.inflate(R.layout.left_message_view, parent, false);
            v2= (TextView) v.findViewById(R.id.Left_msg);
           v1=(ImageView)v.findViewById(R.id.image_msg);
            if(m1.msgpath!=null&&m1.msgpath.length()<0)
            {

                v2.setVisibility(View.GONE);
                v1.setVisibility(View.VISIBLE);
                Uri url1 = Uri.parse("http://73.37.238.238:8084/TDserverWeb/images/"+m1.msgpath);
                Picasso.with(b)
                        .load(url1)
                        .into(v1);

            }
            else{
                ShowMessageTask sh=new ShowMessageTask(v2);
                sh.execute(m1.getMsg());
            }
        }
        return v;
    }
    class ShowMessageTask extends AsyncTask<String, Void, Spanned> {

        private TextView textView;

        public ShowMessageTask(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Spanned doInBackground(String... message) {

            Spanned spannedMessage = Html.fromHtml(message[0], imageGetter, null);
            return spannedMessage;
        }

        @Override
        protected void onPostExecute(Spanned spannedMessage) {
            textView.setText(spannedMessage);
        }
    }

    /**
     * Image getter for showing the emoticons in the message.
     */
    Html.ImageGetter imageGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {

            try {
                Drawable drawable = Drawable.createFromPath(source);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());

                return drawable;
            } catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    };




}
