package com.dh.superxz_bottom;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.dh.superxz_bottom.constant.Constant;
import com.dh.superxz_bottom.dhutils.CommUtil;
import com.dh.superxz_bottom.framework.db.AfeiDb;
import com.dh.superxz_bottom.framework.exception.CrashHandler;
import com.dh.superxz_bottom.xutils.sample.utils.Preference;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class VehicleApp extends Application {
	private static VehicleApp instance;

	private AfeiDb afeiDb;

	private List<Activity> activitys = new LinkedList<Activity>();

	private List<Activity> activityList = new LinkedList<Activity>();

	// 设置登录前的activity
	private Activity activity;

	private boolean openCrashHandler = false;

	public boolean appSwitch = false;

	private boolean isShowAssistant = true;
	private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime()
			.maxMemory();// 分配的可用内存
	public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;// 使用的缓存数量
	// 如果这边报缺少jar包错误，请添加fbcore-v0.5.0.jar fresco-v0.5.0.jar这两个jar包
	public static final int MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 5 * ByteConstants.MB;// 小图极低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
	public static final int MAX_SMALL_DISK_LOW_CACHE_SIZE = 10 * ByteConstants.MB;// 小图低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
	public static final int MAX_SMALL_DISK_CACHE_SIZE = 20 * ByteConstants.MB;// 小图磁盘缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）

	public static final int MAX_DISK_CACHE_VERYLOW_SIZE = 10 * ByteConstants.MB;// 默认图极低磁盘空间缓存的最大值
	public static final int MAX_DISK_CACHE_LOW_SIZE = 30 * ByteConstants.MB;// 默认图低磁盘空间缓存的最大值
	public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;// 默认图磁盘缓存的最大值
	final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
			(int) Runtime.getRuntime().maxMemory() / 4, // 内存缓存中总图片的最大大小,以字节为单位。
			Integer.MAX_VALUE, // 内存缓存中图片的最大数量。
			(int) Runtime.getRuntime().maxMemory() / 4, // 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
			Integer.MAX_VALUE, // 内存缓存中准备清除的总图片的最大数量。
			Integer.MAX_VALUE); // 内存缓存中单个图片的最大大小。

	public void onCreate() {
		super.onCreate();
		instance = this;
		afeiDb = AfeiDb.create(this, Constant.DB_NAME, true);

		// 全局捕获异常错误信息存至SD卡
		if (openCrashHandler) {
			CrashHandler crashHandler = CrashHandler.getInstance();
			crashHandler.init(getApplicationContext());
		}
		// 初始化 异步加载图片
		initImage_facebook();
	}

	public static VehicleApp getInstance() {
		return instance;
	}

	public void initImage_facebook() {
		Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
			@Override
			public MemoryCacheParams get() {
				return bitmapCacheParams;
			}
		};
		DiskCacheConfig diskCacheConfig = DiskCacheConfig
				.newBuilder(this)
				.setBaseDirectoryPath(
						new File(CommUtil.getInstance().getExternCachePath()))// 缓存图片基路径
				.setBaseDirectoryName("image")// 文件夹名
				// .setCacheErrorLogger(cacheErrorLogger)//日志记录器用于日志错误的缓存。
				// .setCacheEventListener(cacheEventListener)//缓存事件侦听器。
				// .setDiskTrimmableRegistry(diskTrimmableRegistry)//类将包含一个注册表的缓存减少磁盘空间的环境。
				.setMaxCacheSize(MAX_DISK_CACHE_SIZE)// 默认缓存的最大大小。
				.setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)// 缓存的最大大小,使用设备时低磁盘空间。
				.setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)// 缓存的最大大小,当设备极低磁盘空间
				// .setVersion(version)
				.build();
		DiskCacheConfig diskSmallCacheConfig = DiskCacheConfig
				.newBuilder(this)
				.setBaseDirectoryPath(
						new File(CommUtil.getInstance().getExternCachePath()))// 缓存图片基路径
				.setBaseDirectoryName("image_samll")// 文件夹名
				// .setCacheErrorLogger(cacheErrorLogger)//日志记录器用于日志错误的缓存。
				// .setCacheEventListener(cacheEventListener)//缓存事件侦听器。
				// .setDiskTrimmableRegistry(diskTrimmableRegistry)//类将包含一个注册表的缓存减少磁盘空间的环境。
				.setMaxCacheSize(MAX_DISK_CACHE_SIZE)// 默认缓存的最大大小。
				.setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE)// 缓存的最大大小,使用设备时低磁盘空间。
				.setMaxCacheSizeOnVeryLowDiskSpace(
						MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)// 缓存的最大大小,当设备极低磁盘空间
				// .setVersion(version)
				.build();
		ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig
				.newBuilder(this)
				// .setAnimatedImageFactory(AnimatedImageFactory
				// animatedImageFactory)//图片加载动画
				.setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)// 内存缓存配置（一级缓存，已解码的图片）
				// .setCacheKeyFactory(cacheKeyFactory)//缓存Key工厂
				// .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)//内存缓存和未解码的内存缓存的配置（二级缓存）
				// .setExecutorSupplier(executorSupplier)//线程池配置
				// .setImageCacheStatsTracker(imageCacheStatsTracker)//统计缓存的命中率
				// .setImageDecoder(ImageDecoder imageDecoder) //图片解码器配置
				// .setIsPrefetchEnabledSupplier(Supplier<Boolean>
				// isPrefetchEnabledSupplier)//图片预览（缩略图，预加载图等）预加载到文件缓存
				.setMainDiskCacheConfig(diskCacheConfig)// 磁盘缓存配置（总，三级缓存）
				// .setMemoryTrimmableRegistry(memoryTrimmableRegistry)
				// //内存用量的缩减,有时我们可能会想缩小内存用量。比如应用中有其他数据需要占用内存，不得不把图片缓存清除或者减小
				// 或者我们想检查看看手机是否已经内存不够了。
				// .setNetworkFetchProducer(networkFetchProducer)//自定的网络层配置：如OkHttp，Volley
				// .setPoolFactory(poolFactory)//线程池工厂配置
				// .setProgressiveJpegConfig(progressiveJpegConfig)//渐进式JPEG图
				// .setRequestListeners(requestListeners)//图片请求监听
				// .setResizeAndRotateEnabledForNetwork(boolean
				// resizeAndRotateEnabledForNetwork)//调整和旋转是否支持网络图片
				.setSmallImageDiskCacheConfig(diskSmallCacheConfig)// 磁盘缓存配置（小图片，可选～三级缓存的小图优化缓存）
				// . setMainDiskCacheConfig(mainDiskCacheConfig)
				.build();
		Fresco.initialize(this, imagePipelineConfig);
	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		if (activitys != null && activitys.size() > 0) {
			if (!activitys.contains(activity)) {
				activitys.add(activity);
			}
		} else {
			activitys.add(activity);
		}

	}

	public List<Activity> getActivitys() {
		return activitys;
	}

	public void exit() {
		if (activitys != null && activitys.size() > 0) {
			for (Activity activity : activitys) {
				activity.finish();
			}
		}
		System.exit(0);

		// 直接在服务中，暂停了。。。

		// 以便下次打开下载管理时，可以置成下载状态中。。。。
		// 发送广播，让其全部暂停掉下载...
		/*
		 * sendBroadcast(new
		 * Intent(DownloadService.PAUSEALLRECEIVER_ACTION).putExtra("isExit",
		 * true));
		 */
		// Log.i(TAG, "发送广播方式退出了.....");
		// 怎么停止掉服务...

		// Process.killProcess(Process.myPid());//停止掉本应用进程，即退出了应用
	}

	public void finishAll() {
		if (activitys != null && activitys.size() > 0) {
			for (Activity activity : activitys) {
				activity.finish();
			}
		}
	}

	public AfeiDb getAfeiDb() {
		return afeiDb;
	}

	// 添加Activity到容器中
	public void addActivityToList(Activity activity) {
		if (activityList != null && activityList.size() > 0) {
			if (!activityList.contains(activity)) {
				activityList.add(activity);
			}
		} else {
			activityList.add(activity);
		}

	}

	public List<Activity> getActivityLists() {
		return activityList;
	}

	public void finishAllActivityLists() {
		if (activityList != null && activityList.size() > 0) {
			for (Activity activity : activityList) {
				activity.finish();
			}
		}
	}

	public String getVehicleCity() {
		return Preference.getString("vehicleCity");
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public boolean isAppSwitch() {
		return appSwitch;
	}

	public void setAppSwitch(boolean appSwitch) {
		this.appSwitch = appSwitch;
	}

	public void setShowAssistant(boolean isShowAssistant) {
		this.isShowAssistant = isShowAssistant;
	}

	public boolean isShowAssistant() {
		return isShowAssistant;
	}

}
