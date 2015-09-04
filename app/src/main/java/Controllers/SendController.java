package Controllers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.sinapp.sharathsind.tradepost.R;

import Model.CustomEditText;
import Model.RegisterWebService;

/**
 * Created by sharathsind on 2015-06-05.
 */
public class SendController implements View.OnClickListener {
    AppCompatActivity appCompatActivity;
    EditText e;
  public   SendController(AppCompatActivity appCompatActivity, CustomEditText editText)
    {
        this.appCompatActivity=appCompatActivity;
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
