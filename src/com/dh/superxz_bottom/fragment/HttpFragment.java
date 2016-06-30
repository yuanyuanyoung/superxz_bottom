package com.dh.superxz_bottom.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.VehicleApp;
import com.dh.superxz_bottom.alibaba.fastjson.JSON;
import com.dh.superxz_bottom.constant.Constant;
import com.dh.superxz_bottom.entity.HrVOBean;
import com.dh.superxz_bottom.framework.db.AfeiDb;
import com.dh.superxz_bottom.framework.log.L;
import com.dh.superxz_bottom.framework.net.exception.DataException;
import com.dh.superxz_bottom.framework.net.fgview.Action;
import com.dh.superxz_bottom.framework.net.fgview.BaseParser;
import com.dh.superxz_bottom.framework.net.fgview.OnResponseListener;
import com.dh.superxz_bottom.framework.net.fgview.Request;
import com.dh.superxz_bottom.framework.net.fgview.Response;
import com.dh.superxz_bottom.framework.net.fgview.Response.ErrorMsg;
import com.dh.superxz_bottom.xutils.sample.utils.Preference;
import com.dh.superxz_bottom.xutils.sample.utils.ToastUtil;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;
import com.dh.superxz_bottom.yinzldemo.VehicleFragment;

/**
 * @author 超级小志
 * 
 */
public class HttpFragment extends VehicleFragment {

	// private HttpHandler handler;

	private Context mAppContext;
	@ViewInject(R.id.tv_test)
	private TextView tv;
	private AfeiDb afeiDb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main, container, false);
		ViewUtils.inject(this, view);

		mAppContext = inflater.getContext().getApplicationContext();
		queryCouponListByOrderId(true);
		// 存入用户名及密码
		afeiDb = VehicleApp.getInstance().getAfeiDb();
		if (null == afeiDb) {
			afeiDb = AfeiDb.create(getActivity(), Constant.DB_NAME, true);
		}
		if (!TextUtils.isEmpty(Preference.getString("hrdate"))) {
			ToastUtil.showLong(getActivity(), Preference.getString("hrdate"));
			List<HrVOBean> list = JSON.parseArray(
					Preference.getString("hrdate"), HrVOBean.class);
			initDate(list);
		}
		return view;
	}

	private void initDate(List<HrVOBean> list) {
		afeiDb.dropTableIfTableExist(HrVOBean.class);
		// afeiDb.save(list.get(0));
		for (int i = 0; i < list.size(); i++) {
			afeiDb.save(list.get(i));
		}
		List<HrVOBean> listtemp = new ArrayList<HrVOBean>();
		listtemp = afeiDb.findAll(HrVOBean.class);
		String temp = "";
		if (listtemp.size() > 0) {
			for (int i = 0; i < listtemp.size(); i++) {
				temp += listtemp.get(i).toString() + "\n\n\n";
			}
		}
		listtemp = afeiDb.findAllByWhereStr(HrVOBean.class, " sub_id = '2'");
		temp += "查询sub id =2的数据" + listtemp.get(0).toString();
		tv.setText(temp);
	}

	private void queryCouponListByOrderId(boolean dialog) {

		// 结果解析parser
		// GetQueryListParser parser = new GetQueryListParser();
		// 请求parser
		Request<List<HrVOBean>> req = new Request<List<HrVOBean>>();
		req.setRequestMethod(Request.M_GET);
		req.setBaseParser(new BaseParser<List<HrVOBean>>() {

			@Override
			public List<HrVOBean> parseResDate(String resBody)
					throws DataException {
				if (resBody != null && !resBody.equals("")) {
					Preference.putString("hrdate", resBody);
					return JSON.parseArray(resBody, HrVOBean.class);
				}
				return null;
			}
		});
		HashMap<String, String> params = new HashMap<String, String>();

		// params.put("page", page);
		// params.put("size", size);
		req.setUrl("http://10.202.1.39:8080/hr/ListServlet");
		req.setRequestParams(params);

		Action action = new Action(getActivity());
		if (!dialog) {
			action.setDefaultLoadingTipOpen(false);
		}
		action.execute(req, new OnResponseListener<List<HrVOBean>>() {
			@Override
			public void onResponseFinished(Request<List<HrVOBean>> request,
					Response<List<HrVOBean>> response) {
				List<HrVOBean> list = new ArrayList<HrVOBean>();
				list = response.getT();
				ToastUtil.showLong(getActivity(), "list size is :"
						+ list.get(0).toString());
				L.e("yinzl", "获取到的第一个数据为：" + list.get(0).toString());
				initDate(list);
			}

			@Override
			public void onResponseDataError(Request<List<HrVOBean>> equest) {
				// TODO Auto-generated method stub
				ToastUtil.showLong(getActivity(), "请求失败");
			}

			@Override
			public void onResponseConnectionError(
					Request<List<HrVOBean>> request, int statusCode) {
				ToastUtil.showLong(getActivity(), "请求失败");
			}

			@Override
			public void onResponseFzzError(Request<List<HrVOBean>> request,
					ErrorMsg errorMsg) {
				ToastUtil.showLong(getActivity(), "请求失败");
				// btnController(true);
				// if (page.equals(page0)) {
				// lv_voucher.setVisibility(View.GONE);
				// ll_no_data.setVisibility(View.VISIBLE);
				// }
			}

		});
	}

	public void refreshData(boolean showDialog) {
	}
}
