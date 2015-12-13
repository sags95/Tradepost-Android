package com.sinapp.sharathsind.tradepost;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.Tracker;

import Model.CustomButton;
import Model.CustomCheckBox;
import Model.CustomTextView;
import Model.LimitedEditText;
import Model.RegisterWebService;

/**
 * Created by HenryChiang on 15-08-25.
 */
public class CreateAccountActivity extends AppCompatActivity {

    private CustomButton nextButton;

    private LimitedEditText username,email,pw,pwConfirm;
    private CustomCheckBox showPw;
public String user,pwd,em;
    private Tracker mTracker;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        TradePost application = (TradePost) getApplication();
        mTracker = application.getDefaultTracker();
        //ctivity().getApplication()).getTracker(TrackerName.APP_TRACKER);

// Build and send exception.
        Thread.UncaughtExceptionHandler myHandler = new ExceptionReporter(
                mTracker,                                        // Currently used Tracker.
                Thread.getDefaultUncaughtExceptionHandler(),      // Current default uncaught exception handler.
                this);

        nextButton = (CustomButton)findViewById(R.id.create_account_next_btn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user =username.getText().toString();
                em =email.getText().toString();
                pwd =pw.getText().toString();



                new AsyncTask<String,String,String>(){
                    ContentValues cv;
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);

                        long l= Constants.db.insert("login",null,cv);
                        startActivity(new Intent(getApplicationContext(), CreateAccountPicActivity.class));
                    }

                    @Override
                    protected String doInBackground(String... params) {
Bitmap b= BitmapFactory.decodeResource(getResources(),
        R.drawable.sample_img);

                      cv= RegisterWebService.signUp(user, em, pwd, "email", b, false, Constants.db);
                        return null;
                    }
                }.execute(null,null,null);

            }
        });

        username = (LimitedEditText)findViewById(R.id.create_account_username);
        email = (LimitedEditText)findViewById(R.id.create_account_email);
        pw = (LimitedEditText)findViewById(R.id.create_account_pw);
        pwConfirm = (LimitedEditText)findViewById(R.id.create_account_pwConfirm);
        showPw = (CustomCheckBox)findViewById(R.id.create_account_showPw);

        username.setMaxCharacters(30);
        email.setMaxCharacters(30);
        pw.setMaxCharacters(12);
        pwConfirm.setMaxCharacters(12);

        showPw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pw.setTransformationMethod(null);
                    pwConfirm.setTransformationMethod(null);
                }else{
                    pw.setTransformationMethod(new PasswordTransformationMethod());
                    pwConfirm.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });









    }


}
