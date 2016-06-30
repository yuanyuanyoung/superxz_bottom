package com.dh.superxz_bottom.framework.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池管理
 * @author chensf5
 */
public class ThreadPoolManager {
	private ExecutorService service;
	
	private static final int INIT_POOL_SIZE  = 12; //至少可以执行12个线程
	
	private ThreadPoolManager(){
		int num = Runtime.getRuntime().availableProcessors();//获取当前系统的CPU 数目
		if(num*2 > INIT_POOL_SIZE){
			service = Executors.newFixedThreadPool(num*2);//通常根据系统资源情况灵活定义线程池大小
		}else{
			service = Executors.newFixedThreadPool(INIT_POOL_SIZE);//通常根据系统资源情况灵活定义线程池大小
		}
	}
	
	private static final ThreadPoolManager manager= new ThreadPoolManager();
	
	public static ThreadPoolManager getInstance(){
		return manager;
	}
	
	public void executeTask(Runnable runnable){
		service.execute(runnable);
	}
	
	public void shutdownNow(){
		service.shutdownNow();
	}
}