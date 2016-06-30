package com.dh.superxz_bottom.framework.util;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecute {
	private static int THREAD_POOL_SIZE = 1;

	/** 线程池 **/
	private ExecutorService mExecutor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	
	private static ThreadExecute mThreadExecute = new ThreadExecute();
	
	private ThreadExecute(){}
	
	public static ThreadExecute getInstance(){
		return mThreadExecute;
	}
	
	
	public void execute(Runnable runnable){
		mExecutor.execute(runnable);
	}
}
