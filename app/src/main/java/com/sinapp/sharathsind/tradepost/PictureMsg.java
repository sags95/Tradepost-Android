package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import Model.RegisterWebService;
import data.MessageClass;


public class PictureMsg extends Activity {

Button send ,cancel;       Bitmap bitmap;
int offerid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_msg);
        Intent intent = getIntent();
       bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        offerid=(int)intent.getIntExtra("offerid",0);
        ImageView im=(ImageView)findViewById(R.id.imageView);
        im.setImageBitmap(bitmap);
        send= (Button) findViewById(R.id.send);
        cancel=(Button) findViewById(R.id.cancel);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageClass m=new MessageClass("",bitmap,Constants.userid);
               RegisterWebService.sendMsg(m.getMsg(), m.getPicmsg(), Constants.userid,offerid,PictureMsg.this);


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picture_msg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
