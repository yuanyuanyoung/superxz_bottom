package com.dh.superxz_bottom.dhutils;
/*
 * Copyright (C), 2014-2014, 联创车盟汽车服务有限公司
 * FileName: ImageUtils
 * Author:   jun.w
 * Date:     2014/11/6 09:39
 * Description: 图片处理类
 */
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;

import com.dh.superxz_bottom.framework.util.Density;

public class ImageUtils {
	/**
	 * @param 将图片内容解析成字节数组
	 * @param inStream
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// while ((len = inStream.read(buffer)) != -1)
		while ((len = inStream.read(buffer)) > 0) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;

	}

	/**
	 * @param 将字节数组转换为ImageView可调用的Bitmap对象
	 * @param bytes
	 * @param opts
	 * @return Bitmap
	 */
	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	/**
	 * @param 图片缩放
	 * @param bitmap
	 *            对象
	 * @param w
	 *            要缩放的宽度
	 * @param h
	 *            要缩放的高度
	 * @return newBmp 新 Bitmap对象
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newBmp;
	}

	public static Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
		int originWidth = bitmap.getWidth();
		int originHeight = bitmap.getHeight();
		// no need to resize
		if (originWidth < maxWidth && originHeight < maxHeight) {
			return bitmap;
		}
		int width = originWidth;
		int height = originHeight;
		// 若图片过宽, 则保持长宽比缩放图片
		if (originWidth > maxWidth) {
			width = maxWidth;
			double i = originWidth * 1.0 / maxWidth;
			height = (int) Math.floor(originHeight / i);
			bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
		}
		// 若图片过长, 则从上端截取
		if (height > maxHeight) {
			height = maxHeight;
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
		}
		// Log.i(TAG, width + " width");
		// Log.i(TAG, height + " height");

		return bitmap;
	}

	/**
	 * 把Bitmap转Byte
	 * 
	 * @Author HEH
	 * @EditTime 2010-07-19 上午11:45:56
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] bytes = baos.toByteArray();
		try {
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}

	public static byte[] Bitmap2BytesByType(Bitmap bm, String fileType) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (fileType.equals("image/png"))
			bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		else
			bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] bytes = baos.toByteArray();
		try {
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}

	/**
	 * 把File转Byte
	 * 
	 * @Author yinzl filePath 代表文件路径
	 * @EditTime 2010-07-19 上午11:45:56
	 */
	public static byte[] File2Bytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;

	}

	// /**
	// * 根据路径取bitmap
	// * @param myJpgPath
	// * @return
	// */
	// public static Bitmap getBitMapFromFile(String myJpgPath)
	// {
	// // String myJpgPath = "/sdcard/test.png";
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inSampleSize = 2;
	// Bitmap bm = BitmapFactory.decodeFile(myJpgPath, options);
	// return bm;
	// }

	/**
	 * 把字节数组保存为一个文件
	 * 
	 * @Author HEH
	 * @EditTime 2010-07-19 上午11:45:56
	 */
	public static File getFileFromBytes(byte[] b, String outputFile) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * //获得圆角图片的方法
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	// 将Drawable转化为Bitmap
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}

	/**
	 * 将Drawable转化为圆角Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToRoundBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		// Canvas canvas = new Canvas(bitmap);
		// drawable.setBounds(0, 0, width, height);
		// drawable.draw(canvas);

		return getRoundedCornerBitmap(bitmap, 6);

	}

	/**
	 * 提取图片并压缩
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapFromPath(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		// 获取这个图片的宽和高
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);// 此时返回bm为空
		// if (bitmap.getHeight() > 300 || bitmap.getWidth() > 200)
		{

			options.inJustDecodeBounds = false;
			// 计算缩放比
			int be = (int) (options.outHeight / (float) 200);
			if (be <= 0)
				be = 1;
			options.inSampleSize = be;
			// 重新读入图片，注意这次要把options.inJustDecodeBounds设为false哦
			bitmap = BitmapFactory.decodeFile(path, options);
			// int w = bitmap.getWidth();
			// int h = bitmap.getHeight();
			// System.out.println(w + " " + h);
		}
		return bitmap;
	}

	public static Bitmap decodeImage(String path, Context context) {
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, op);
		int screenWidth = Density.getSceenWidth(context);
		int screenHeight = Density.getSceenHeight(context);

		int zoomScale = ImageUtils.computeSampleSize(op, -1, screenWidth
				* screenHeight);

		op.inSampleSize = zoomScale;

		op.inJustDecodeBounds = false; // 注意这里，一定要设置为false，因为上面我们将其设置为true来获取图片尺寸了
		bitmap = BitmapFactory.decodeFile(path, op);
		return bitmap;
	}

	public static Bitmap decodeImage(Context context, Uri uri)
			throws FileNotFoundException {
		BitmapFactory.Options op = new BitmapFactory.Options();
		// op.inSampleSize = 8;
		op.inJustDecodeBounds = true;
		// Bitmap pic = BitmapFactory.decodeFile(imageFilePath,
		// op);//调用这个方法以后，op中的outWidth和outHeight就有值了
		// 由于使用了MediaStore存储，这里根据URI获取输入流的形式
		Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver()
				.openInputStream(uri), null, op);
		int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
		int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
		// 计算缩放率，新尺寸除原始尺寸
		op.inSampleSize = computeSampleSize(op, -1, screenWidth * screenHeight);
		op.inJustDecodeBounds = false; // 注意这里，一定要设置为false，因为上面我们将其设置为true来获取图片尺寸了
		bitmap = BitmapFactory.decodeStream(context.getContentResolver()
				.openInputStream(uri), null, op);
		return bitmap;
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/*
	 * 旋转图片
	 * 
	 * @param angle
	 * 
	 * @param bitmap
	 * 
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		// matrix.postRotate(angle);
		matrix.setRotate(angle); // 旋转angle度

		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

}
