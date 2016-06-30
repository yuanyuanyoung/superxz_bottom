package com.dh.superxz_bottom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;
import com.dh.superxz_bottom.yinzldemo.VehicleActivity;

public class PubWebViewActivity extends VehicleActivity {

	@ViewInject(R.id.webview_pub)
	private WebView webview_pub;
	@ViewInject(R.id.btn_title_btn_back_layout)
	private RelativeLayout btn_title_btn_back_layout;
	private String title;
	private String webUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pub_web);
		super.initTop();
		setTitle("关于");
		ViewUtils.inject(this);
		Intent intent = getIntent();
		title = intent.getStringExtra("webtitle");
		webUrl = intent.getStringExtra("weburl");
		if (!TextUtils.isEmpty(title))
			setTitle(title);
		btn_title_btn_back_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (webview_pub.canGoBack()) {
					webview_pub.goBack();
				} else {
					finish();
				}
			}
		});
		// 设置WebView属性，能够执行Javascript脚本
		webview_pub.getSettings().setJavaScriptEnabled(true);
		// 加载需要显示的网页
		if (!TextUtils.isEmpty(webUrl)) {
			webview_pub.loadUrl(webUrl);
		}
		// 设置Web视图
		webview_pub.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url); // 在当前的webview中跳转到新的url;
				return true;
			}

		});
	}

	@Override
	// 设置回退
	// 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview_pub.canGoBack()) {
			webview_pub.goBack(); // goBack()表示返回WebView的上一页面
			return true;
		}
		this.finish();
		return false;
	}

	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}
}
