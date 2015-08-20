package Model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.sinapp.sharathsind.tradepost.R;

/**
 * Created by HenryChiang on 15-08-09.
 */
public class CustomEditText extends EditText {

    final private String TAG = "EditText";

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(FontManager.settingFont(context, attrs, R.styleable.CustomEditText, R.styleable.CustomEditText_typefaceAsset));

    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(FontManager.settingFont(context, attrs, R.styleable.CustomEditText, R.styleable.CustomEditText_typefaceAsset));

    }

    private void settingFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        String customFont = a.getString(R.styleable.CustomEditText_typefaceAsset);
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
