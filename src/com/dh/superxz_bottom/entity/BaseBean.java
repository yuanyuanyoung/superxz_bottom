package com.dh.superxz_bottom.entity;


import java.io.Serializable;

/**
 * @author 超级小志
 *
 */
public class BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String resultcode;
	private String message;

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "BaseBean [resultcode=" + resultcode + ", message=" + message
				+ "]";
	}
}
