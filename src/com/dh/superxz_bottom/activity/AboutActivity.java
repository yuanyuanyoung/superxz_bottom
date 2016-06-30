package com.dh.superxz_bottom.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.xutils.sample.utils.PubUtils;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;
import com.dh.superxz_bottom.yinzldemo.VehicleActivity;

public class AboutActivity extends VehicleActivity {
	@ViewInject(R.id.tv_version)
	private TextView tv_version;
	@ViewInject(R.id.tv_xieyi)
	private TextView tv_xieyi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		ViewUtils.inject(this);
		super.initTop();
		setTitle("关于");
		tv_version.setText(PubUtils.getAppVersionName(AboutActivity.this));

	}

}
