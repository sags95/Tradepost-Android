package Controllers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.sinapp.sharathsind.tradepost.ChatFragment;
import com.sinapp.sharathsind.tradepost.ChatPageFragment;
import com.sinapp.sharathsind.tradepost.R;

import Model.RegisterWebService;
import data.MessageClass;
import datamanager.userdata;

/**
 * Created by sharathsind on 2015-06-05.
 */
public class SendController implements View.OnClickListener {
    Activity f;
    EditText e;
  public   SendController(Activity fragment,EditText editText)
    {
        this.f=fragment;
        this.e=editText;

    }
    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.send_btn)
        {
String msg=e.getText().toString();
MessageClass m=RegisterWebService.sendMsg(msg, null, userdata.userid, ((ChatFragment) f).offerid,f);
            ((ChatFragment) f).updatemsg(m);
     e.setText("");

        }

    }
}
