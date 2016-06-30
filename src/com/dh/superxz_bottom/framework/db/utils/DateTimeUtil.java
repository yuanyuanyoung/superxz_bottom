package com.dh.superxz_bottom.framework.db.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    public static Date stringToDateTime(String strDate) {
        if (strDate != null) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    public static String dateTimeToString(Object dateTime) {
        if (dateTime != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTime);
        }
        return null;
    }

    public static String dateTimeToYMD(Object dateTime) {
        String sourceString = null;
        if (dateTime != null) {
            sourceString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTime);
            sourceString = sourceString.substring(0, 10);
            sourceString = sourceString.replace("-", "");
            return sourceString;
        }
        return null;
    }
    // 标准时间格式
    public static String getDatetimeStr(String strDate) {
        if(!TextUtils.isEmpty(strDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(stringToDateTime(strDate));
        }
        return "";
    }
    // 标准时间格式
    public static String getChinaDatetimeStr(String strDate) {
        if(!TextUtils.isEmpty(strDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            return sdf.format(stringToDateTime(strDate));
        }
        return "";
    }


    // 根据分钟数显示时长
    public static String getTimeStrFormSMins(Double mins) {// 如果超过1小时就是 x小时 x分 不超过一小时就是 xx分钟
        String sourceString = (int) (mins % 60) + "分钟";
        if (mins >= 60) {
            sourceString = (int) (mins % 60) + "分";
            String hourString = (int) (mins / 60) + "小时";
            return hourString + sourceString;
        }
        return sourceString;
    }

    public static String getCurrentTimeWithoutYear() {
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date());
    }

}
