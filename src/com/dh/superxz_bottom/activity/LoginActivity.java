package com.dh.superxz_bottom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.VehicleApp;
import com.dh.superxz_bottom.constant.Constant;
import com.dh.superxz_bottom.dhutils.CommUtil;
import com.dh.superxz_bottom.framework.db.AfeiDb;
import com.dh.superxz_bottom.xutils.sample.utils.ToastUtil;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;
import com.dh.superxz_bottom.xutils.view.annotation.event.OnClick;
import com.dh.superxz_bottom.yinzldemo.FragmentTabActivity2;
import com.dh.superxz_bottom.yinzldemo.VehicleActivity;
import com.dh.superxz_bottom.yinzldemo.VehicleNoSwipbackActivity;

public class LoginActivity extends VehicleNoSwipbackActivity {
	@ViewInject(R.id.et_name)
	private EditText et_name;
	@ViewInject(R.id.et_password)
	private EditText et_password;
	@ViewInject(R.id.login_bt)
	private Button bt;
	@ViewInject(R.id.tv_register)
	private TextView tv_register;
	@ViewInject(R.id.tv_forget)
	private TextView tv_forget;
	@ViewInject(R.id.login_clearname)
	private ImageView clearname;
	@ViewInject(R.id.login_clearpass)
	private ImageView clearpass;
	public AfeiDb afeiDb;

	private String loing_type = "";
	private Double longitude = 0.00;
	private Double latitude = 0.00;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		ViewUtils.inject(this);
		afeiDb = VehicleApp.getInstance().getAfeiDb();
		if (null == afeiDb) {
			afeiDb = AfeiDb.create(LoginActivity.this, Constant.DB_NAME, true);
		}
		// user = VehicleApp.getInstance().getUserBean();
		// if (null != VehicleApp.getInstance().getLocationBean()) {
		//
		// longitude = VehicleApp.getInstance().getLocationBean()
		// .getLongitude();
		// latitude = VehicleApp.getInstance().getLocationBean().getLatitude();
		// }

		initdata();
		// initUmeng();
		loing_type = getIntent().getStringExtra("login_type");
	}

	public void initdata() {
		et_name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					// clearname.setVisibility(View.VISIBLE);
					clearname.setVisibility(View.VISIBLE);
				} else {
					clearname.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		et_password.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					// clearname.setVisibility(View.VISIBLE);

					clearpass.setVisibility(View.VISIBLE);

				} else {
					clearpass.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});

		// if (!TextUtils.isEmpty(Preference.getString(Constant.PRE_USER_NAME)))
		// {
		// et_name.setText(Preference.getString(Constant.PRE_USER_NAME));
		// }

	}

	@OnClick(R.id.login_clearname)
	public void loginclearname(View view) {
		et_name.setText("");
	}

	@OnClick(R.id.login_clearpass)
	public void loginclearpassword(View view) {
		et_password.setText("");
	}

	@OnClick(R.id.tv_register)
	public void register(View view) {
		// if (Constant.isDoubleClicked()) {
		// return;
		// }
		launch(RegisterActivity.class);
	}

	@OnClick(R.id.tv_forget)
	public void forget(View view) {
		launch(ForgetActivity.class);
	}

	@OnClick(R.id.login_bt)
	public void btnlogin(View view) {
		// if(Constant.isDoubleClicked()){
		// return;
		// }
		if (TextUtils.isEmpty(et_name.getText().toString())) {
			// ToastUtil.showLong(getApplicationContext(), "手机号码不能为空");
			ToastUtil.showToastOnly(getApplicationContext(), "手机号码不能为空");
		} else if (TextUtils.isEmpty(et_password.getText().toString().trim())) {

			ToastUtil.showLong(getApplicationContext(), "密码不能为空");
		} else if (CommUtil.getInstance().isPhoneNumber(
				et_name.getText().toString()) == false) {
			ToastUtil.showLong(getApplicationContext(), "手机号码不正确");
		} else {
			// querylogin();
			ToastUtil.showLong(getApplicationContext(), "登录成功");
		}
	}

	//
	// public void querylogin() {
	//
	// ProgeressUtils.showLoadingDialogMy("正在登录，请稍候...", LoginActivity.this);
	// Request<LoginBean> req = new Request<LoginBean>();
	// req.setRequestMethod(Request.M_GET);
	// req.setBaseParser(new BaseParser<LoginBean>() {
	// @Override
	// public LoginBean parseResDate(String resBody) throws DataException {
	// if (resBody != null && !resBody.equals("")) {
	// return JSON.parseObject(resBody, LoginBean.class);
	// }
	// return null;
	// }
	// });
	// @SuppressWarnings("deprecation")
	// String str = "/app/vuser/loginChecked.do?login_name="
	// + URLEncoder.encode(et_name.getText().toString().trim())
	// + "&password="
	// + URLEncoder.encode(et_password.getText().toString().trim())
	// + "&longitude=" + longitude + "&latitude=" + latitude
	// + "&timestamp=" + System.currentTimeMillis();
	//
	// try {
	// // req.setUrl(Constant.URLIP + DES3.encode(str));
	// req.setUrl(Constant.URLIP + DES3.encode(str));
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// Action action = new Action(this);
	// action.setDefaultLoadingTipOpen(false);
	// action.setShowErrorDialog(true);
	// action.execute(req, new OnResponseListener<LoginBean>() {
	// @Override
	// public void onResponseFinished(Request<LoginBean> request,
	// Response<LoginBean> response) {
	// if (null != response) {
	// LoginBean bean = new LoginBean();
	// bean = response.getT();
	// // ArrayList<HomeGoodCitizenBean> list = new
	// // ArrayList<HomeGoodCitizenBean>();
	// // list = response.getT().getRows();
	// // L.e("yinzl", "请求size is：" + list.size());
	//
	// if (bean.getMessage().equals("success")) {
	// ToastUtil.showLong(LoginActivity.this, "登录成功");
	// mPushAgent.enable(mRegisterCallback);
	// new AddAliasTask(bean.getSysUser().getId(),
	// "goodcitizen").execute();
	// Preference.putString(Constant.PRE_USER_NAME, et_name
	// .getText().toString().trim());
	// Preference.putString(Constant.PRE_USER_PASSWORD,
	// et_password.getText().toString());
	// afeiDb.dropTableIfTableExist(UserBean.class);
	// afeiDb.save(bean.getSysUser());
	// VehicleApp.getInstance().setUserBean(bean.getSysUser());
	// List<UserBean> useList = new ArrayList<UserBean>();
	// useList = afeiDb.findAll(UserBean.class);
	// finish();
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// // 推送一个事件
	// PersonEvent appevent = new PersonEvent(
	// PersonEvent.MYSELF_PERSONAL);
	// EventBus.getDefault().post(appevent);
	//
	// }
	// }).start();
	//
	// } else {
	// ToastUtil.showLong(LoginActivity.this, bean.getError());
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	// // Log.e("测试数据", "成功"+bean.getMessage());
	// }
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	//
	// @Override
	// public void onResponseDataError(Request<LoginBean> equest) {
	// // ToastUtil.showLong(LoginActivity.this, "请求失败");
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	//
	// @Override
	// public void onResponseConnectionError(Request<LoginBean> request,
	// int statusCode) {
	// // ToastUtil.showLong(LoginActivity.this, "请求失败");
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	//
	// @Override
	// public void onResponseFzzError(Request<LoginBean> request,
	// ErrorMsg errorMsg) {
	// // ToastUtil.showLong(LoginActivity.this, "请求失败");
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	// });
	//
	// }
	//
	// private void initUmeng() {
	// mPushAgent = PushAgent.getInstance(this);
	// // mPushAgent.addAlias("52", ALIAS_TYPE.BAIDU);
	// }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (!TextUtils.isEmpty(loing_type) && loing_type.equals("exit")) {
				Intent mIntent = new Intent();
				mIntent.setClass(LoginActivity.this, FragmentTabActivity2.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				mIntent.putExtra("tab_type", FragmentTabActivity2.TAB_INDEX);
				launch(mIntent);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
