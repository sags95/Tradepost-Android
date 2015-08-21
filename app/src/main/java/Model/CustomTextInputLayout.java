package Model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;

import com.sinapp.sharathsind.tradepost.R;

import java.lang.reflect.Field;

/**
 * Created by HenryChiang on 15-08-10.
 */
public class CustomTextInputLayout extends TextInputLayout {

    final private String TAG = "TextInputLayout";

    public CustomTextInputLayout(Context context) {
        super(context);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        settingFont(context, attrs);

    }

    private void settingFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextInputLayout);
        String customFont = a.getString(R.styleable.CustomTextInputLayout_typefaceAsset);
        Typeface tf = null;
        try {
            tf = FontManager.getTypeface(customFont, getContext());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            a.recycle();
            return;
        }

        getEditText().setTypeface(tf);

        try{
            // Retrieve the CollapsingTextHelper Field
            final Field cthf = getClass().getDeclaredField("mCollapsingTextHelper");
            cthf.setAccessible(true);

            // Retrieve an instance of CollapsingTextHelper and its TextPaint
            final Object cth = cthf.get(getClass());
            final Field tpf = cth.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);

            // Apply your Typeface to the CollapsingTextHelper TextPaint
            ((TextPaint) tpf.get(cth)).setTypeface(tf);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        a.recycle();
    }
}
