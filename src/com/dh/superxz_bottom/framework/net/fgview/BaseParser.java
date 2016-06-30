package com.dh.superxz_bottom.framework.net.fgview;

import org.json.JSONException;
import org.json.JSONObject;

import com.dh.superxz_bottom.framework.net.exception.DataException;
import com.dh.superxz_bottom.framework.net.fgview.Response.ErrorMsg;
/**
 * 对请求响应体的 数据进行解析，响应体可以为json或xml或等等其它格式
 * @author afei
 */
public abstract class BaseParser<T> {
	
	/**
	 * @param resBody 将响应的字符串,按照json或xml进行解析
	 */
	 public abstract T parseResDate(String resBody) throws DataException;
	 
	 
	 
	 /**
	  * 返回响应的情况，是error时返回null，否则为相应的响应代号
	  * @param paramString 
	 * @throws DataException 
	  */
	 public String checkResponse(String paramString) throws DataException{
		if(paramString==null || "".equals(paramString.trim())){ 
			return null;
		}else{
			String result = null;
			try {
				JSONObject jsonObject = new JSONObject(paramString);
				result = jsonObject.getString(Action.RESP_CODE);
				if(result!=null && result.equals("00")){
					return result;
				}else{
					return null;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				throw new DataException("json parser exception", e);
			} 
		}
	 }
	 
	 /**
	  * 对400 错误的 进行解析，封装
	  * @param resBody
	  * @return
	  * @throws DataException
	  */
	 public ErrorMsg parserErrorMsg(String resBody) throws DataException{
		 if(null != resBody){
			 try {
				JSONObject json = new JSONObject(resBody);
				int code = json.optInt("code");
				String message = json.optString("message");
				ErrorMsg em = new Response().new ErrorMsg();
				em.setCode(code);
				em.setMessage(message);
				return em;
			} catch (JSONException e) {
				e.printStackTrace();
				throw new DataException(" body is not json ");
			}
		 }
		 return null;
	 }
}
