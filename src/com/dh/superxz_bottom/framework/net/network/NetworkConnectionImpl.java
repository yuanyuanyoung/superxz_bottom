/**
 * 2011 Foxykeep (http://datadroid.foxykeep.com)
 * <p>
 * Licensed under the Beerware License : <br />
 * As long as you retain this notice you can do whatever you want with this stuff. If we meet some
 * day, and you think this stuff is worth it, you can buy me a beer in return
 */
package com.dh.superxz_bottom.framework.net.network;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpStatus;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.text.TextUtils;
import android.text.InputFilter.LengthFilter;
import android.util.Base64;
import android.util.Log;

import com.dh.superxz_bottom.framework.log.L;
import com.dh.superxz_bottom.framework.net.exception.ConnectionException;
import com.dh.superxz_bottom.framework.net.network.NetworkConnection.ConnectionResult;
import com.dh.superxz_bottom.framework.net.network.NetworkConnection.Method;

/**
 * Implementation of the network connection.
 * 
 * @author Foxykeep
 */
public final class NetworkConnectionImpl {

	private static final String TAG = NetworkConnectionImpl.class.getSimpleName();

	private static final String ACCEPT_CHARSET_HEADER = "Accept-Charset";
	private static final String ACCEPT_ENCODING_HEADER = "Accept-Encoding";
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String LOCATION_HEADER = "Location";

	private static final String UTF8_CHARSET = "UTF-8";

	// 上传文件的支持，采用拼MIME协议上传
	private final static String LINEND = "\r\n";
	private final static String BOUNDARY = "---------------------------7da213758" + new Random().nextInt(1000); // 数据分隔线
	private final static String PREFIX = "--";

	// Default connection and socket timeout of 60 seconds. Tweak to taste.
	private static final int OPERATION_TIMEOUT = 60 * 1000;

	private NetworkConnectionImpl() {
		// No public constructor
	}

	/**
	 * 封装表单文本数据
	 * 
	 * @param paramText
	 * @return
	 */
	private static String bulidFormText(ArrayList<BasicNameValuePair> paramText) {
		if (paramText == null || paramText.isEmpty())
			return "";
		StringBuffer sb = new StringBuffer("");
		for (BasicNameValuePair bnvp : paramText) {
			sb.append(PREFIX).append(BOUNDARY).append(LINEND);
			sb.append("Content-Disposition:form-data;name=\"" + bnvp.getName() + "\"" + LINEND);
			sb.append(LINEND);
			sb.append(bnvp.getValue());
			sb.append(LINEND);
		}
		return sb.toString();
	}

	/**
	 * Call the webservice using the given parameters to construct the request
	 * and return the result.
	 * 
	 * @param context
	 *            The context to use for this operation. Used to generate the
	 *            user agent if needed.
	 * @param urlValue
	 *            The webservice URL.
	 * @param method
	 *            The request method to use.
	 * @param parameterList
	 *            The parameters to add to the request.
	 * @param headerMap
	 *            The headers to add to the request.
	 * @param isGzipEnabled
	 *            Whether the request will use gzip compression if available on
	 *            the server.
	 * @param userAgent
	 *            The user agent to set in the request. If null, a default
	 *            Android one will be created.
	 * @param postText
	 *            The POSTDATA text to add in the request.
	 * @param credentials
	 *            The credentials to use for authentication.
	 * @param isSslValidationEnabled
	 *            Whether the request will validate the SSL certificates.
	 * @return The result of the webservice call.
	 */
	public static ConnectionResult execute(Context context, String urlValue, Method method, ArrayList<BasicNameValuePair> parameterList, HashMap<String, String> headerMap, boolean isGzipEnabled, String userAgent, String postText, UsernamePasswordCredentials credentials, boolean isSslValidationEnabled, String contentType, List<byte[]> fileByteDates, List<String> fileMimeTypes, int httpErrorResCodeFilte,boolean isMulFiles) throws ConnectionException {
		HttpURLConnection connection = null;
		try {
			// Prepare the request information
			if (userAgent == null) {
				userAgent = UserAgentUtils.get(context);
			}
			if (headerMap == null) {
				headerMap = new HashMap<String, String>();
			}
			headerMap.put(HTTP.USER_AGENT, userAgent);
			if (isGzipEnabled) {
				headerMap.put(ACCEPT_ENCODING_HEADER, "gzip");
			}
			headerMap.put(ACCEPT_CHARSET_HEADER, UTF8_CHARSET);
			if (credentials != null) {
				headerMap.put(AUTHORIZATION_HEADER, createAuthenticationHeader(credentials));
			}

			StringBuilder paramBuilder = new StringBuilder();
			if (parameterList != null && !parameterList.isEmpty()) {
				for (int i = 0, size = parameterList.size(); i < size; i++) {
					BasicNameValuePair parameter = parameterList.get(i);
					String name = parameter.getName();
					String value = parameter.getValue();
					if (TextUtils.isEmpty(name)) {
						// Empty parameter name. Check the next one.
						continue;
					}
					if (value == null) {
						value = "";
					}
					paramBuilder.append(URLEncoder.encode(name, UTF8_CHARSET));
					paramBuilder.append("=");
					paramBuilder.append(URLEncoder.encode(value, UTF8_CHARSET));
					paramBuilder.append("&");
				}
			}

			// 创建内存输出流，先存入内容中
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			// Create the connection object
			URL url = null;
			String outputText = null;
			switch (method) {
			case GET:
			case DELETE:
				String fullUrlValue = urlValue;
				if (paramBuilder.length() > 0) {
					fullUrlValue += "?" + paramBuilder.toString();
				}
				url = new URL(fullUrlValue);
				connection = HttpUrlConnectionHelper.openUrlConnection(url);
				break;
			case PUT:
			case POST:
				url = new URL(urlValue);
				connection = HttpUrlConnectionHelper.openUrlConnection(url);
				connection.setDoOutput(true);

				if (paramBuilder.length() > 0 && NetworkConnection.CT_DEFALUT.equals(contentType)) { // form
																										// 形式
					// headerMap.put(HTTP.CONTENT_TYPE,
					// "application/x-www-form-urlencoded");
					outputText = paramBuilder.toString();
					headerMap.put(HTTP.CONTENT_TYPE, contentType);
					headerMap.put(HTTP.CONTENT_LEN, String.valueOf(outputText.getBytes().length));
				} else if (postText != null && (NetworkConnection.CT_JSON.equals(contentType) || NetworkConnection.CT_XML.equals(contentType))) { // body
																																					// 形式
					// headerMap.put(HTTP.CONTENT_TYPE, "application/json");
					// //add 只能为json，后面再改
					headerMap.put(HTTP.CONTENT_TYPE, contentType); // add
																	// 只能为json，后面再改
					headerMap.put(HTTP.CONTENT_LEN, String.valueOf(postText.getBytes().length));
					outputText = postText;
					// Log.e("newtewewerew",
					// "1111application/json------------------outputText222222:::"+outputText+"-------method::"+method+"----paramBuilder.length() :"+paramBuilder.length()+"----postText:"+postText
					// );
				} else if (NetworkConnection.CT_MULTIPART.equals(contentType)) { // 上传文件
					String[] Array = urlValue.split("/");
					/*Boolean isFiles = false;
					if (Array[Array.length - 1].equals("clientRemarkPic")) {
						isFiles = true;
					}*/
					if (null == fileByteDates || fileByteDates.size() <= 0) {
						throw new ConnectionException("file formdata no bytes data", NetworkConnection.EXCEPTION_CODE_FORMDATA_NOBYTEDATE);
					}
					headerMap.put(HTTP.CONTENT_TYPE, contentType + ";boundary=" + BOUNDARY);
					String bulidFormText = bulidFormText(parameterList);
					bos.write(bulidFormText.getBytes());
					for (int i = 0; i < fileByteDates.size(); i++) {
						long currentTimeMillis = System.currentTimeMillis();
						StringBuffer sb = new StringBuffer("");
						sb.append(PREFIX).append(BOUNDARY).append(LINEND);
						// sb.append("Content-Type:application/octet-stream" +
						// LINEND);
						// sb.append("Content-Disposition: form-data; name=\""+nextInt+"\"; filename=\""+nextInt+".png\"").append(LINEND);;
						if (isMulFiles)
							sb.append("Content-Disposition: form-data; name=\"files\";filename=\"pic" + currentTimeMillis + ".png\"").append(LINEND);
						else
							sb.append("Content-Disposition: form-data; name=\"file\";filename=\"pic" + currentTimeMillis + ".png\"").append(LINEND);
						// sb.append("Content-Type:image/png" + LINEND);
						sb.append("Content-Type:" + fileMimeTypes.get(i) + LINEND);
						// sb.append("Content-Type:image/png" + LINEND);
						sb.append(LINEND);
						bos.write(sb.toString().getBytes());
						bos.write(fileByteDates.get(i));
						bos.write(LINEND.getBytes());
					}
					byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
					bos.write(end_data);
					bos.flush();
					headerMap.put(HTTP.CONTENT_LEN, bos.size() + "");
				}
				// L.e("newtewewerew",
				// "2222------------------outputText222222:::"+outputText+"-------method::"+method+"----paramBuilder.length() :"+paramBuilder.length()+"----postText:"+postText
				// );
				break;
			}

			// Set the request method
			connection.setRequestMethod(method.toString());

			// If it's an HTTPS request and the SSL Validation is disabled
			if (url.getProtocol().equals("https") && !isSslValidationEnabled) {
				HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
				httpsConnection.setSSLSocketFactory(getAllHostsValidSocketFactory());
				httpsConnection.setHostnameVerifier(getAllHostsValidVerifier());
			}

			// Add the headers
			if (!headerMap.isEmpty()) {
				for (Entry<String, String> header : headerMap.entrySet()) {
					connection.addRequestProperty(header.getKey(), header.getValue());
				}
			}

			// Set the connection and read timeout
			connection.setConnectTimeout(OPERATION_TIMEOUT);
			connection.setReadTimeout(OPERATION_TIMEOUT);
			// Set the outputStream content for POST and PUT requests
			if ((method == Method.POST || method == Method.PUT)) {
				OutputStream output = null;
				try {
					if (NetworkConnection.CT_MULTIPART.equals(contentType)) {
						output = connection.getOutputStream();
						output.write(bos.toByteArray());
					} else {
						if (null != outputText) {
							L.e("newtewewerew", method + "------------------outputText:::" + outputText);
							output = connection.getOutputStream();
							output.write(outputText.getBytes());
						}
					}

				} finally {
					if (output != null) {
						try {
							output.close();
						} catch (IOException e) {
							// Already catching the first IOException so nothing
							// to do here.
						}
					}
				}
			}

			// Log the request
			if (L.canLog(Log.DEBUG)) {
				L.d(TAG, "Request url: " + urlValue);
				L.d(TAG, "Method: " + method.toString());

				if (parameterList != null && !parameterList.isEmpty()) {
					L.d(TAG, "Parameters:");
					for (int i = 0, size = parameterList.size(); i < size; i++) {
						BasicNameValuePair parameter = parameterList.get(i);
						String message = "- \"" + parameter.getName() + "\" = \"" + parameter.getValue() + "\"";
						L.d(TAG, message);
					}
					L.d(TAG, "Parameters String: \"" + paramBuilder.toString() + "\"");
				}

				if (postText != null) {
					L.d(TAG, "Post data: " + postText);
				}

				if (headerMap != null && !headerMap.isEmpty()) {
					L.d(TAG, "Headers:");
					for (Entry<String, String> header : headerMap.entrySet()) {
						L.d(TAG, "- " + header.getKey() + " = " + header.getValue());
					}
				}
			}

			String contentEncoding = connection.getHeaderField(HTTP.CONTENT_ENCODING);

			int responseCode = connection.getResponseCode();
			boolean isGzip = contentEncoding != null && contentEncoding.equalsIgnoreCase("gzip");
			L.d(TAG, "Response code: " + responseCode);

			if (responseCode == HttpStatus.SC_MOVED_PERMANENTLY) {
				String redirectionUrl = connection.getHeaderField(LOCATION_HEADER);
				throw new ConnectionException("New location : " + redirectionUrl, redirectionUrl);
			}

			InputStream errorStream = connection.getErrorStream();
			if (errorStream != null) {
				String error = convertStreamToString(errorStream, isGzip);
				// L.e("responseCode:"+responseCode+" httpErrorResCodeFilte:"+httpErrorResCodeFilte+" responseCode==httpErrorResCodeFilte:"+(responseCode==httpErrorResCodeFilte));
				if (responseCode == httpErrorResCodeFilte) { // 若是规定的400错误，平台指定的，错误比如手机号不存在，等等...
					logResBodyAndHeader(connection, error);
					return new ConnectionResult(connection.getHeaderFields(), error, responseCode);
				} else {
					throw new ConnectionException(error, responseCode);
				}
			}

			String body = convertStreamToString(connection.getInputStream(), isGzip);

			// 去提 返回的回车换行
			if (null != body && body.contains("\r")) {
				body = body.replace("\r", "");
			}

			if (null != body && body.contains("\n")) {
				body = body.replace("\n", "");
			}

			logResBodyAndHeader(connection, body);

			return new ConnectionResult(connection.getHeaderFields(), body, responseCode);
		} catch (IOException e) {
			L.e(TAG, "IOException", e);
			throw new ConnectionException(e);
		} catch (KeyManagementException e) {
			L.e(TAG, "KeyManagementException", e);
			throw new ConnectionException(e);
		} catch (NoSuchAlgorithmException e) {
			L.e(TAG, "NoSuchAlgorithmException", e);
			throw new ConnectionException(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private static void logResBodyAndHeader(HttpURLConnection connection, String body) {
		if (L.canLog(Log.VERBOSE)) {
			L.v(TAG, "Response body: ");

			int pos = 0;
			int bodyLength = body.length();
			while (pos < bodyLength) {
				L.v(TAG, body.substring(pos, Math.min(bodyLength - 1, pos + 200)));
				pos = pos + 200;
			}

			L.v(TAG, "Response Header: ");
			Map<String, List<String>> headerFields = connection.getHeaderFields();
			for (Map.Entry<String, List<String>> en : headerFields.entrySet()) {
				L.v(TAG, "key=" + en.getKey() + ",value=" + en.getValue());
			}
		}
	}

	private static String createAuthenticationHeader(UsernamePasswordCredentials credentials) {
		StringBuilder sb = new StringBuilder();
		sb.append(credentials.getUserName()).append(":").append(credentials.getPassword());
		return "Basic " + Base64.encodeToString(sb.toString().getBytes(), Base64.NO_WRAP);
	}

	private static SSLSocketFactory sAllHostsValidSocketFactory;

	private static SSLSocketFactory getAllHostsValidSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
		if (sAllHostsValidSocketFactory == null) {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			sAllHostsValidSocketFactory = sc.getSocketFactory();
		}

		return sAllHostsValidSocketFactory;
	}

	private static HostnameVerifier sAllHostsValidVerifier;

	private static HostnameVerifier getAllHostsValidVerifier() {
		if (sAllHostsValidVerifier == null) {
			sAllHostsValidVerifier = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
		}

		return sAllHostsValidVerifier;
	}

	private static String convertStreamToString(InputStream is, boolean isGzipEnabled) throws IOException {
		InputStream cleanedIs = is;
		if (isGzipEnabled) {
			cleanedIs = new GZIPInputStream(is);
		}

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(cleanedIs, UTF8_CHARSET));
			StringBuilder sb = new StringBuilder();
			for (String line; (line = reader.readLine()) != null;) {
				sb.append(line);
				sb.append("\n");
			}

			return sb.toString();
		} finally {
			if (reader != null) {
				reader.close();
			}

			cleanedIs.close();

			if (isGzipEnabled) {
				is.close();
			}
		}
	}
}
