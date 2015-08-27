package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;

import Model.CustomButton;
import Model.CustomCheckBox;
import Model.CustomTextView;
import Model.LimitedEditText;

/**
 * Created by HenryChiang on 15-08-25.
 */
public class CreateAccountActivity extends AppCompatActivity {

    private CustomButton nextButton;
    private LimitedEditText username,email,pw,pwConfirm;
    private CustomCheckBox showPw;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        nextButton = (CustomButton)findViewById(R.id.create_account_next_btn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CreateAccountPicActivity.class));
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
