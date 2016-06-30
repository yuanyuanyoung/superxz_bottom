package com.dh.superxz_bottom.dhutils;

import android.content.Context;
import android.widget.Toast;

/**
 * 这个自定义的Toast用于频繁弹出Toast时取消之前的toast，只显示最后一个Toast
 */
public class MyToast {
    public static final int LENGTH_SHORT = 1000;
    public static final int LENGTH_MID = 1500;
    public static final int LENGTH_LONG = 2000;

    private static Toast toast;

    private static void showToast(Context ctx, CharSequence msg, int duration) {
	if (null != toast) {
	    toast.setText(msg);
	} else {
	    toast = android.widget.Toast.makeText(ctx, msg, duration);
	}
	toast.show();
    }

    /**
     * 弹出Toast
     * 
     * @param ctx
     *            弹出Toast的上下文
     * @param msg
     *            弹出Toast的内容
     * @param duration
     *            弹出Toast的持续时间
     */
    public static void show(Context ctx, CharSequence msg, int duration)
	    throws NullPointerException {
	if (null == ctx) {
	    throw new NullPointerException("The ctx is null!");
	}
	if (0 > duration) {
	    duration = LENGTH_SHORT;
	}
	showToast(ctx, msg, duration);
    }

    /**
     * 弹出Toast
     * 
     * @param ctx
     *            弹出Toast的上下文
     * @param msg
     *            弹出Toast的内容的资源ID
     * @param duration
     *            弹出Toast的持续时间
     */
    public static void show(Context ctx, int resId, int duration)
	    throws NullPointerException {
	if (null == ctx) {
	    throw new NullPointerException("The ctx is null!");
	}
	if (0 > duration) {
	    duration = LENGTH_SHORT;
	}
	showToast(ctx, ctx.getResources().getString(resId), duration);
    }

}
