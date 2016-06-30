package com.dh.superxz_bottom.framework.net.fgview;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dh.superxz_bottom.framework.db.annotation.Table;

@Table(name = "T_HTTP_CACHE")
public class ReqResBean implements Serializable {

	/**
	 * 
	 */
	private String _id; // 自增主键
	private int requestHashCode;
	private String response;
	private long time;
	private int responseCode;
//	private Map<String, List<String>> headerMap;
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public int getRequestHashCode() {
		return requestHashCode;
	}

	public void setRequestHashCode(int requestHashCode) {
		this.requestHashCode = requestHashCode;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

//	public Map<String, List<String>> getHeaderMap() {
//		return headerMap;
//	}
//
//	public void setHeaderMap(Map<String, List<String>> headerMap) {
//		this.headerMap = headerMap;
//	}

	@Override
	public String toString() {
		return "ReqResBean [_id=" + _id + ", requestHashCode="
				+ requestHashCode + ", response=" + response + ", time=" + time
				+ "]";
	}

}
