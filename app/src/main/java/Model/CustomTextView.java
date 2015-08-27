package Model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.sinapp.sharathsind.tradepost.R;

/**
 * Created by HenryChiang on 15-08-09.
 */
public class CustomTextView extends TextView {


    private static final String TAG = "TextView";

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        settingFont(context,attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        settingFont(context,attrs);
    }


    private void settingFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String customFont = a.getString(R.styleable.CustomTextView_typefaceAsset);
        Typeface tf = null;
        try {
            tf = FontManager.getTypeface(customFont,getContext());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            a.recycle();
            return;
        }

        setTypeface(tf);
        a.recycle();
    }

    public void settingOpenSansLight(){
        setTypeface(FontManager.getTypeface("fonts/OpenSansLight.ttf",getContext()));

    }

    public void settingOpenSansRegular(){
        setTypeface(FontManager.getTypeface("fonts/OpenSansRegular.ttf",getContext()));


    }

    public void settingOpenSansSemiBold(){
        setTypeface(FontManager.getTypeface("fonts/OpenSansSemiBold.ttf",getContext()));


    }

}