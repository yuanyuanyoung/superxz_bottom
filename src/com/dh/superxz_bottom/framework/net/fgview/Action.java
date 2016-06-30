package com.dh.superxz_bottom.framework.net.fgview;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;

import com.dh.superxz_bottom.R;
import com.dh.superxz_bottom.VehicleApp;
import com.dh.superxz_bottom.framework.log.L;
import com.dh.superxz_bottom.framework.net.exception.ConnectionException;
import com.dh.superxz_bottom.framework.net.exception.DataException;
import com.dh.superxz_bottom.framework.net.exception.RequestParamException;
import com.dh.superxz_bottom.framework.net.fgview.Response.ErrorMsg;
import com.dh.superxz_bottom.framework.util.CommonUtils;
import com.dh.superxz_bottom.framework.util.ThreadPoolManager;
import com.dh.superxz_bottom.xutils.sample.utils.PubUtils;

/**
 * Activity页面中，用的网络访问类， use Action action = new Action(XXActivity.this);
 * <p/>
 * //若需要在网络访问前与网络方法后，处理些动作，比如弹出 进度对话框
 * 正在加载中...，请稍候...(默认设置一个）可以设置OnDataGainBeforeAfterListener监听器
 * //action.setOnDataGainBeforeAfterListener(xxxListener.) //一般情况下用不上
 * action.execute(request,listener);
 * 
 * @author afei
 */
@SuppressLint("HandlerLeak")
public class Action {

	public static final String RESP_CODE = "RESP_CODE"; // 平台统一的响应代码

	public static final long RESP_OVER_TIME = 30000;

	private OnDataGainBeforeAfterListener onDataGainBeforeAfterListener;

	private Context context;
	private RequestManager requestManager;

	private ThreadPoolManager threadPoolManager;

	// 默认的http请求开始的 提示对话框打开(正在加载中...)及请求结束后的 对话框 关闭，是否用起来，默认true代表用起来
	private boolean isDefaultLoadingTipOpen = true;

	private Map<String, Runnable> runnableMaps;

	private Handler overTimeHandler;

	private boolean isShowErrorDialog = true;
	private DialogInterface.OnDismissListener dialogDismissListener;
	// resources用于获取res资源中的字符串或图片
	private Resources resources;

	// 响应处理
	private class ResponseHandler<T> extends Handler {
		private OnResponseListener<T> listener;
		private Request<T> request;
		private String threadId;

		public ResponseHandler(Request<T> request,
				OnResponseListener<T> listener, String threadId,
				DialogInterface.OnDismissListener dialogDismissListener) {
			this.request = request;
			this.listener = listener;
			this.threadId = threadId;
			Action.this.dialogDismissListener = dialogDismissListener;
		}

		public ResponseHandler(Request<T> request,
				OnResponseListener<T> listener, String threadId) {
			this.request = request;
			this.listener = listener;
			this.threadId = threadId;
		}

		@Override
		public void handleMessage(Message msg) {
			if (context instanceof Activity) { // 判断网络请求未请求完，activity被结束了
				Activity activity = (Activity) context;
				if (activity.isFinishing()) {
					return;
				}
			}

			if (onDataGainBeforeAfterListener != null) {
				onDataGainBeforeAfterListener.onAfterGainAction(); // 访问完成
			}
			// 取消 超时请求的线程执行
			Runnable runnable = runnableMaps.get(threadId);
			overTimeHandler.removeCallbacks(runnable);

			switch (msg.what) {
			case OnResponseListener.CODE_SUCESS: // 返回正常的情况 包括400的手机号不存在等
				Response<T> response = (Response<T>) msg.obj;
				if (RequestManager.HTTP_ERROR_RESCODEFILTE == response
						.getResponseCode()) {
					ErrorMsg errorMsg = (ErrorMsg) response.getErrorMsg();
					if (null == errorMsg) {
						return;
					}

					if (isShowErrorDialog) {
						PubUtils.showTipDialog(context, errorMsg.getMessage(),
								dialogDismissListener);
					}

					if (null != listener) {
						listener.onResponseFzzError(request, errorMsg);
					}
				} else {
					if (null != listener) {
						listener.onResponseFinished(request, response);
					}
				}
				break;
			case OnResponseListener.CODE_NO_NET: // 暂时连接不上网络，网络没开
				if (isShowErrorDialog) {
					PubUtils.showTipDialog(context,
							resources.getString(R.string.frame_tip_no_net),
							dialogDismissListener);
				}
				if (null != listener) {
					listener.onResponseConnectionError(request,
							OnResponseListener.CODE_NO_NET);
				}
				break;
			case OnResponseListener.CODE_REQUEST_PARAMS_ERROR: // 入参不对，比如要求文件上传，结果没有byte[]流等等
																// ---
																// 这个错误，要配合平台将错误修正处理掉
				if (isShowErrorDialog) {
					PubUtils.showTipDialog(context, resources
							.getString(R.string.frame_tip_params_error));
				}
				if (null != listener) {
					listener.onResponseConnectionError(request,
							OnResponseListener.CODE_REQUEST_PARAMS_ERROR);
				}
				break;
			case OnResponseListener.CODE_CONN_HTTP_EXCEPTION: // 属于404 500等等错误
				if (isShowErrorDialog) {
					PubUtils.showTipDialog(context,
							resources.getString(R.string.frame_tip_conn_error),
							dialogDismissListener);
				}
				Integer statusCode = msg.arg1; // 响应状态码
				if (null != listener) {
					listener.onResponseConnectionError(request,
							OnResponseListener.CODE_CONN_HTTP_EXCEPTION);
				}
				break;
			case OnResponseListener.CODE_RES_PARSER_DATA_EXCEPTION: // 属于数据解析异常
				if (isShowErrorDialog) {
					PubUtils.showTipDialog(
							context,
							resources
									.getString(R.string.frame_tip_parser_date_exception),
							dialogDismissListener);
				}
				if (null != listener) {
					listener.onResponseDataError(request);
				}
				break;
			case OnResponseListener.CODE_CONN_OTHER_EXCEPTION: // 属于数据解析异常
				if (isShowErrorDialog) {
					PubUtils.showTipDialog(
							context,
							resources
									.getString(R.string.frame_tip_conn_error),
							dialogDismissListener);
				}
				if (null != listener) {
					listener.onResponseDataError(request);
				}
				break;
			case OnResponseListener.CODE_RESPONSE_OVER_TIME: // 平台响应超时异常，有可能用户手机网络连接不上服务端主机，或服务端主机崩溃了，暂定请求30秒后无响应则认为是超时无响应了。
				if (isShowErrorDialog) {
					PubUtils.showTipDialog(
							context,
							resources
									.getString(R.string.frame_tip_response_overtime_exception),
							dialogDismissListener);
				}
				if (null != listener) {
					listener.onResponseConnectionError(request,
							OnResponseListener.CODE_RESPONSE_OVER_TIME);
				}
				break;
			default:
				break;
			}
		}
	}

	// 请求处理
	private class RequestTask<T> implements Runnable {

		private ResponseHandler<T> hander;
		private Request<T> request;

		public RequestTask(Request<T> request, ResponseHandler<T> hander) {
			this.request = request;
			this.hander = hander;
		}

		@Override
		public void run() {
			Message obtainMessage = hander.obtainMessage();
			obtainMessage = obtainResponseMsg(obtainMessage);
			hander.sendMessage(obtainMessage);
		}

		private Message obtainResponseMsg(Message obtainMessage) {
			Response<T> response;
			int whatCode;
			if (null == context) {
				context = VehicleApp.getInstance();
			}
			if (CommonUtils.hasNetwork(context)) {
				try {
					response = requestManager.execute(request, context);
					whatCode = OnResponseListener.CODE_SUCESS;
					obtainMessage.obj = response;
				} catch (RequestParamException e) {
					e.printStackTrace();
					whatCode = OnResponseListener.CODE_REQUEST_PARAMS_ERROR;
				} catch (ConnectionException e) {
					e.printStackTrace();
					if (e.getStatusCode() != -1) { // HTTP类的响应代码,404,415等等
						whatCode = OnResponseListener.CODE_CONN_HTTP_EXCEPTION;
						obtainMessage.arg1 = e.getStatusCode();
						L.e("responsecode:" + e.getStatusCode()
								+ "  and message:" + e.getMessage());
					} else {
						whatCode = OnResponseListener.CODE_CONN_OTHER_EXCEPTION;
					}
				} catch (DataException e) {
					e.printStackTrace();
					whatCode = OnResponseListener.CODE_RES_PARSER_DATA_EXCEPTION;
				}
			} else {
				whatCode = OnResponseListener.CODE_NO_NET;
			}
			obtainMessage.what = whatCode;
			return obtainMessage;
		}

	}

	public boolean isDefaultLoadingTipOpen() {
		return isDefaultLoadingTipOpen;
	}

	public boolean isShowErrorDialog() {
		return isShowErrorDialog;
	}

	public void setShowErrorDialog(boolean isShowErrorDialog) {
		this.isShowErrorDialog = isShowErrorDialog;
	}

	public void setDefaultLoadingTipOpen(boolean isDefaultLoadingTipOpen) {
		this.isDefaultLoadingTipOpen = isDefaultLoadingTipOpen;
		if (!isDefaultLoadingTipOpen) {
			onDataGainBeforeAfterListener = null;
		}
	}

	public Action(Context context) {
		this.context = context;
		resources = context.getResources();
		requestManager = RequestManager.getInstance();
		threadPoolManager = ThreadPoolManager.getInstance();
		onDataGainBeforeAfterListener = new DefaultOnDataGainBeforeAfterListener();

		runnableMaps = new HashMap<String, Runnable>();
		overTimeHandler = new Handler();
	}

	/**
	 * 向服务器获取数据之前的动作，与向服务器获取数据之后的动作，监听器。
	 */
	public interface OnDataGainBeforeAfterListener {
		/**
		 * 获取数据之前进行的操作
		 */
		void onBeforeGainAction(String msg);

		/**
		 * 获取数据之后进行的操作
		 */
		void onAfterGainAction();
	}

	/**
	 * 设置 请求获取数据之前及获取数据之后的操作
	 * 
	 * @param onDataGainBeforeAfterListener
	 */
	public void setOnDataGainBeforeAfterListener(
			OnDataGainBeforeAfterListener onDataGainBeforeAfterListener) {
		this.onDataGainBeforeAfterListener = onDataGainBeforeAfterListener;
	}

	/**
	 * 核心方法，Activity中访问网络时，需要调用此方法。来完成http请求的响应
	 * 
	 * @param request
	 * @param listener
	 */
	public <T> void execute(Request<T> request, OnResponseListener<T> listener,
			String msg) {
		if (onDataGainBeforeAfterListener != null) {
			onDataGainBeforeAfterListener.onBeforeGainAction(msg);
		}
		String threadId = PubUtils.getUUId();

		ResponseHandler<T> hander = new ResponseHandler<T>(request, listener,
				threadId, dialogDismissListener);
		RequestTask<T> task = new RequestTask<T>(request, hander);

		threadPoolManager.executeTask(task);

		OverTimeRunnable overTimeRunnable = new OverTimeRunnable(hander);
		runnableMaps.put(threadId, overTimeRunnable);
		overTimeHandler.postDelayed(overTimeRunnable, RESP_OVER_TIME);

	}

	public void setDialogDismissListener(
			DialogInterface.OnDismissListener dialogDismissListener) {
		this.dialogDismissListener = dialogDismissListener;
	}

	// 超时处理,暂时15秒未返回，中止执行
	private class OverTimeRunnable<T> implements Runnable {

		private Handler resHandler;

		public OverTimeRunnable(Handler resHandler) {
			this.resHandler = resHandler;
		}

		@Override
		public void run() {
			Message obtainMessage = resHandler.obtainMessage();
			obtainMessage.what = OnResponseListener.CODE_RESPONSE_OVER_TIME;
			resHandler.sendMessage(obtainMessage);
		}

	}

	// * @deprecated 建议使用 execute(RequestInputer ri,OnResponseListener<T>
	// listener)

	/**
	 * 核心方法，Activity中访问网络时，需要调用此方法。来完成http请求的响应 默认提示 获取数据中,请稍候...
	 * 
	 * @param request
	 * @param listener
	 */
	public <T> void execute(Request<T> request, OnResponseListener<T> listener) {
		execute(request, listener,
				resources.getString(R.string.frame_tip_loading_date));
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param ri
	 * @param listener
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public <T> void execute(RequestInputer ri, OnResponseListener<T> listener) {
		Request<T> request = ri.getRequest();
		String msg = resources.getString(R.string.frame_tip_loading_date);
		// 若RequestInputer中被重写了isShowLoadingDialog为false则自定义网络访问前与访问后方法将会被执行
		if (!ri.isShowLoadingDialog()) {
			setOnDataGainBeforeAfterListener(ri);
		}
		// 设置是否显示，网络失败时的，提示信息
		setShowErrorDialog(ri.isErrorDialog());

		if (onDataGainBeforeAfterListener != null) {
			onDataGainBeforeAfterListener.onBeforeGainAction(msg);
		}

		String threadId = PubUtils.getUUId();

		ResponseHandler<T> hander = new ResponseHandler<T>(request, listener,
				threadId, dialogDismissListener);
		RequestTask<T> task = new RequestTask<T>(request, hander);
		threadPoolManager.executeTask(task);

		OverTimeRunnable overTimeRunnable = new OverTimeRunnable(hander);
		runnableMaps.put(threadId, overTimeRunnable);
		overTimeHandler.postDelayed(overTimeRunnable, RESP_OVER_TIME);
	}

	// 默认请求处理前与处理后的 监听器实现
	class DefaultOnDataGainBeforeAfterListener implements
			OnDataGainBeforeAfterListener {

		private ProgressDialog progressDialog;

		@Override
		public void onBeforeGainAction(String msg) {
			if (context instanceof Activity) {
				Activity activity = (Activity) context;
				if (activity.isFinishing()) {
					return;
				}
			}

			progressDialog = ProgressDialog.show(context, "", msg, true, true,
					new DialogInterface.OnCancelListener() {

						@Override
						public void onCancel(DialogInterface dialog) {
							if (progressDialog != null
									&& progressDialog.isShowing()) {
								progressDialog.dismiss();
							}
						}
					});
		}

		@Override
		public void onAfterGainAction() {
			if (context != null && progressDialog != null
					&& progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		}

	}
}
