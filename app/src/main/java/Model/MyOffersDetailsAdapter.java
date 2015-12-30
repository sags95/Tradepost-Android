package Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.Constants;
import com.sinapp.sharathsind.tradepost.R;
import com.squareup.picasso.Picasso;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import datamanager.userdata;
import webservices.MainWebService;

/**
 * Created by HenryChiang on 2015-11-15.
 */
public class MyOffersDetailsAdapter extends RecyclerView.Adapter<MyOffersDetailsAdapter.ViewHolder> {

    private ArrayList<MyOffersItem> mData;
    private Context context;
    private View.OnClickListener itemClicked;
public ArrayList<Integer> offerid;
    public MyOffersDetailsAdapter(Context context,ArrayList<MyOffersItem> mData, View.OnClickListener itemClicked,ArrayList<Integer> usrid) {
        this.mData = mData;
        this.context=context;
        this.itemClicked=itemClicked;
    offerid=usrid;
    }

    @Override
    public MyOffersDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_my_offers_details, viewGroup, false);
        MyOffersDetailsAdapter.ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(itemClicked);

        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


        viewHolder.mItemImg.setImageBitmap(mData.get(i).getOffersItemImg());
        ///SQLiteDatabase db=context.openOrCreateDatabase("tradepostdb.db", MODE_PRIVATE, null);
        Cursor c= Constants.db.rawQuery("select * from offers where offerid=" + offerid.get(i), null);
        c.moveToFirst();

        int userid = c.getInt(c.getColumnIndex("recieveduserid"));
        int ruserid = c.getInt(c.getColumnIndex("userid"));
        int itemid = c.getInt(c.getColumnIndex("Itemid"));
        int getuser = userdata.userid != userid ? userid : ruserid;
        int status=c.getInt(c.getColumnIndex("status"));
        int cash=c.getInt(c.getColumnIndex("cash"));
        Uri url1 = Uri.parse("http://73.37.238.238:8084/TDserverWeb/images/" + getuser+"/profile.png");
        Picasso.with(context)
                .load(url1)
                .placeholder(R.drawable.image_placeholder)
                .into(viewHolder.mProfileImg);
        if(status==0)
        {
            viewHolder.mStatus.setText("PENDING");
        }
        else if(status==1)
        {
            viewHolder.mStatus.setText("ACCEPTED");
        }
        else if(status==2)
        {
            viewHolder.mStatus.setText("DECLINED");
        }
        else
        {
            viewHolder.mStatus.setText("FINALISED");
        }
        SoapObject o = new SoapObject("http://webser/", "getItemnameU");
        o.addProperty("userid", getuser);
        //this.userid.add(getuser);

        o.addProperty("itemid", itemid);
        KvmSerializable s = MainWebService.getMsg2(o, "http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/getItemnameURequest");
        SoapObject s1=(SoapObject)s;

        String itemname = null,username = null;
        if(s1!=null) {
            username = s1.getProperty(0).toString();
            itemname = s1.getProperty(1).toString();
        }
        viewHolder.mExtraCash.setText(String.valueOf(cash));
        viewHolder.mUsername.setText(username);
        viewHolder.mItemTitle.setText(getOffer(offerid.get(i),getuser));
       // viewHolder.mProfileImg.setImageBitmap();
c.close();
    }
public String getOffer(int offerid,int getuser)
{
    String itemname=null;
    Cursor c=Constants.db.rawQuery("select * from offeritems where offerid="+ offerid,null);
    c.moveToFirst();
    while(!c.isAfterLast()) {
        int itemid=c.getInt(c.getColumnIndex("itemid"));

        SoapObject o = new SoapObject("http://webser/", "getItemnameU");
        o.addProperty("userid", getuser);
        //this.userid.add(getuser);

        o.addProperty("itemid",itemid);
        KvmSerializable s = MainWebService.getMsg2(o, "http://73.37.238.238:8084/TDserverWeb/OfferWebService?wsdl", "http://webser/OfferWebService/getItemnameURequest");
        SoapObject s1=(SoapObject)s;
        String item1 = null,username = null;
        if(s1!=null) {
            username = s1.getProperty(0).toString();
            item1 = s1.getProperty(1).toString();
        }
        if(c.isFirst())
        {
            itemname=item1;
        }
        else{
            itemname+=","+itemname;
        }
        c.moveToNext();
    }
    c=Constants.db.rawQuery("select * from offeradditionalitems where offerid="+ offerid,null);
    c.moveToFirst();
    if(c.getCount()>0)
    {
        itemname=","+c.getString(c.getColumnIndex("ItemName"));
    }

c.close();
return itemname;
}

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public CustomTextView mItemTitle, mUsername, mExtraCash, mStatus;
        public ImageView mItemImg, mProfileImg;



        public ViewHolder(View itemView) {
            super(itemView);
            mItemTitle = (CustomTextView) itemView.findViewById(R.id.myOffers_details_itemTitle);
            mUsername = (CustomTextView) itemView.findViewById(R.id.myOffers_details_username);
            mExtraCash = (CustomTextView) itemView.findViewById(R.id.myOffers_details_cash);
            mStatus = (CustomTextView) itemView.findViewById(R.id.myOffers_details_status);

            mItemImg = (ImageView) itemView.findViewById(R.id.myOffers_details_itemImg);
            mProfileImg = (ImageView) itemView.findViewById(R.id.myOffers_details_profileImg);




        }
    }
}
