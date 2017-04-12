package com.yunyouzhiyuan.qianbaoshangcheng.uitl;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by wangjunqiang on 2016/10/31.
 */
public class Text_Size {
    /**
     * textview 显示的文字大小 分两行
     * @param tv
     * @param text
     * @param one
     * @param tow
     * @param three
     * @param four
     */
    public static void setSize(Context context,TextView tv, String text, int one, int tow,String colors,int sizea, int three, int four,String colors1,int sizea1){
        SpannableString sp = new SpannableString(text);
        DisplayMetrics ff = context.getResources().getDisplayMetrics();
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sizea, ff);
        sp.setSpan(new AbsoluteSizeSpan(size), one,tow, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor(colors)), one, tow, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int size2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sizea1, ff);
        sp.setSpan(new AbsoluteSizeSpan(size2),three, four, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor(colors1)), three, four, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sp);

    }
}
