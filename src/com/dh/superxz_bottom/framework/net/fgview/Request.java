package com.dh.superxz_bottom.framework.net.fgview;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * http 请求封装
 * 
 * @author afei
 * 
 */
public class Request<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	// POST /shop/collect/{shopId}/{userId} url，不在框架层拼接了，因为接口给的多个参数 接的顺利不可确定，
	// 比如上面 有可能 要求先userId值，再shopId值，也有可能先userId值，再shopId值,path方式，需要各人在view
	// activity层中，自己拼
	// 而且，接口平台，可能部分入参是path，部分入参是query，更没判断拼接url，所以还是自己玩...
	private String url; // 请求的url地址 ， 若入参为path方式，则需要将参数拼在后面

	// KEY1=VALUE1&key2=value2
	private HashMap<String, String> requestParams;// 往后台传输时的请求参数
													// ---query与form形式的传参

	private String bodyRequestParam; // 请求体参数，在PUT与POST方法时，用到。乐驾1.0主要是json格式

	private HashMap<String, String> headerMap; // http请求头 信息
												// 比如cookie，content-type、content-length、referer等等

	private BaseParser<T> baseParser; // 获取到响应数据后，解析返回的数据

	private int requestMethod = M_GET;

	public static final int M_POST = 1; // POST方法
	public static final int M_DELETE = 2;
	public static final int M_PUT = 3;
	public static final int M_GET = 4; // 默认方法--

	// 请求数据类型，当requestMethod为POST与PUT时（主要是入参为body时），可以有如下多种中的一个比如
	// application/x-www-form-urlencoded、application/json、application/xml、multipart/form-data等等
	// 当requestMethod为GET与DELETE时，不需要指定requestContenType，入参只可能为path或query了。
	// 淡痛。。。post还搞那么多种...有还四种http方法.....
	private int requestContenType = RCT_DEFAULT;

	public static final int RCT_DEFAULT = 1; // 默认为application/x-www-form-urlencoded
	public static final int RCT_JSON = 2; //
	public static final int RCT_XML = 3;
	public static final int RCT_FORMDATA = 4; // multipart/form-data型 ，文件上传类型的

	// 文件上传时，需要这个
	private List<byte[]> mulFileDateBytes;
	private byte[] singleFileDateByte;

	private String singleFileMimeType;
	private List<String> mulFileMimeTypes;

	private boolean isMulFiles = false;// 是否使用多张图片

	private boolean isGzipEnabled = true; // 是否支持响应报文是经过gzip压缩的，默认支持

	// 代表请求是否接收缓存 ， 建议在POST上传文件时，不要用缓存，登录无法使用cache
	private boolean isEnableCache = false; // 默认是不按缓存来的，对于实时要求不高的，比如猜你喜欢之类请求，可以设置为true。

	public boolean isMulFiles() {
		return isMulFiles;
	}

	public void setMulFiles(boolean isMulFiles) {
		this.isMulFiles = isMulFiles;
	}

	public String getSingleFileFormat() {
		return singleFileMimeType;
	}

	public List<String> getMulFileFormats() {
		return mulFileMimeTypes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HashMap<String, String> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(HashMap<String, String> requestParams) {
		this.requestParams = requestParams;
	}

	public BaseParser<T> getBaseParser() {
		return baseParser;
	}

	public void setBaseParser(BaseParser<T> jsonParser) {
		this.baseParser = jsonParser;
	}

	public int getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(int requestMethod) {
		this.requestMethod = requestMethod;
	}

	public int getRequestContenType() {
		return requestContenType;
	}

	public void setRequestContenType(int requestContenType) {
		this.requestContenType = requestContenType;
	}

	public List<byte[]> getMulFileDateBytes() {
		return mulFileDateBytes;
	}

	public void setMulFileDateBytes(List<byte[]> mulFileDateBytes, List<String> mulFileMimeTypes) {
		this.mulFileDateBytes = mulFileDateBytes;
		this.mulFileMimeTypes = mulFileMimeTypes;
	}

	public byte[] getSingleFileDateByte() {
		return singleFileDateByte;
	}

	public void setSingleFileDateByte(byte[] singleFileDateByte, String singleFileMimeType) {
		this.singleFileDateByte = singleFileDateByte;
		this.singleFileMimeType = singleFileMimeType;
	}

	public boolean isEnableCache() {
		return isEnableCache;
	}

	public void setEnableCache(boolean isEnableCache) {
		this.isEnableCache = isEnableCache;
	}

	public String getBodyRequestParam() {
		return bodyRequestParam;
	}

	public void setBodyRequestParam(String bodyRequestParam) {
		this.bodyRequestParam = bodyRequestParam;
	}

	public HashMap<String, String> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(HashMap<String, String> headerMap) {
		this.headerMap = headerMap;
	}

	public boolean isGzipEnabled() {
		return isGzipEnabled;
	}

	public void setGzipEnabled(boolean isGzipEnabled) {
		this.isGzipEnabled = isGzipEnabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isEnableCache ? 1231 : 1237);
//		result = prime * result + ((baseParser == null) ? 0 : baseParser.hashCode());
		result = prime * result + ((mulFileDateBytes == null) ? 0 : mulFileDateBytes.hashCode());
		result = prime * result + requestContenType;
		result = prime * result + requestMethod;
		result = prime * result + ((requestParams == null) ? 0 : requestParams.hashCode());
		result = prime * result + Arrays.hashCode(singleFileDateByte);
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;

		if (isEnableCache != other.isEnableCache)
			return false;
		if (baseParser == null) {
			if (other.baseParser != null)
				return false;
		} else if (!baseParser.equals(other.baseParser))
			return false;
		if (mulFileDateBytes == null) {
			if (other.mulFileDateBytes != null)
				return false;
		} else if (!mulFileDateBytes.equals(other.mulFileDateBytes))
			return false;
		if (requestContenType != other.requestContenType)
			return false;
		if (requestMethod != other.requestMethod)
			return false;
		if (requestParams == null) {
			if (other.requestParams != null)
				return false;
		} else if (!requestParams.equals(other.requestParams))
			return false;
		if (!Arrays.equals(singleFileDateByte, other.singleFileDateByte))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "Request [url=" + url + ", requestParams=" + requestParams + ", bodyRequestParam=" + bodyRequestParam + ", headerMap=" + headerMap + ", jsonParser=" + baseParser + ", requestMethod=" + requestMethod + ", requestContenType=" + requestContenType + ", mulFileDateBytes=" + mulFileDateBytes + ", singleFileDateByte=" + Arrays.toString(singleFileDateByte) + ", isEnableCache=" + isEnableCache + "]";
	}

}
