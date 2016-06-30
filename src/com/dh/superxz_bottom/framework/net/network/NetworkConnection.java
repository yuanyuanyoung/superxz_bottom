/**
 * 2012 Foxykeep (http://datadroid.foxykeep.com)
 * <p>
 * Licensed under the Beerware License : <br />
 * As long as you retain this notice you can do whatever you want with this stuff. If we meet some
 * day, and you think this stuff is worth it, you can buy me a beer in return
 */

package com.dh.superxz_bottom.framework.net.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.dh.superxz_bottom.framework.log.L;
import com.dh.superxz_bottom.framework.net.exception.ConnectionException;

/**
 * This class gives the user an API to easily call a webservice and return the received response.
 *
 * @author Foxykeep
 */
public final class NetworkConnection {

    private static final String LOG_TAG = NetworkConnection.class.getSimpleName();

    public static enum Method {
        GET, POST, PUT, DELETE
    }

    /**
     * The result of a webservice call.
     * <p>
     * Contains the headers and the body of the response as an unparsed <code>String</code>.
     *
     * @author Foxykeep
     */
    public static final class ConnectionResult {

        public final Map<String, List<String>> headerMap;
        public final String body;
        public final int responseCode; //add 增加响应代码

        public ConnectionResult(Map<String, List<String>> headerMap, String body,int responseCode) {
            this.headerMap = headerMap;
            this.body = body;
            this.responseCode = responseCode;
        }
    }

    private final Context mContext;
    private final String mUrl;
    private Method mMethod = Method.GET;
    private ArrayList<BasicNameValuePair> mParameterList = null;
    private HashMap<String, String> mHeaderMap = null;
    private boolean mIsGzipEnabled = true;
    private String mUserAgent = null;
    private String mPostText = null;
    private UsernamePasswordCredentials mCredentials = null;
    private boolean mIsSslValidationEnabled = true;
    private int httpErrorResCodeFilte ;  //http错误响应的代码,比如平台响应的400，有code与message
    
    private String mContentType = "application/x-www-form-urlencoded"; //增加ContentType的支持，默认是文本字符内容
    
    private List<byte[]> mFileByteDates; //文件数据
    private List<String> mFileMimeTypes; //文件类型.
	private boolean isMulFiles = false; //
    
    //各种类型的ContentType -- add
    public static final String CT_DEFALUT = "application/x-www-form-urlencoded"; //
    public static final String CT_MULTIPART = "multipart/form-data";
    public static final String CT_JSON = "application/json";
    public static final String CT_XML = "application/xml"; //用不上，放在也许以后项目要求  xml格式的入参

    
    //异常code，当为文件上传操作时，byte[]流，中无数据报异常
  	public static final int EXCEPTION_CODE_FORMDATA_NOBYTEDATE = 99999;
  	
  	
  	
    /**
     * Create a {@link NetworkConnection}.
     * <p>
     * The Method to use is {@link Method#GET} by default.
     *
     * @param context The context used by the {@link NetworkConnection}. Used to create the
     *            User-Agent.
     * @param url The URL to call.
     */
    public NetworkConnection(Context context, String url) {
        if (url == null) {
            L.e(LOG_TAG, "NetworkConnection.NetworkConnection - request URL cannot be null.");
            throw new NullPointerException("Request URL has not been set.");
        }
        mContext = context;
        mUrl = url;
    }

    /**
     * Set the method to use. Default is {@link Method#GET}.
     * <p>
     * If set to another value than {@link Method#POST}, the POSTDATA text will be reset as it can
     * only be used with a POST request.
     *
     * @param method The method to use.
     * @return The networkConnection.
     */
    public NetworkConnection setMethod(Method method) {
        mMethod = method;
        if (method != Method.POST && method != Method.PUT) { //修改 支持put方式 
            mPostText = null;
        }
        return this;
    }

    /**
     * 设置http请求错误码过滤，默认400过滤掉.
     * @param httpErrorResCodeFilte
     * @return
     */
    public NetworkConnection setHttpErrorResCodeFilte(int httpErrorResCodeFilte){
    	this.httpErrorResCodeFilte = httpErrorResCodeFilte;
    	return this;
    }
    
    public void setIsMulFiles(boolean isMulFiles){
    	this.isMulFiles = isMulFiles;
    }
    
    /**
     * 设置POST与PUT方式的 请求数据内容类型，
     * 支持有：
     * application/x-www-form-urlencoded
     * multipart/form-data --支持文件的上传
     * application/json
     * application/xml
     * 等等类型
     * 
     * @param contentType
     * @param method
     * @return
     */
    public NetworkConnection  setContentType(String contentType,Method method){
    	if (method != Method.POST && method != Method.PUT) {
            throw new IllegalArgumentException("Method must be POST or PUT");
        }
    	this.mContentType = contentType;
    	this.mMethod = method;
    	return this;
    }
    
    
    public NetworkConnection  setFileByteDates(List<byte[]> fileByteDates,List<String> fileMimeTypes,Method method){
    	if (method != Method.POST && method != Method.PUT) {
            throw new IllegalArgumentException("Method must be POST or PUT");
        }
    	this.mFileByteDates = fileByteDates;
    	this.mFileMimeTypes = fileMimeTypes;
    	this.mMethod = method;
    	return this;
    }
    
    
    /**
     * Set the parameters to add to the request. This is meant to be a "key" => "value" Map.
     * <p>
     * The POSTDATA text will be reset as they cannot be used at the same time.
     *
     * @see #setPostText(String)
     * @see #setParameters(ArrayList)
     * @param parameterMap The parameters to add to the request.
     * @return The networkConnection.
     */
    public NetworkConnection setParameters(HashMap<String, String> parameterMap) {
        ArrayList<BasicNameValuePair> parameterList = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
          parameterList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        return setParameters(parameterList);
    }


    /**
     * Set the parameters to add to the request. This is meant to be a "key" => "value" Map.
     * <p>
     * The POSTDATA text will be reset as they cannot be used at the same time.
     * <p>
     * This method allows you to have multiple values for a single key in contrary to the HashMap
     * version of the method ({@link #setParameters(HashMap)})
     *
     * @see #setPostText(String)
     * @see #setParameters(HashMap)
     * @param parameterList The parameters to add to the request.
     * @return The networkConnection.
     */
    public NetworkConnection setParameters(ArrayList<BasicNameValuePair> parameterList) {
      mParameterList = parameterList;
      mPostText = null;
      return this;
    }
    
    /**
     * Set the headers to add to the request.
     *
     * @param headerMap The headers to add to the request.
     * @return The networkConnection.
     */
    public NetworkConnection setHeaderList(HashMap<String, String> headerMap) {
        mHeaderMap = headerMap;
        return this;
    }

    /**
     * Set whether the request will use gzip compression if available on the server. Default is
     * true.
     *
     * @param isGzipEnabled Whether the request will user gzip compression if available on the
     *            server.
     * @return The networkConnection.
     */
    public NetworkConnection setGzipEnabled(boolean isGzipEnabled) {
        mIsGzipEnabled = isGzipEnabled;
        return this;
    }

    /**
     * Set the user agent to set in the request. Otherwise a default Android one will be used.
     *
     * @param userAgent The user agent.
     * @return The networkConnection.
     */
    public NetworkConnection setUserAgent(String userAgent) {
        mUserAgent = userAgent;
        return this;
    }

    /**
     * Set the POSTDATA text that will be added in the request. Also automatically set the
     * {@link Method} to {@link Method#POST} to be able to use it.
     * <p>
     * The parameters will be reset as they cannot be used at the same time.
     *
     * @param postText The POSTDATA text that will be added in the request.
     * @return The networkConnection.
     * @see #setParameters(HashMap)
     * @see #setPostText(String)
     */
    public NetworkConnection setPostText(String postText) {
        return setPostText(postText, Method.POST);
    }

    /**
     * Set the POSTDATA text that will be added in the request and also set the {@link Method}
     * to use. The Method can only be {@link Method#POST} or {@link Method#PUT}.
     * <p>
     * The parameters will be reset as they cannot be used at the same time.
     *
     * @param postText The POSTDATA text that will be added in the request.
     * @param method The method to use.
     * @return The networkConnection.
     * @see #setParameters(HashMap)
     * @see #setPostText(String)
     */
    public NetworkConnection setPostText(String postText, Method method) {
        if (method != Method.POST && method != Method.PUT) {
            throw new IllegalArgumentException("Method must be POST or PUT");
        }
        mPostText = postText;
        mMethod = method;
        //支持POST与PUT，当入参为 query与body的结合方式 
        //mParameterList = null;
        return this;
    }

    /**
     * Set the credentials to use for authentication.
     *
     * @param credentials The credentials to use for authentication.
     * @return The networkConnection.
     */
    public NetworkConnection setCredentials(UsernamePasswordCredentials credentials) {
        mCredentials = credentials;
        return this;
    }

    /**
     * Set whether the SSL certificates validation are enabled. Default is true.
     *
     * @param enabled Whether the SSL certificates validation are enabled.
     * @return The networkConnection.
     */
    public NetworkConnection setSslValidationEnabled(boolean enabled) {
        mIsSslValidationEnabled = enabled;        
        return this;
    }

    /**
     * Execute the webservice call and return the {@link ConnectionResult}.
     *
     * @return The result of the webservice call.
     */
    public ConnectionResult execute() throws ConnectionException {
    	//Log.e("newtewewerew", "ConnectionResult------------------mPostText::ConnectionResult1111111------:"+mPostText);
        return NetworkConnectionImpl.execute(mContext, mUrl, mMethod, mParameterList,
                mHeaderMap, mIsGzipEnabled, mUserAgent, mPostText, mCredentials,
                mIsSslValidationEnabled,mContentType,mFileByteDates,mFileMimeTypes,httpErrorResCodeFilte,isMulFiles);
    }
}
