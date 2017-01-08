package com.kymjs.arty.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.TextView;

import com.kymjs.arty.R;

public class ViewUtils {

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    /**
     * 设置TextView的drawable
     * @param tv
     * @param drawableId
     * @param gray 位置 ， Gravity.LEFT， Gravity.TOP， Gravity.RIGHT， Gravity.BOTTOM
     */
    public static void setTextViewDrawble(@NonNull TextView tv, int drawableId, int gray){
        Drawable drawable= tv.getContext().getResources().getDrawable(drawableId);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (gray){

        }
        switch (gray){
            case Gravity.LEFT:
                tv.setCompoundDrawables(drawable,null,null,null);
                break;
            case Gravity.TOP:
                tv.setCompoundDrawables(null,drawable,null,null);
                break;
            case Gravity.RIGHT:
                tv.setCompoundDrawables(null,null,drawable,null);
                break;
            case Gravity.BOTTOM:
                tv.setCompoundDrawables(null,null,null,drawable);
                break;
        }
    }


    public static void setTextViewDrawbleLeft(TextView tv, int drawableId){
        setTextViewDrawble(tv,drawableId,Gravity.LEFT);
    }

    public static void setTextViewDrawbleTop(TextView tv, int drawableId){
        setTextViewDrawble(tv,drawableId,Gravity.TOP);
    }

    public static void setTextViewDrawbleRight(TextView tv, int drawableId){
        setTextViewDrawble(tv,drawableId,Gravity.RIGHT);
    }

    public static void setTextViewDrawbleBottom(TextView tv, int drawableId){
        setTextViewDrawble(tv,drawableId,Gravity.BOTTOM);
    }
}
