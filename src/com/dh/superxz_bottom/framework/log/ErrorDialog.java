package com.dh.superxz_bottom.framework.log;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dh.superxz_bottom.framework.ui.TipDialog;
import com.dh.superxz_bottom.framework.util.Density;

public class ErrorDialog extends TipDialog implements OnClickListener {

	private String msg;

	// private Button btn_ok;

	private TextView tv_msg;

	public ErrorDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		LinearLayout viewLayout = new LinearLayout(context);

		viewLayout.setLayoutParams(new LayoutParams(Density.getSceenWidth(context), Density.getSceenHeight(context) / 3));
		viewLayout.setBackgroundColor(Color.BLACK);
		viewLayout.setOrientation(LinearLayout.VERTICAL);

		viewLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		viewLayout.setPadding(Density.of(context, 10), Density.of(context, 10), Density.of(context, 10), Density.of(context, 10));

		tv_msg = new TextView(context);

		tv_msg.setLayoutParams(new LinearLayout.LayoutParams(Density.getSceenWidth(context) * 3 / 4, LayoutParams.WRAP_CONTENT));

		tv_msg.setMinHeight(Density.of(context, 50));
		tv_msg.setMaxHeight(Density.getSceenHeight(context)-Density.of(context, 100));
		tv_msg.setTextColor(Color.WHITE);

		tv_msg.setText(msg);

		Button btn_ok = new Button(context);

		btn_ok.setLayoutParams(new LinearLayout.LayoutParams(Density.getSceenWidth(context) * 3 / 4, Density.of(context, 40)));

		btn_ok.setText("确定");

		btn_ok.setOnClickListener(this);

		viewLayout.addView(tv_msg);
		viewLayout.addView(btn_ok);

		setContentView(viewLayout);
	}

	public void showTip(String msg) {
		//this.msg = msg;
		show();
		tv_msg.setText(msg);
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}

	public void onClick(View v) {
		dismiss();
	}

}
