package com.dh.superxz_bottom.framework.net.fgview;

import java.util.List;
import java.util.Map;

/**
 * http 请求完的响应结果封装
 * @author afei
 *
 */
public class Response<T> {

	private int responseCode;  //http 请求响应码   值如：200，404，415、500等等
	
	private String body;  //http 请求响应体内容 ，，格式可以是json，也可以是xml 或html，等等内容。  --此乐驾1.0版本中，响应体是json
	
	private Map<String, List<String>> headerMap;  //http 请求响应头信息，比如响应格式 content-type、content-length、expires
	
	private T t; //对body进行按json及xml等方式进行的解析的结果，将body里的用BaseParser实例进行解析
	
	private ErrorMsg  errorMsg;
	
	//错误响应,主要是针对400返回的数据，比如{code = 3010107, message = '查询商家评价列表失败') }或手机号不存在什么的
	public class ErrorMsg{
		private int code;
		private String message;
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, List<String>> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, List<String>> headerMap) {
		this.headerMap = headerMap;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public ErrorMsg getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(ErrorMsg errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
