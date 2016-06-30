package com.dh.superxz_bottom.framework.net.fgview;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.dh.superxz_bottom.framework.net.fgview.Action.OnDataGainBeforeAfterListener;
/**
 * 将用户输入及请求需要的输入及请求处理前与后相应的dosomething，封装成RequestInputer 请求输入者
 * @author afei
 *
 */
public abstract class RequestInputer<T> implements OnDataGainBeforeAfterListener{

	private HashMap<String,String> mReqPar = new HashMap<String,String>();
	
	private Context mContext;
	
	private Request<T> mRequest;
	
	/**
	 * 默认显示，正在加载中的对话框
	 * 当这个开关，返回false时，
	 * 请求开始及结束的方法将会被回调（onBeforeGainAction及onAfterGainAction会回调，有需要建议重写此方法）
	 * @return
	 */
	public boolean isShowLoadingDialog() { 
		return true;
	}

	/**
	 * 默认显示结果返回的错误对话框 ，可重新返回false时，不显示错误的对话框
	 * @return
	 */
	public boolean isErrorDialog() {
		return true;
	}
	
	/**
	 * isShowLoadingDialog 方法返回false时，可重新此方法来,
	 * 获取数据之前进行的操作
	 */
	public void onBeforeGainAction(String msg){
		
	}

	/**
	 * 获取数据之后进行的操作
	 */
	public void onAfterGainAction(){
		
	}
	
	/**
	 * 获取请求入参，内部model用
	 * @return
	 */
	public HashMap<String, String> getReqPar() {
		return mReqPar;
	}
	
	public Context getContext() {
		return mContext;
	}

	public RequestInputer(Context context){
		this.mContext = context;
	}
	
	/**
	 * Controller获取到用户的输入后，将用户输入的或请求需要的入参组成成map入参。
	 * @param reqPar
	 */
	public abstract void setReqPar(Map<String,String> reqPar);

	
    public Request<T> getRequest() {
        return mRequest;
    }

    public void setRequest(Request<T> request) {
        this.mRequest = request;
    }

    
	
}
