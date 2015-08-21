package Model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.sinapp.sharathsind.tradepost.R;

/**
 * EditText subclass created to enforce limit of the lines number in editable
 * text field
 */
public class LimitedEditText extends EditText {

    final private String TAG = "LimitedEditText";

    /**
     * Max lines to be present in editable text field
     */
    private int maxLines;

    /**
     * Max characters to be present in editable text field
     */
    private int maxCharacters;

    /**
     * application context;
     */
    private Context context;

    private String currentCharCount;

    public int getMaxCharacters() {
        return maxCharacters;
    }

    public String getCurrentCharCount() {
        return currentCharCount;
    }

    public void setCurrentCharCount(String currentCharCount) {
        this.currentCharCount = currentCharCount;
    }

    public void setMaxCharacters(int maxCharacters) {
        this.maxCharacters = maxCharacters;
    }

    @Override
    public int getMaxLines() {
        return maxLines;
    }

    @Override
    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
    }

    public LimitedEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        settingFont(context, attrs);

    }

    public LimitedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        settingFont(context, attrs);

    }

    public LimitedEditText(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        TextWatcher watcher = new TextWatcher() {

            private String text;
            private int beforeCursorPosition = 0;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                text = s.toString();
                beforeCursorPosition = start;
            }

            @Override
            public void afterTextChanged(Editable s) {

            /* turning off listener */
                removeTextChangedListener(this);

            /* handling lines limit exceed */
                if (LimitedEditText.this.getLineCount() > maxLines) {
                    LimitedEditText.this.setText(text);
                    LimitedEditText.this.setSelection(beforeCursorPosition);
                }

            /* handling character limit exceed */
                if (s.toString().length() > maxCharacters) {
                    LimitedEditText.this.setText(text);
                    LimitedEditText.this.setSelection(beforeCursorPosition);
                    Toast.makeText(context, "text too long", Toast.LENGTH_SHORT)
                            .show();
                }

            /* turning on listener */
                addTextChangedListener(this);

            }
        };

        this.addTextChangedListener(watcher);
    }

    private void settingFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.LimitedEditText);
        String customFont = a.getString(R.styleable.LimitedEditText_typefaceAsset);
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