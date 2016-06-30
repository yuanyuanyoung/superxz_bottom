package com.dh.superxz_bottom.framework.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;

public class SysUtil {
	
	public static final int REQUEST_CODE_SEND_SMS = 1;
	
	// sim卡是否可读
	public static boolean isCanUseSim(Context context) {
		try {
			TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

			return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void sendSms(Activity activity,String phone_num,String sms_body) {
		Uri uri = Uri.parse("smsto://"+phone_num);

		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

		intent.putExtra("sms_body", sms_body);

		activity.startActivityForResult(intent, REQUEST_CODE_SEND_SMS);
	}
}
