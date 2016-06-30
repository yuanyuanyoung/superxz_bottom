package com.dh.superxz_bottom.framework.view;

import java.lang.reflect.Method;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * view 的 onClick、onItemClick、onItemSelected、onItemLongClick等事件处理器
 * @author chensf5
 *
 */
public class EventListener implements OnItemClickListener, OnClickListener,
		OnItemSelectedListener, OnItemLongClickListener , OnLongClickListener{

	private Object handler;
	
	private String onClickMethod;
	
	private String onLongClickMethod;
	
	private String onItemLongClickMethod;
	
	private String onItemClickMethod;
	
	private String onItemSelectedMethod;
	
	private String onNothingSelectedMethod;
	
	public EventListener(Object handler){
		this.handler = handler;
	}
	
	public EventListener onClickMethod(String onClickMethod){
		this.onClickMethod = onClickMethod;
		return this;
	}
	
	public EventListener onLongClickMethod(String onLongClickMethod){
		this.onLongClickMethod = onLongClickMethod;
		return this;
	}
	
	public EventListener onItemLongClickMethod(String onItemLongClickMethod){
		this.onItemLongClickMethod = onItemLongClickMethod;
		return this;
	}
	
	public EventListener onItemClickMethod(String onItemClickMethod){
		this.onItemClickMethod = onItemClickMethod;
		return this;
	}
	
	public EventListener onItemSelectedMethod(String onItemSelectedMethod){
		this.onItemSelectedMethod = onItemSelectedMethod;
		return this;
	}
	
	public EventListener onNothingSelectedMethod(String onNothingSelectedMethod){
		this.onNothingSelectedMethod = onNothingSelectedMethod;
		return this;
	}
	
	
	
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		if(!TextUtils.isEmpty(onItemLongClickMethod)){
			invorkOnItemLongClick(onItemLongClickMethod,parent,view,position,id);
		}
		return false;
	}

	private boolean invorkOnItemLongClick(String onItemLongClickMethod, Object... params) {
		if(null == handler) throw new ViewException("EventListener handler is null!");
		Method method = null;
		try {
			method = handler.getClass().getDeclaredMethod(onItemLongClickMethod, AdapterView.class,View.class,int.class,long.class);
			if(null != method){
				Object obj = method.invoke(handler, params);
				return obj==null?false:Boolean.valueOf(obj.toString());
			}else{
				throw new ViewException(handler.getClass().getName()+" have not such method ["+onItemLongClickMethod+"]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if(!TextUtils.isEmpty(onItemSelectedMethod)){
			invorkOnItemSelected(onItemSelectedMethod,parent,view,position,id);
		}
	}

	private Object invorkOnItemSelected(String onItemSelectedMethod,Object... params) {
		if(null == handler) throw new ViewException("EventListener handler is null !");
		Method method = null;
		try {
			method = handler.getClass().getDeclaredMethod(onItemSelectedMethod, AdapterView.class,View.class,int.class,long.class);
			if(null != method){
				return method.invoke(handler, params);
			}else{
				throw new ViewException(handler.getClass().getName()+" have not such method ["+onItemSelectedMethod+"]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		if(!TextUtils.isEmpty(onNothingSelectedMethod)){
			invorkOnNothingSelected(onNothingSelectedMethod,parent);
		}
	}

	private Object invorkOnNothingSelected(String onNothingSelectedMethod,Object... params) {
		if(null == handler) throw new ViewException("EventListener handler is null !");
		Method method = null;
		try {
			method = handler.getClass().getDeclaredMethod(onNothingSelectedMethod, AdapterView.class);
			if(null != method){
				return method.invoke(handler, params);
			}else{
				throw new ViewException(handler.getClass().getName()+" have not such method ["+onNothingSelectedMethod+"]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void onClick(View v) {
		if(!TextUtils.isEmpty(onClickMethod)){
			invorkOnClick(onClickMethod,v);
		}
	}

	private Object invorkOnClick(String onClickMethod, View v) {
		if(null == handler) throw new ViewException("EventListener handler is null!");
		Method method = null;
		try{
			method = handler.getClass().getDeclaredMethod(onClickMethod, View.class);
			if(null != method){
				Object obj = method.invoke(handler, v);
				return obj;
			}else{
				throw new ViewException(handler.getClass().getName()+" have not such method ["+onClickMethod+"]");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(!TextUtils.isEmpty(onItemClickMethod)){
			invorkOnItemClick(onItemClickMethod,parent,view,position,id);
		}
		
	}

	private Object invorkOnItemClick(String onItemClickMethod,Object... params ) {
		if(null == handler) throw new ViewException("EventListener handler is null!");
		Method method = null;
		try {
			method = handler.getClass().getDeclaredMethod(onItemClickMethod, AdapterView.class,View.class,int.class,long.class);
			if(null != method){
				return method.invoke(handler, params);
			}else{
				throw new ViewException(handler.getClass().getName()+" have not such method ["+onItemClickMethod+"]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean onLongClick(View v) {
		
		if(!TextUtils.isEmpty(onLongClickMethod)){
			return invorkOnLongClick(onLongClickMethod,v);
		}
		return false;
	}

	private boolean invorkOnLongClick(String onLongClickMethod, View v) {
		if(null == handler) throw new ViewException("EventListener handler is null!");
		Method method = null;
		try {
			method = handler.getClass().getDeclaredMethod(onLongClickMethod, View.class);
			if(null != method){
				Object obj = method.invoke(handler, v);
				return null == obj ? false : Boolean.valueOf(obj.toString());
			}else{
				throw new ViewException(handler.getClass().getName()+" have not such method ["+onLongClickMethod+"]");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
