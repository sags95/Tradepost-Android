package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import Model.CustomButton;

/**
 * Created by HenryChiang on 15-08-27.
 */
public class ListingProcessDoneActivity extends AppCompatActivity {

    private RelativeLayout successLayout,failedLayout;
ShareDialog shareDialog;
    CallbackManager callbackManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_process_done);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
 String s=result.getPostId();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        successLayout = (RelativeLayout)findViewById(R.id.listing_process_success_layout);
        failedLayout = (RelativeLayout)findViewById(R.id.listing_process_failed_layout);
        CustomButton b=(CustomButton)findViewById(R.id.b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap image=getIntent().getParcelableExtra("im") ;
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                if (ShareDialog.canShow(ShareLinkContent.class)) {


                    shareDialog.show(ListingProcessDoneActivity.this,content);
                }

            }
        });
        final boolean isSuccess = getIntent().getBooleanExtra("isSuccess",false);
        successLayout.setVisibility(isSuccess ? View.VISIBLE : View.GONE);
        failedLayout.setVisibility(isSuccess ? View.GONE : View.VISIBLE);

    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
