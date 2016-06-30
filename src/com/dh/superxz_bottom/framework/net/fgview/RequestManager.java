package com.dh.superxz_bottom.framework.net.fgview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.dh.superxz_bottom.VehicleApp;
import com.dh.superxz_bottom.framework.db.AfeiDb;
import com.dh.superxz_bottom.framework.net.exception.ConnectionException;
import com.dh.superxz_bottom.framework.net.exception.DataException;
import com.dh.superxz_bottom.framework.net.exception.RequestParamException;
import com.dh.superxz_bottom.framework.net.network.NetworkConnection;
import com.dh.superxz_bottom.framework.net.network.NetworkConnection.ConnectionResult;
import com.dh.superxz_bottom.framework.net.network.NetworkConnection.Method;

/**
 * 请求管理器，主要是
 * 1、执行请求
 * 2、缓存请求结果
 * @author afei
 *
 */
public class RequestManager {

//	private LruCache<Request, Response> reqResCache ;
	
	private static final int CACHE_SIZE  = 10; 
	
	public static final int HTTP_ERROR_RESCODEFILTE = 400; //默认对http请求的进行过滤
	
	private static RequestManager requestManager = new RequestManager();
	
	private RequestManager(){
//		reqResCache = new LruCache<Request, Response>(CACHE_SIZE);
	}
	
	public static RequestManager getInstance(){
		return requestManager;
	}
	
	/**
	 * 访问网络数据
	 * @param <T>
	 * @param request 请求
	 * @return 返回 Response 响应数据
	 * @throws RequestParamException 
	 * @throws ConnectionException 
	 * @throws DataException 
	 */
	public <T> Response<T> execute(Request<T> request,Context context) throws RequestParamException, ConnectionException, DataException{
		
		String contentType = null;
		switch (request.getRequestContenType()) {
		case Request.RCT_DEFAULT:
			contentType = NetworkConnection.CT_DEFALUT;
			break;
		case Request.RCT_FORMDATA:
			contentType = NetworkConnection.CT_MULTIPART;
			break;
		case Request.RCT_JSON:
			contentType = NetworkConnection.CT_JSON;
			break;
		case Request.RCT_XML:
			contentType = NetworkConnection.CT_XML;
			break;

		default:
			throw new RequestParamException(request.getRequestContenType()+" request content-type invalid"); //不支持这种请求体内容格式 
		}
		
		//TODO,根据request里，自己要不要 缓存来的。
		List<byte[]> fileByteDates = new ArrayList<byte[]>();
		List<String> fileMimeType = new ArrayList<String>();
		if(null != request.getSingleFileDateByte()){
			fileByteDates.add(request.getSingleFileDateByte());
			fileMimeType.add(request.getSingleFileFormat());
		}
		
		if(null != request.getMulFileDateBytes()){
			fileByteDates.addAll(request.getMulFileDateBytes());
			fileMimeType.addAll(request.getMulFileFormats());
		}
		
		NetworkConnection networkConnection = new NetworkConnection(context,request.getUrl());
		HashMap<String, String> requestParams = request.getRequestParams();
		if(null != requestParams && requestParams.size()>0){
			networkConnection.setParameters(requestParams);
		}
		Method method = null;
		switch (request.getRequestMethod()) {
		case Request.M_POST:
			method = Method.POST;
			networkConnection.setContentType(contentType, method);
			networkConnection.setFileByteDates(fileByteDates,fileMimeType, method);
			networkConnection.setPostText(request.getBodyRequestParam(), method);
			break;
		case Request.M_PUT:
			method = Method.PUT;
			networkConnection.setContentType(contentType, method);
			networkConnection.setFileByteDates(fileByteDates,fileMimeType, method);
			networkConnection.setPostText(request.getBodyRequestParam(), method);
			break;
		case Request.M_GET:
			method = Method.GET;
			break;
		case Request.M_DELETE:
			method = Method.DELETE;
			break;

		default:
			throw new RequestParamException(request.getRequestMethod()+" request method invalid"); //不支持这种请求方式 
		}
		
		networkConnection.setHeaderList(request.getHeaderMap());
		networkConnection.setGzipEnabled(request.isGzipEnabled());
		networkConnection.setMethod(method);
		networkConnection.setHttpErrorResCodeFilte(HTTP_ERROR_RESCODEFILTE);
		networkConnection.setIsMulFiles(request.isMulFiles());
		Response<T> res = new Response<T>();
		try {
			ReqResBean reqResBean=null;
			if(request.isEnableCache()&&null!=(reqResBean=getResponseWithCache(request))){
				res.setBody(reqResBean.getResponse());
				res.setResponseCode(reqResBean.getResponseCode());
				
			}else{
				ConnectionResult execute =  networkConnection.execute();
				res.setBody(execute.body);
				res.setResponseCode(execute.responseCode);
				res.setHeaderMap(execute.headerMap);
				if(request.isEnableCache()){
					AfeiDb db =VehicleApp.getInstance().getAfeiDb();
					ReqResBean bean=new ReqResBean();
					bean.setTime(System.currentTimeMillis());
					bean.setRequestHashCode(request.hashCode());
					bean.setResponse(res.getBody());
					db.save(bean);   
				}
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
			int statusCode = e.getStatusCode();
			if(statusCode == NetworkConnection.EXCEPTION_CODE_FORMDATA_NOBYTEDATE){
				throw new RequestParamException("formdata required bytes[] dates !");
			}else{
				throw e;
			}
		}
		
		
		if(HTTP_ERROR_RESCODEFILTE == res.getResponseCode()){
			BaseParser<T> baseParser = request.getBaseParser();
			if(null != baseParser){
				res.setErrorMsg((Response.ErrorMsg) baseParser.parserErrorMsg(res.getBody()));
			}else{ //解析错误的  提供个默认实现
				BaseParser ba = new BaseParser() {
					@Override
					public Object parseResDate(String resBody)
							throws DataException {
						return null;
					}
				};
				res.setErrorMsg(ba.parserErrorMsg(res.getBody()));
			}
		}else{
			BaseParser<T> baseParser = request.getBaseParser();
			if(null != baseParser){
				T resDate = baseParser.parseResDate(res.getBody());
				res.setT(resDate);
			}
		}
//		if(request.isEnableCache()){
//			reqResCache.put(request, res);
//		}
		return res;
	}
	/**
	 * 从缓存中，取出响应数据
	 * 必须要保证request ，是可缓存的isEnableCache是为true的。
	 * @param request
	 * @return
	 */
	public <T> ReqResBean getResponseWithCache(Request<T> request){
		AfeiDb db =VehicleApp.getInstance().getAfeiDb();
		db.deleteByWhereStr(ReqResBean.class, " time - "+System.currentTimeMillis()+" > 60000");
		List<ReqResBean> list=db.findAllByWhereStr(ReqResBean.class, " requestHashCode="+request.hashCode()+"");
		if(null!=list&&list.size()>0){
			return list.get(0);
			//return null;
		}else{
			return null;
		}
	}
			
}
