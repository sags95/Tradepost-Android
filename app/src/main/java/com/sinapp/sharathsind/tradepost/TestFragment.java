package com.sinapp.sharathsind.tradepost;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

import Model.FontManager;

/**
 * Created by HenryChiang on 15-08-10.
 */
public class TestFragment extends Fragment {

    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_create_account, container, false);
        TextInputLayout username_til = (TextInputLayout)rootView.findViewById(R.id.create_account_username_til);
        TextInputLayout email_til = (TextInputLayout)rootView.findViewById(R.id.create_account_email_til);
        TextInputLayout pw_til = (TextInputLayout)rootView.findViewById(R.id.create_account_pw_til);
        TextInputLayout pw_confirm_til = (TextInputLayout)rootView.findViewById(R.id.create_account_pw_confirm_til);

        setTextInputTypeFace(username_til);
        setTextInputTypeFace(email_til);
        setTextInputTypeFace(pw_til);
        setTextInputTypeFace(pw_confirm_til);


        return rootView;
    }

    public void setTextInputTypeFace(TextInputLayout til){
        //final Typeface tf = Typeface.createFromAsset(getAssets(), "your_custom_font.ttf");
        final Typeface tf = FontManager.getTypeface("fonts/OpenSansLight.ttf", getActivity());

        til.getEditText().setTypeface(tf);

        try {
            // Retrieve the CollapsingTextHelper Field
            final Field cthf = til.getClass().getDeclaredField("mCollapsingTextHelper");
            cthf.setAccessible(true);

            // Retrieve an instance of CollapsingTextHelper and its TextPaint
            final Object cth = cthf.get(til);
            final Field tpf = cth.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);

            // Apply your Typeface to the CollapsingTextHelper TextPaint
            ((TextPaint) tpf.get(cth)).setTypeface(tf);
        } catch (Exception ignored) {
            // Nothing to do
        }
    }

}
