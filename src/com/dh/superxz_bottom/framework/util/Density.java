package com.dh.superxz_bottom.framework.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class Density {

	/**** dp转换为px */
	public static int of(Context context, int dp_value) {
		return (int) (dp_value * getDensity(context) + 0.5f);
	}

	/** 获取屏幕密度 **/
	public static float getDensity(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		return dm.density;
	}

	/**
	 * get the width of the device screen
	 * 
	 * @param context
	 * @return
	 */
	public static int getSceenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * get the height of the device screen
	 * 
	 * @param context
	 * @return
	 */
	public static int getSceenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
	   /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int sp2px(Context context, float spValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    } 
}