package Model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import com.sinapp.sharathsind.tradepost.R;

/**
 * Created by HenryChiang on 15-08-09.
 */
public class CustomAutoCompleteTextView extends AutoCompleteTextView {

    private static final String TAG = "AutoCompleteTextView";


    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        settingFont(context, attrs);

    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        settingFont(context, attrs);

    }

    public CustomAutoCompleteTextView(Context context) {
        super(context);
    }

    private void settingFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String customFont = a.getString(R.styleable.CustomTextView_typefaceAsset);
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
