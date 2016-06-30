package com.dh.superxz_bottom.dhutils;

import java.io.File;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.os.Environment;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 通用工具方法类
 * 
 * @author 超级小志 2015-5-28
 * 
 */
@SuppressLint("NewApi")
public class CommUtil {

    private static CommUtil commUtil = null;

    public static CommUtil getInstance() {
	if (null == commUtil) {
	    commUtil = new CommUtil();
	}
	return commUtil;
    }

    public String getExternCachePath() {
	String path = Environment.getExternalStorageDirectory()
		.getAbsolutePath();
	if (path != null) {
	    path += "/data/com.dh/cache/";
	    File file = new File(path);
	    if (!file.exists()) {
		if (file.mkdirs()) {
		    return path;
		}
	    } else {
		return path;
	    }
	}
	return null;
    }

    public boolean hasSdcard() {
	String state = Environment.getExternalStorageState();
	if (state.equals(Environment.MEDIA_MOUNTED)) {
	    return true;
	} else {
	    return false;
	}
    }

    public static int getVersionCode(Activity activity) {
	int versionCode = 0;
	try {
	    PackageInfo info = activity.getPackageManager().getPackageInfo(
		    activity.getPackageName(), 0);
	    // 当前应用的版本名称
	    versionCode = info.versionCode;
	} catch (NameNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return versionCode;
    }

    public boolean containsEmoji(String source) {
	int len = source.length();
	for (int i = 0; i < len; i++) {
	    char codePoint = source.charAt(i);
	    if (!isEmojiCharacter(codePoint)) {
		// 如果不能匹配,则该字符是Emoji表情 return

		return true; // true;
		// } } return false; }
	    }
	}
	return false;

    }

    public boolean isMail(String email) {
	String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	Pattern p = Pattern.compile(str);
	Matcher m = p.matcher(email);

	return m.matches();
    }

    public boolean isEmail(String email) {
	final Pattern emailer = Pattern
		.compile("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$");

	if (email == null || email.trim().length() == 0)

	    return false;

	return emailer.matcher(email).matches();
    }

    public boolean isPhoneNumber(String phone) {

	String regExp = "^(0|86|17951)?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$";

	Pattern p = Pattern.compile(regExp);

	Matcher m = p.matcher(phone);
	return m.find();

    }

    public boolean isQQNumber(String phone) {

	String regExp = "^[0-9]{5,13}+$";

	Pattern p = Pattern.compile(regExp);

	Matcher m = p.matcher(phone);
	return m.find();

    }

    public void isLock(View v, boolean value) {
	v.setClickable(value);
	v.setFocusable(value);
	v.setEnabled(value);
    }

    public String getNomalStr(String source) {
	int len = source.length();
	String temp = "";
	for (int i = 0; i < len; i++) {
	    char codePoint = source.charAt(i);
	    if (isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情 return
		// source=source.replace(source.substring(i, i + 1), "");
		temp = temp + source.substring(i, i + 1);
		// } } return false; }
	    } else
		temp = temp + "@";
	}
	return temp;
    }

    /** * 判断是否是Emoji * @param codePoint 比较的单个字符 * @return */
    private boolean isEmojiCharacter(char codePoint) {
	return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
		|| (codePoint == 0xD)
		|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
		|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
		|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    // 获取String.xml中的文字资源，RId代表string 的对应id
    public String getStringFromXml(Context context, int RId) {
	String str = "";
	str = context.getResources().getString(RId);
	return str;
    }

    /**
     * 获取文件夹大小
     * 
     * @param file
     *            File实例
     * @return long
     */
    public static long getFolderSize(java.io.File file) {

	long size = 0;
	try {
	    java.io.File[] fileList = file.listFiles();
	    for (int i = 0; i < fileList.length; i++) {
		if (fileList[i].isDirectory()) {
		    size = size + getFolderSize(fileList[i]);

		} else {
		    size = size + fileList[i].length();

		}
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	// return size/1048576;
	return size;
    }

    /**
     * 删除指定目录下文件及目录
     * 
     * @param deleteThisPath
     * @param filepath
     * @return
     */
    public void deleteFolderFile(String filePath, boolean deleteThisPath) {
	if (!TextUtils.isEmpty(filePath)) {
	    try {
		File file = new File(filePath);
		if (file.isDirectory()) {// 处理目录
		    File files[] = file.listFiles();
		    for (int i = 0; i < files.length; i++) {
			deleteFolderFile(files[i].getAbsolutePath(), true);
		    }
		}
		if (deleteThisPath) {
		    if (!file.isDirectory()) {// 如果是文件，删除
			file.delete();
		    } else {// 目录
			if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
			    file.delete();
			}
		    }
		}
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    /**
     * 格式化单位
     * 
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
	double kiloByte = size / 1024;
	if (kiloByte < 1) {
	    if (size == 0) {
		return "0B";
	    }
	    return size + "B";
	}

	double megaByte = kiloByte / 1024;
	if (megaByte < 1) {
	    BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
	    return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
		    .toPlainString() + "KB";
	}

	double gigaByte = megaByte / 1024;
	if (gigaByte < 1) {
	    BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
	    return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
		    .toPlainString() + "MB";
	}

	double teraBytes = gigaByte / 1024;
	if (teraBytes < 1) {
	    BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
	    return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
		    .toPlainString() + "GB";
	}
	BigDecimal result4 = new BigDecimal(teraBytes);
	return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
		+ "TB";
    }

    /**
     * view 背景色变化
     * 
     * @author Scw
     * 
     * @param clickView
     *            点击的视图
     * @param addEffectView
     *            添加效果的视图
     * @param downEffect
     *            按下的效果
     * @param upEffect
     *            正常的效果
     */
    public static void clickEffect(View clickView, final View addEffectView,
	    final int downEffect, final int upEffect) {
	clickView.setOnTouchListener(new OnTouchListener() { // 按钮点击效果
		    @SuppressLint("ClickableViewAccessibility")
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
			    addEffectView.setBackgroundResource(downEffect);
			} else if (event.getAction() == MotionEvent.ACTION_UP
				|| event.getAction() == MotionEvent.ACTION_OUTSIDE
				|| event.getAction() == MotionEvent.ACTION_CANCEL) {
			    addEffectView.setBackgroundResource(upEffect);
			}
			return false;
		    }
		});
    }

    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     * 
     * @author Scw
     * @param inputString
     * @return
     */
    public static String getPingYin(String inputString) {
	HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
	format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	format.setVCharType(HanyuPinyinVCharType.WITH_V);
	char[] input = inputString.trim().toCharArray();
	String output = "";
	try {
	    for (char curchar : input) {
		if (java.lang.Character.toString(curchar).matches(
			"[\\u4E00-\\u9FA5]+")) {
		    String[] temp = PinyinHelper.toHanyuPinyinStringArray(
			    curchar, format);
		    output += temp[0];
		} else
		    output += java.lang.Character.toString(curchar);
	    }
	} catch (BadHanyuPinyinOutputFormatCombination e) {
	    e.printStackTrace();
	}
	return output;
    }

    /**
     * 获取每个汉字的首字母
     * 
     * @author Scw
     * @param chines
     *            汉字
     * @return 拼音
     */
    public static String getAllFirstSpell(String chinese) {
	StringBuffer pybf = new StringBuffer();
	char[] arr = chinese.toCharArray();
	HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	for (char curchar : arr) {
	    if (curchar > 128) {
		try {
		    String[] temp = PinyinHelper.toHanyuPinyinStringArray(
			    curchar, defaultFormat);
		    if (temp != null) {
			pybf.append(temp[0].charAt(0));
		    }
		} catch (BadHanyuPinyinOutputFormatCombination e) {
		    e.printStackTrace();
		}
	    } else {
		pybf.append(curchar);
	    }
	}
	return pybf.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 获取所有汉字中第一个汉字的首字母-小写
     * 
     * @author Scw
     * @param chines
     *            汉字
     * @return 拼音-小写
     */
    public static String getFirstSpellSmall(String chinese) {
	StringBuffer pybf = new StringBuffer();
	char arr = chinese.charAt(0);
	HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	if (arr > 128) {
	    try {
		String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr,
			defaultFormat);
		if (temp != null) {
		    pybf.append(temp[0].charAt(0));
		}
	    } catch (BadHanyuPinyinOutputFormatCombination e) {
		e.printStackTrace();
	    }
	} else {
	    pybf.append(arr);
	}

	return pybf.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 获取所有汉字中第一个汉字的首字母-大写
     * 
     * @author Scw
     * @param chines
     *            汉字
     * @return 拼音 -大写
     */
    public static String getFirstSpellBig(String chinese) {
	StringBuffer pybf = new StringBuffer();
	char arr = chinese.charAt(0);
	HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
	defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	if (arr > 128) {
	    try {
		String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr,
			defaultFormat);
		if (temp != null) {
		    pybf.append(temp[0].charAt(0));
		}
	    } catch (BadHanyuPinyinOutputFormatCombination e) {
		e.printStackTrace();
	    }
	} else {
	    pybf.append(arr);
	}

	return pybf.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 把一个字符串中的大写转为小写，小写转换为大写 -方法1
     * 
     * @author Scw
     * @param str
     * @return String
     */
    public static String exChange(String str) {
	StringBuffer sb = new StringBuffer();
	if (str != null) {
	    for (int i = 0; i < str.length(); i++) {
		char c = str.charAt(i);
		if (Character.isUpperCase(c)) {
		    sb.append(Character.toLowerCase(c));
		} else if (Character.isLowerCase(c)) {
		    sb.append(Character.toUpperCase(c));
		}
	    }
	}

	return sb.toString();
    }

    /**
     * 把一个字符串中的大写转为小写，小写转换为大写 -方法2
     * 
     * @author Scw
     * @param str
     * @return String
     */
    @SuppressLint("DefaultLocale")
    public static String exChange2(String str) {
	for (int i = 0; i < str.length(); i++) {
	    // 如果是小写
	    if (str.substring(i, i + 1).equals(
		    str.substring(i, i + 1).toLowerCase())) {
		str.substring(i, i + 1).toUpperCase();
	    } else {
		str.substring(i, i + 1).toLowerCase();
	    }
	}
	return str;
    }
	public static int getStatusHeight(Activity activity) {
		int statusHeight = 0;
		Rect localRect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
		statusHeight = localRect.top;
		if (0 == statusHeight) {
			Class<?> localClass;
			try {
				localClass = Class.forName("com.android.internal.R$dimen");
				Object localObject = localClass.newInstance();
				int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
				statusHeight = activity.getResources().getDimensionPixelSize(i5);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusHeight;
	}
}
