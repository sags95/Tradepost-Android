package Model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import com.sinapp.sharathsind.tradepost.R;

/**
 * Created by HenryChiang on 15-08-10.
 */
public class CustomButton extends Button {

    final private String TAG = "Button";


    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        settingFont(context, attrs);

    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        settingFont(context, attrs);

    }

    private void settingFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomButton);
        String customFont = a.getString(R.styleable.CustomButton_typefaceAsset);
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
