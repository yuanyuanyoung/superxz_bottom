package com.dh.superxz_bottom.framework.view;

import com.dh.superxz_bottom.framework.db.exception.AfeiException;
import com.dh.superxz_bottom.framework.log.L;

/**
 * afei 视图view的异常
 * @author chensf5
 *
 */
public class ViewException extends AfeiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String strMsg;
	
	public ViewException(){}
	
	public ViewException(String strMsg){
		super(strMsg);
		this.strMsg = strMsg;
	}
	
	public void printStackInTrace(){
		if(null != strMsg){
			L.e(strMsg);
		}
		super.printStackTrace();
	}

}
