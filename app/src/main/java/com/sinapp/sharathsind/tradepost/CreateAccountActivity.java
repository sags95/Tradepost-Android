package com.sinapp.sharathsind.tradepost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import Model.CustomButton;

/**
 * Created by HenryChiang on 15-08-25.
 */
public class CreateAccountActivity extends AppCompatActivity {

    private CustomButton nextButton;

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




    }


}
