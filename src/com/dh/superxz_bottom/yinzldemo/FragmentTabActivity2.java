package com.dh.superxz_bottom.yinzldemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.VehicleApp;
import com.dh.superxz_bottom.fragment.BitmapFragment;
import com.dh.superxz_bottom.fragment.EventBusFragment;
import com.dh.superxz_bottom.fragment.FragmentTabHost;
import com.dh.superxz_bottom.fragment.HttpFragment;
import com.dh.superxz_bottom.fragment.ListFragment;
import com.dh.superxz_bottom.fragment.ListNewFragment;
import com.dh.superxz_bottom.framework.util.Density;
import com.dh.superxz_bottom.xutils.sample.utils.PubUtils;

/**
 * @author 超级小志
 *
 */
public class FragmentTabActivity2 extends VehicleNoSwipbackActivity {
	public static final String HOME_TAB_TYPE = "tab_type";
	public static final String TAB_INDEX = "http";
	public static final String TAB_LEIGOU = "list";
	public static final String TAB_ORDER = "image";
	public static final String TAB_MY = "eventbus";

	private FragmentTabHost mTabHost;
	private View v_indicator;

	private int preTabPosition;
	private int currentTabPosition;
	private long exitTime = 0;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.tab_layout2);

		mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		addTab(TAB_INDEX, R.drawable.ic_launcher, R.drawable.ic_launcher,
				HttpFragment.class);
		addTab(TAB_LEIGOU, R.drawable.ic_launcher,
		// R.drawable.home_tab_leigou, LejiaquanFragment.class);
				R.drawable.ic_launcher, ListNewFragment.class);
		addTab(TAB_ORDER, R.drawable.ic_launcher, R.drawable.ic_launcher,
				BitmapFragment.class);
		addTab(TAB_MY, R.drawable.ic_launcher, R.drawable.ic_launcher,
				EventBusFragment.class);

		v_indicator = findViewById(R.id.v_indicator);
		final int indicator_width = Density.getSceenWidth(this) / 4;

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {// 设置v_indicator
				if (tabId.equals(TAB_INDEX)) {
					currentTabPosition = 0;
				} else if (tabId.equals(TAB_LEIGOU)) {
					currentTabPosition = 1;
				} else if (tabId.equals(TAB_ORDER)) {
					currentTabPosition = 2;
				} else if (tabId.equals(TAB_MY)) {
					currentTabPosition = 3;
				}

				if (currentTabPosition != preTabPosition) {
					int from_x, to_x;
					if (currentTabPosition > preTabPosition) {
						from_x = currentTabPosition * indicator_width
								- indicator_width;
						to_x = from_x + indicator_width;

					} else {
						from_x = currentTabPosition * indicator_width
								+ indicator_width;
						to_x = from_x - indicator_width;
					}
					Animation animation = new TranslateAnimation(from_x, to_x,
							0, 0);
					animation.setDuration(300);
					animation.setFillAfter(true);
					v_indicator.startAnimation(animation);
					preTabPosition = currentTabPosition;
				}
			}
		});
		mTabHost.setCurrentTab(0);
		// 查询红包
	}

	// 增加TAB
	public void addTab(String paramString, int selectedDrawableId,
			int normalDrawableId, Class<? extends Fragment> paramClass) {
		LinearLayout indicator = (LinearLayout) LayoutInflater.from(this)
				.inflate(R.layout.tab_indicator_holo2, null);
		TextView tv_text = (TextView) indicator.findViewById(R.id.tv_text);
		ImageView iv_icon = (ImageView) indicator.findViewById(R.id.iv_icon);
		tv_text.setText(paramString);
		iv_icon.setImageDrawable(getCheckedSelector(this, selectedDrawableId,
				normalDrawableId));
		mTabHost.addTab(mTabHost.newTabSpec(paramString)
				.setIndicator(indicator), paramClass, null);
	}

	/*
	 * 保证第一次启动页面后，可以调用执行fragment中的刷新相应方法
	 */
	@Override
	protected void onStart() {
		super.onStart();
		refreshTabFragment();
	}

	private void refreshTabFragment() {
		FragmentManager supportFragmentManager = getSupportFragmentManager();
		if (0 == mTabHost.getCurrentTab()) {
			if (null != supportFragmentManager) {
				Fragment f = supportFragmentManager
						.findFragmentByTag(TAB_INDEX);
				if (null != f && f instanceof HttpFragment) { //
					((HttpFragment) f).refreshData(false);
				}
			}
		} else if (1 == mTabHost.getCurrentTab()) {
			Fragment f = supportFragmentManager.findFragmentByTag(TAB_LEIGOU);
			if (null != f && f instanceof BitmapFragment) {
				((BitmapFragment) f).refreshData(false);
			}
		} else if (3 == mTabHost.getCurrentTab()) {
			Fragment f = supportFragmentManager.findFragmentByTag(TAB_MY);
			if (null != f && f instanceof EventBusFragment) {
				((EventBusFragment) f).refreshData(false);
			}
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (null != intent) {
			String tabType = intent.getStringExtra(HOME_TAB_TYPE);
			if (!TextUtils.isEmpty(tabType)) {
				if (tabType.equals(TAB_INDEX)) {
					currentTabPosition = 0;
				} else if (tabType.equals(TAB_LEIGOU)) {
					currentTabPosition = 1;
				} else if (tabType.equals(TAB_ORDER)) {
					currentTabPosition = 2;
				} else if (tabType.equals(TAB_MY)) {
					currentTabPosition = 3;
				}
				mTabHost.setCurrentTab(currentTabPosition);
			}

		}
	}

	private static StateListDrawable getCheckedSelector(Context context,
			int idSelectedId, int idNormalId) {
		StateListDrawable bg = new StateListDrawable();
		Drawable normal = idNormalId == -1 ? null : context.getResources()
				.getDrawable(idNormalId);
		Drawable selected = idSelectedId == -1 ? null : context.getResources()
				.getDrawable(idSelectedId);
		bg.addState(new int[] { android.R.attr.state_selected }, selected);
		bg.addState(new int[] { android.R.attr.state_pressed }, selected);
		bg.addState(new int[] {}, normal);
		return bg;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent arg2) {
		super.onActivityResult(requestCode, resultCode, arg2);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			default:
				break;
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				PubUtils.popTipOrWarn(this, "再按一次退出程序");
				exitTime = System.currentTimeMillis();
			} else {
				VehicleApp.getInstance().exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
