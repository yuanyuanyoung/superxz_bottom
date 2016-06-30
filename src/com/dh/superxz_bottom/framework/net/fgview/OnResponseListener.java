package com.dh.superxz_bottom.framework.net.fgview;

import com.dh.superxz_bottom.framework.net.fgview.Response.ErrorMsg;

/**
 * 响应结果 监听器...
 * @author afei
 *
 */
public interface OnResponseListener<T>{
	
	//自定义的code从9000开始,没有网络，网络不正常
	public static final int CODE_NO_NET = 9000;
	
	//请求 方式及入参 等不正确
	public static final int CODE_REQUEST_PARAMS_ERROR = 9001;
	
	//CONNECTION类的，正常的HTTP异常，比如404，415等等
	public static final int CODE_CONN_HTTP_EXCEPTION = 9002;
	
	//CONNECTION类的，其它异常,比如算法异常，等等。
	public static final int CODE_CONN_OTHER_EXCEPTION = 9003;
	
	//平台响应超时异常，有可能用户手机网络连接不上服务端主机，或服务端主机崩溃了，暂定请求15秒后无响应则认为是超时无响应了。
	public static final int CODE_RESPONSE_OVER_TIME = 9008;
	
	
	
	//对平台响应的数据 解析异常，有可能是平台返回的格式不对，或解析时没按规范解析
	public static final int CODE_RES_PARSER_DATA_EXCEPTION = 9004;
	
	//响应成功
	public static final int CODE_SUCESS = 9999; 
	/**
	 * 正常OK的返回数据结果
	 * @param request
	 * @param response
	 */
	void onResponseFinished(Request<T> request,Response<T> response);
	
	/**
	 * 数据异常，比如出现在对平台数据的解析上，...
	 * @param equest
	 */
	void onResponseDataError(Request<T> equest);
	/**
	 * statusCode 响应状态码   500  415等等连接上的错误，若自定义的一些异常
	 * @param request
	 * @param statusCode
	 */
	void onResponseConnectionError(Request<T> request, int statusCode);
	
	/**
	 * 400 错误的，平台返回的code与message，比如查询商品列表 失败会有
	 * 400	{code=3010402, message='查询商品列表失败'}
	 * 又比如重置登录密码接口
	 * 400 {code : 1010102, message = '登录密码重置失败'}
	 * 400 {code : 1010103, message = '当前系统中，手机号不存在'}
	 * @param request
	 * @param errorMsg
	 */
	void onResponseFzzError(Request<T> request,ErrorMsg  errorMsg);
}