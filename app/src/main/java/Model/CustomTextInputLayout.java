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
        Typeface tf = FontManager.getTypeface(customFont, getContext());


        try {
            // Retrieve the CollapsingTextHelper Field
            final Field cthf = this.getClass().getDeclaredField("mCollapsingTextHelper");
            cthf.setAccessible(true);

            final Object cth = cthf.get(this);
            final Field tpf = cth.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);
            ((TextPaint) tpf.get(cth)).setTypeface(tf);
            a.recycle();

        } catch (Exception ignored) {
            ignored.printStackTrace();
            a.recycle();

        }
    }
}
