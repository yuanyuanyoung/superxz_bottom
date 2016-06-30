package com.dh.superxz_bottom.framework.net.fgview;

import com.dh.superxz_bottom.framework.net.fgview.Response.ErrorMsg;

/**
 * 响应监听器 适配器类
 * 在不必要处理网络访问失败的逻辑，只需要处理正确获取到数据时使用。
 * @author afei
 *
 * @param <T>
 */
public abstract class OnResponseAdapter<T> implements OnResponseListener<T> {

    public static final String ERROR_TYPE_PARSER_DATA = "1"; //数据解析失败
    public static final String ERROR_TYPE_INNER_HANDLER = "2" ; //平台处理失败,404  500等等错误
    public static final String ERROR_TYPE_Fzz = "3" ; //400,用户输入条件不规范
	
	@Override
	public void onResponseDataError(Request<T> request) {
	    onResponseFailure(request,ERROR_TYPE_PARSER_DATA,null);
	}

	@Override
	public void onResponseConnectionError(Request<T> request, int statusCode) {
	    onResponseFailure(request,ERROR_TYPE_INNER_HANDLER,null);
	}

	@Override
	public void onResponseFzzError(Request<T> request, ErrorMsg errorMsg) {
	    onResponseFailure(request,ERROR_TYPE_Fzz,errorMsg);
	}
	
	/**
	 * 网络访问失败回调
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 简化网络访问失败的处理，若开发有需要对不同的失败原因处理，可以分别重写上面三个方法，也可以重写下面的方法
	 * @param request
	 * @param errorType 
	 *     1，数据解析失败  errorMsg为空
	 *     2, 404或500等平台错误 errorMsg为空
	 *     3, 用户输入条件不规范，errorMsg有具体提示信息
	 * @param errorMsg 
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public void onResponseFailure(Request<T> request,String errorType, ErrorMsg errorMsg) {
	    
	}

}
