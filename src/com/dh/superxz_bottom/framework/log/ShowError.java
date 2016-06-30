package com.dh.superxz_bottom.framework.log;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.dh.superxz_bottom.framework.ui.TipDialog;

public class ShowError {

	private Activity mContext;

	private TipDialog mErrorDialog;

	private Handler handler;

	public ShowError(Activity ctx) {
		mContext = ctx;
		handler = new Handler(Looper.getMainLooper());
		runOnUiThread(new Runnable() {
			public void run() {
				if (mErrorDialog == null && mContext != null && !mContext.isDestroyed()) {
					mErrorDialog = new ErrorDialog(mContext);
				}

			}
		});
	}

	public ShowError(Activity ctx, TipDialog dialog) {
		this(ctx);
		mErrorDialog = dialog;
	}

	public void showErrorToast(final String error_info) {
		runOnUiThread(new Runnable() {

			public void run() {
				Toast.makeText(mContext, error_info, Toast.LENGTH_LONG).show();
			}
		});
	}

	public void showError(final String error_info) {
		runOnUiThread(new Runnable() {

			public void run() {
				if (mErrorDialog != null) {
					mErrorDialog.showTip(error_info);
				}

			}
		});
	}

	private void runOnUiThread(Runnable r) {
		boolean success = handler.post(r);
		while (!success) {
			handler = new Handler(Looper.getMainLooper());
			success = handler.post(r);
		}
	}
}