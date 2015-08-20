package Model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;


import java.util.Hashtable;

public class FontManager {

    private static Hashtable<String, Typeface> fontCache = new Hashtable<>();

    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontname, typeface);
        }

        return typeface;
    }


    public static Typeface settingFont(Context ctx, AttributeSet attrs, int[] i , int j) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, i);
        String customFont = a.getString(j);
        Typeface tf = null;
        try {
            tf = getTypeface(customFont, ctx);
        } catch (Exception e) {
            a.recycle();
            return null;
        }
        a.recycle();
        return tf;

    }
}