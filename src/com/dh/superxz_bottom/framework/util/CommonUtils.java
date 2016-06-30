package com.dh.superxz_bottom.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class CommonUtils {

	/**
	 * 从Assets资源文件中，获取文件流数据...
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static byte[] getAssetsFileDate(Context context, String fileName) {
		AssetManager am = context.getResources().getAssets();
		InputStream is = null;
		try {
			is = am.open(fileName);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = -1;
			while ((len = is.read(buf)) != -1) {
				bos.write(buf, 0, len);
			}
			is.close();
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 获得网络连接是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo workinfo = con.getActiveNetworkInfo();
		if (workinfo == null || !workinfo.isAvailable()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 距离m转化为km
	 * 
	 * @param meterStr
	 *            距离(m)
	 * @return
	 */
	public static String getStringMeter2KM(int meterStr) {
		if (meterStr <= 1000) {
			return meterStr + "m";
		} else {
			int limit = 50 * 1000;
			if (meterStr <= limit) {
				DecimalFormat df = new DecimalFormat("0.0");
				Double floatDistance = (Double) (meterStr / 1000.00);
				return df.format(floatDistance) + "km";
			} else {
				return meterStr / 1000 + "km";
			}

		}
	}

	/**
	 * 校验银行卡卡号
	 * 
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId
				.substring(0, cardId.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * 
	 * @param nonCheckCodeCardId
	 * @return
	 */
	private static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null
				|| nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	public static void showKeyInput(final Context context,
			final EditText editText) {
		editText.requestFocus();
		editText.postDelayed(new Runnable() {

			@Override
			public void run() {
				((InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE))
						.showSoftInput(editText, 0);
				editText.setSelection(editText.getText().length());
			}
		}, 200);
	}

	public static Drawable getRecCorner(int stockColor, int stockWidth,
			int solidColor, int leftRadius, int rightRadius) {
		GradientDrawable drawable = new GradientDrawable();
		float[] radius = new float[] { leftRadius, leftRadius, rightRadius,
				rightRadius, rightRadius, rightRadius, leftRadius, leftRadius };
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setCornerRadii(radius);
		drawable.setStroke(stockWidth, stockColor);
		drawable.setColor(solidColor);
		return drawable;
	}

	public static Drawable getRecCorner(int stockColor, int solidColor,
			int leftRadius, int rightRadius) {
		return getRecCorner(stockColor, 1, solidColor, leftRadius, rightRadius);
	}

	public static StateListDrawable getCheckedSelector(Context context,
			Drawable checkedDrawable, Drawable normalDrawable) {
		StateListDrawable bg = new StateListDrawable();
		bg.addState(new int[] { android.R.attr.state_checked }, checkedDrawable);
		bg.addState(new int[] { android.R.attr.state_pressed }, normalDrawable);
		bg.addState(new int[] {}, normalDrawable);
		return bg;
	}
}
