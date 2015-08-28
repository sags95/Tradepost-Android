package com.sinapp.sharathsind.tradepost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by HenryChiang on 15-08-27.
 */
public class ListingProcessDoneActivity extends AppCompatActivity {

    private RelativeLayout successLayout,failedLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_process_done);

        successLayout = (RelativeLayout)findViewById(R.id.listing_process_success_layout);
        failedLayout = (RelativeLayout)findViewById(R.id.listing_process_failed_layout);

        final boolean isSuccess = getIntent().getBooleanExtra("isSuccess",false);
        successLayout.setVisibility(isSuccess ? View.VISIBLE : View.GONE);
        failedLayout.setVisibility(isSuccess ? View.GONE : View.VISIBLE);

    }
}
