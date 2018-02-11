package com.mingle.widget.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * @author wangyu
 * @date 18-2-10
 * @describe TODO
 */

public class DimenUtil {
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    public static int sp2dp(Context context, float spVal) {
        return (int) px2dp(context, sp2px(context, spVal));
    }

    /**
     * 获取屏幕的高度，单位是PX
     *
     * @param context context
     */
    private static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return context.getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 默认的高度
     *
     * @param context context
     */
    public static int defaultHeight(Context context) {
        return (int) px2dp(context, DimenUtil.getScreenHeight(context) - DimenUtil.dp2px(context, 108));
    }
}
