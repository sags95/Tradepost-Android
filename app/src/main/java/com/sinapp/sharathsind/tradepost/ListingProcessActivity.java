package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import Model.RoundImageHelper;

/**
 * Created by HenryChiang on 15-05-31.
 */
public class ListingProcessActivity extends Activity{



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_process);


        ImageView camera = (ImageView)findViewById(R.id.section2_img_camera);
        ImageView folder = (ImageView)findViewById(R.id.section2_img_folder);
        Bitmap roundProImg= RoundImageHelper.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_section2_camera));
        camera.setImageBitmap(roundProImg);
        folder.setImageBitmap(roundProImg);
    }

}