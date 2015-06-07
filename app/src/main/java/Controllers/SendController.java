package Controllers;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.sinapp.sharathsind.tradepost.R;

import Model.RegisterWebService;

/**
 * Created by sharathsind on 2015-06-05.
 */
public class SendController implements View.OnClickListener {
    Activity a;
    EditText e;
  public   SendController(Activity activity,EditText editText)
    {
        this.a=activity;
        this.e=editText;

    }
    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.send_btn)
        {
String msg=e.getText().toString();
RegisterWebService.sendMsg(msg,null,2);

        }

    }
}
