package com.dh.superxz_bottom.yinzldemo;

/*
 * Copyright (C), 2014-2014, 联创车盟汽车服务有限公司
 * FileName: BaseFragment
 * Author:   jun.w
 * Date:     2014/11/7 09:09
 * Description: Fragment基类
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dh.superxz_bottom.VehicleApp;
import com.dh.superxz_bottom.constant.Constant;
import com.dh.superxz_bottom.framework.db.AfeiDb;

public class VehicleFragment extends Fragment {

	public AfeiDb afeiDb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		afeiDb = VehicleApp.getInstance().getAfeiDb();
		if (null == afeiDb) {
			afeiDb = AfeiDb.create(getActivity(), Constant.DB_NAME, true);
		}
	}

	public void launch(Class<? extends Activity> clazz) {
		launch(new Intent(getActivity(), clazz));
	}

	public void launch(Intent intent) {
		startActivity(intent);
	}

	public void showToast(String message) {
		// Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
	}

	public void showToast(int messageId) {
		// Toast.makeText(getActivity(), messageId,Toast.LENGTH_LONG).show();
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getViewById(View view, int id) {
		return (T) view.findViewById(id);
	}

	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

}
