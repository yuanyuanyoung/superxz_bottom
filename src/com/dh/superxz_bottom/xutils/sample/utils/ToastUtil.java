/**
 * 
 */
package com.dh.superxz_bottom.xutils.sample.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * @author 超级小志
 * 
 */
public class ToastUtil {

	public static void showShort(Context context, String info) {
		if (null != context) {
			Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
		}
	}

	public static void showShort(Context context, int infoID) {
		if (null != context) {
			Toast.makeText(context, infoID, Toast.LENGTH_SHORT).show();
		}
	}

	public static void showLong(Context context, String info) {
		if (null != context) {
			Toast.makeText(context, info, Toast.LENGTH_LONG).show();
		}
	}

	public static void showLong(Context context, int infoID) {
		if (null != context) {
			Toast.makeText(context, infoID, Toast.LENGTH_LONG).show();
		}
	}

	public static void showTime(Context context, String info, int time) {
		if (null != context) {
			Toast.makeText(context, info, time).show();
		}
	}

	public static void showTime(Context context, int infoID, int time) {
		if (null != context) {
			Toast.makeText(context, infoID, time).show();
		}
	}
	private static Handler mHandler = new Handler();
	private static boolean isShowToast =true;
	/**
	 * 在之前的toast没消失之前，不会再弹出新的toast
	 * @param context
	 * @param info
	 */
	public static void showToastOnly(Context context, String info) {
		
		if (null != context) {
			if(isShowToast){
				Toast.makeText(context, info, 2000).show();
				isShowToast =false;
			}
			mHandler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					isShowToast =true;
				}
			}, 2000);
		}
	}


}
