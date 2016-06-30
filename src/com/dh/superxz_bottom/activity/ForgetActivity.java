package com.dh.superxz_bottom.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.dhutils.CommUtil;
import com.dh.superxz_bottom.xutils.sample.utils.ToastUtil;
import com.dh.superxz_bottom.xutils.view.ViewUtils;
import com.dh.superxz_bottom.xutils.view.annotation.ViewInject;
import com.dh.superxz_bottom.xutils.view.annotation.event.OnClick;
import com.dh.superxz_bottom.yinzldemo.VehicleActivity;

public class ForgetActivity extends VehicleActivity {
	@ViewInject(R.id.et_phone)
	private EditText et_phone;
	@ViewInject(R.id.et_code)
	private EditText et_code;
	@ViewInject(R.id.et_pass)
	private EditText et_pass;
	@ViewInject(R.id.et_confrim)
	private EditText et_confrim;
	@ViewInject(R.id.btn_register)
	private Button btn_register;
	@ViewInject(R.id.btn_code)
	private Button btn_code;
	private TimeCount time;
	@ViewInject(R.id.register_clearname)
	private ImageView register_clearname;
	@ViewInject(R.id.register_clearpass)
	private ImageView register_clearpass;
	@ViewInject(R.id.register_clearconfrim)
	private ImageView register_clearconfrim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget);
		ViewUtils.inject(this);
		super.initTop();
		setTitle("找回密码");
		initdate();
	}

	public void initdate() {
		time = new TimeCount(60000, 1000);
		et_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					// clearname.setVisibility(View.VISIBLE);

					register_clearname.setVisibility(View.VISIBLE);

				} else {
					register_clearname.setVisibility(View.GONE);
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

		et_pass.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					// clearname.setVisibility(View.VISIBLE);

					register_clearpass.setVisibility(View.VISIBLE);

				} else {
					register_clearpass.setVisibility(View.GONE);
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

		et_confrim.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					// clearname.setVisibility(View.VISIBLE);

					register_clearconfrim.setVisibility(View.VISIBLE);

				} else {
					register_clearconfrim.setVisibility(View.GONE);
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

	}

	@OnClick(R.id.register_clearname)
	public void onclickclearname(View view) {
		et_phone.setText("");
	}

	@OnClick(R.id.register_clearpass)
	public void onclickclearpass(View view) {
		et_pass.setText("");
	}

	@OnClick(R.id.register_clearconfrim)
	public void onclickclearconfrim(View view) {
		et_confrim.setText("");
	}

	@OnClick(R.id.btn_code)
	public void onclickcode(View view) {
		if (TextUtils.isEmpty(et_phone.getText().toString())) {
			ToastUtil.showLong(ForgetActivity.this, "手机号码不能为空");
		} else if (CommUtil.getInstance().isPhoneNumber(
				et_phone.getText().toString()) == false) {
			ToastUtil.showLong(ForgetActivity.this, "手机号码不正确");
		} else {
			// querycode();
			ToastUtil.showLong(ForgetActivity.this, "发送验证码");
		}

	}

	@OnClick(R.id.btn_register)
	public void onclickretrieve(View view) {

		if (TextUtils.isEmpty(et_phone.getText().toString())) {
			ToastUtil.showLong(ForgetActivity.this, "手机号码不能为空");
		} else if (TextUtils.isEmpty(et_code.getText().toString())) {
			ToastUtil.showLong(ForgetActivity.this, "验证码不能为空");
		} else if (TextUtils.isEmpty(et_pass.getText().toString().trim())) {
			ToastUtil.showLong(ForgetActivity.this, "密码不能为空");
		} else if (TextUtils.isEmpty(et_confrim.getText().toString().trim())) {
			ToastUtil.showLong(ForgetActivity.this, "确认密码不能为空");
		} else if (CommUtil.getInstance().isPhoneNumber(
				et_phone.getText().toString()) == false) {
			ToastUtil.showLong(ForgetActivity.this, "手机号码不正确");
		} else if (6 != et_code.getText().toString().length()) {
			ToastUtil.showLong(ForgetActivity.this, "验证码不正确");
		} else if (et_pass.getText().toString().trim().length() < 6) {
			ToastUtil.showLong(ForgetActivity.this, "密码长度不能小于6位");
		} else if (!et_confrim.getText().toString()
				.equals(et_pass.getText().toString())) {
			ToastUtil.showLong(ForgetActivity.this, "密码与确认密码不一致");
		} else {
			ToastUtil.showLong(ForgetActivity.this, "修改成功");
			finish();
			// querysafe();
		}
	}

	//
	// public void querycode() {
	// ProgeressUtils.showLoadingDialogMy("正在发送，请稍候...", ForgetActivity.this);
	// // 请求parser
	// Request<SimpleBaseBean> req = new Request<SimpleBaseBean>();
	// req.setRequestMethod(Request.M_GET);
	// req.setBaseParser(new BaseParser<SimpleBaseBean>() {
	// @Override
	// public SimpleBaseBean parseResDate(String resBody)
	// throws DataException {
	// if (resBody != null && !resBody.equals("")) {
	// return JSON.parseObject(resBody, SimpleBaseBean.class);
	// }
	// return null;
	// }
	// });
	// // /app/vuser/isExistForLogin_name.do?login_name=15951909869
	// Log.e("注册数据", et_phone.getText().toString() + et_pass.getText() + "");
	// String str = "/app/vuser/sendMsg.do?login_name="
	// + URLEncoder.encode(et_phone.getText().toString())
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
	// action.execute(req, new OnResponseListener<SimpleBaseBean>() {
	// @Override
	// public void onResponseFinished(Request<SimpleBaseBean> request,
	// Response<SimpleBaseBean> response) {
	// if (null != response) {
	// SimpleBaseBean bean = new SimpleBaseBean();
	// bean = response.getT();
	// // ArrayList<HomeGoodCitizenBean> list = new
	// // ArrayList<HomeGoodCitizenBean>();
	// // list = response.getT().getRows();
	// L.e(bean.getMessage()+"");
	// // L.e("yinzl", "请求size is：" + list.size());
	// if (bean.getMessage().equals("success")) {
	// // ToastUtil.showLong(RetrieveActivity.this, "发送验证码成功");
	// // isregister=true;
	// time.start();// 开始计时
	// } else {
	// ToastUtil.showLong(ForgetActivity.this,
	// bean.getError());
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	//
	// }
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	//
	// @Override
	// public void onResponseDataError(Request<SimpleBaseBean> equest) {
	// // ToastUtil.showLong(RetrieveActivity.this, "请求失败");
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	//
	// @Override
	// public void onResponseConnectionError(
	// Request<SimpleBaseBean> request, int statusCode) {
	// // ToastUtil.showLong(RetrieveActivity.this, "请求失败");
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	//
	// @Override
	// public void onResponseFzzError(Request<SimpleBaseBean> request,
	// ErrorMsg errorMsg) {
	// // ToastUtil.showLong(RetrieveActivity.this, "请求失败");
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	// });
	//
	// }

	// private void querysafe() {
	// ProgeressUtils.showLoadingDialogMy("正在修改，请稍候...", ForgetActivity.this);
	// // 请求parser
	// Request<SimpleBaseBean> req = new Request<SimpleBaseBean>();
	// req.setRequestMethod(Request.M_GET);
	// req.setBaseParser(new BaseParser<SimpleBaseBean>() {
	// @Override
	// public SimpleBaseBean parseResDate(String resBody)
	// throws DataException {
	// if (resBody != null && !resBody.equals("")) {
	// return JSON.parseObject(resBody, SimpleBaseBean.class);
	// }
	// return null;
	// }
	// });
	// String str = "/app/vuser/updatePassword.do?login_name="
	// + URLEncoder.encode(et_phone.getText().toString())
	// + "&password="
	// + URLEncoder.encode(et_pass.getText().toString().trim())
	// + "&code=" + et_code.getText().toString() + "&timestamp="
	// + System.currentTimeMillis();
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
	// action.execute(req, new OnResponseListener<SimpleBaseBean>() {
	// @Override
	// public void onResponseFinished(Request<SimpleBaseBean> request,
	// Response<SimpleBaseBean> response) {
	// if (null != response) {
	// SimpleBaseBean bean = new SimpleBaseBean();
	// bean = response.getT();
	// // ArrayList<HomeGoodCitizenBean> list = new
	// // ArrayList<HomeGoodCitizenBean>();
	// // list = response.getT().getRows();
	// // L.e("yinzl", "请求size is：" + list.size());
	// if (bean.getMessage().equals("success")) {
	// ToastUtil.showLong(ForgetActivity.this, "修改成功");
	// finish();
	// // Preference.putString(Constant.PRE_USER_PASSWORD,
	// // et_new
	// // .getText().toString());
	//
	// } else {
	// ToastUtil.showLong(ForgetActivity.this,
	// bean.getError());
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	//
	// }
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	//
	// @Override
	// public void onResponseDataError(Request<SimpleBaseBean> equest) {
	// // ToastUtil.showLong(RetrieveActivity.this, "请求失败");
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	//
	// @Override
	// public void onResponseConnectionError(
	// Request<SimpleBaseBean> request, int statusCode) {
	// // ToastUtil.showLong(RetrieveActivity.this, "请求失败");
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	//
	// @Override
	// public void onResponseFzzError(Request<SimpleBaseBean> request,
	// ErrorMsg errorMsg) {
	// // ToastUtil.showLong(RetrieveActivity.this, "请求失败");
	// ProgeressUtils.closeLoadingDialogMy();
	// }
	// });
	// }

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			btn_code.setText("重新验证");
			btn_code.setClickable(true);
		}

		@SuppressLint("ResourceAsColor")
		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btn_code.setClickable(false);
			btn_code.setText(millisUntilFinished / 1000 + "秒");
			btn_code.setGravity(Gravity.CENTER);
			// btn_code.setBackgroundColor(R.color.white);
		}
	}

}
