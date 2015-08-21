package Model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.util.Log;

import com.sinapp.sharathsind.tradepost.R;

/**
 * Created by HenryChiang on 15-08-11.
 */
public class CustomSwitchCompat extends SwitchCompat {

    final private String TAG = "SwitchCompat";


    public CustomSwitchCompat(Context context) {
        super(context);
    }

    public CustomSwitchCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        settingFont(context, attrs);

    }

    public CustomSwitchCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        settingFont(context, attrs);

    }

    private void settingFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomSwitchCompat);
        String customFont = a.getString(R.styleable.CustomSwitchCompat_typefaceAsset);
        Typeface tf = null;
        try {
            tf = FontManager.getTypeface(customFont, getContext());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            a.recycle();
            return;
        }

        setTypeface(tf);
        a.recycle();
    }
}
