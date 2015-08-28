package Model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CheckBox;

import com.sinapp.sharathsind.tradepost.R;

/**
 * Created by HenryChiang on 15-08-27.
 */
public class CustomCheckBox extends CheckBox {

    final private String TAG = "CheckBox";


    public CustomCheckBox(Context context) {
        super(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        settingFont(context, attrs);

    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        settingFont(context, attrs);

    }

    private void settingFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomCheckBox);
        String customFont = a.getString(R.styleable.CustomCheckBox_typefaceAsset);
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
}
