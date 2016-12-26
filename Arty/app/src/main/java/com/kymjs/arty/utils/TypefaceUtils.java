package com.kymjs.arty.utils;

import android.graphics.Typeface;
import android.widget.TextView;

import com.kymjs.arty.Constant;

/**
 * Created by ZhangTao on 12/26/16.
 */

public class TypefaceUtils {

    private static Typeface simpleFont;
    private static Typeface traditionalFont;
    public static boolean justSimpleFont = false;

    /**
     * 设置字体,默认使用简体字
     *
     * @param view 待设置的view
     */
    public static void setTypeface(TextView view) {
        setTypeface(view, true);
    }

    /**
     * 设置字体
     *
     * @param view           待设置的view
     * @param useSimpleFonts 是否使用简体
     */
    public static void setTypeface(TextView view, boolean useSimpleFonts) {
        Typeface customFont;
        if (useSimpleFonts) {
            if (simpleFont == null) {
                customFont = simpleFont
                        = Typeface.createFromAsset(view.getContext().getAssets(), Constant.FONTS_SIMPLE);
            } else {
                customFont = simpleFont;
            }
        } else {
            if (traditionalFont == null) {
                customFont = Typeface.createFromAsset(view.getContext().getAssets(), Constant.FONTS_TRADITIONAL);
                if (!justSimpleFont) {
                    traditionalFont = customFont;
                }
            } else {
                customFont = traditionalFont;
            }
        }
        view.setTypeface(customFont);
    }
}
