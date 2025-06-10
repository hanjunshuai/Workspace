package com.anning.projectrxjava;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    public static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static Date string2Date(String dateStr) {
        return string2Date(dateStr, DATE_PATTERN);
    }

    // string 转 日期
    public static Date string2Date(String dateStr, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 时间戳转换日期格式字符串
     */
    public static String long2String(long time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(new Date(time));
    }

    /**
     * 日期格式字符串转换时间戳(s)
     */
    public static String string2Long(String date, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
            return String.valueOf(sdf.parse(date).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取年、月、日
     *
     * @param field Calendar.YEAR、Calendar.MONTH、Calendar.DAY_OF_MONTH
     */
    public static int get(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(field);
    }

    /**
     * 获取当天的日期
     */
    public static String getCurrentDay(String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date());
    }

    public static String date2String(Date date) {
        return date2String(date, DATE_PATTERN);
    }

    public static String date2String(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    public static String getCurrentZoneTime(long millis) {
        return getCurrentZoneTime(millis, DATE_PATTERN);
    }

    /**
     * 计算当前时区的 时间
     *
     * @param millis gmt 时间戳 ms
     */
    public static String getCurrentZoneTime(long millis, String pattern) {
        SimpleDateFormat zoneSdf = new SimpleDateFormat(pattern, Locale.getDefault());
        TimeZone currentDeviceZone = DateUtil.getCurrentDeviceZone();
        zoneSdf.setTimeZone(currentDeviceZone);
        return zoneSdf.format(new Date((millis)));
    }

    /**
     * 当前设备时区信息
     *
     * @return
     */
    public static TimeZone getCurrentDeviceZone() {
        TimeZone timeZone = TimeZone.getDefault();
        String id = timeZone.getID();
        //获取名字，如“”
        String name = timeZone.getDisplayName();
        //获取名字，如“GMT+08:00”
        String shotName = timeZone.getDisplayName(false, TimeZone.SHORT);
        //获取时差，返回值毫秒
        int time = timeZone.getRawOffset();
//        Log.e("TAG", " id " + id + " name :" + name + " shotName " + shotName + "  time :" + time);
        String gmtOffsetString = createGmtOffsetString(true, true, time);
//        Log.e("TAG", "gmtOffsetString : " + gmtOffsetString);
        return timeZone;
    }

    private static String createGmtOffsetString(boolean includeGmt,
                                                boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;
        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder, 2, offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');
        }
        appendNumber(builder, 2, offsetMinutes % 60);
        return builder.toString();
    }

    private static void appendNumber(StringBuilder builder, int count, int value) {
        String string = Integer.toString(value);
        for (int i = 0; i < count - string.length(); i++) {
            builder.append('0');
        }
        builder.append(string);
    }

    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return
     */
    public static String getPastDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert endDate != null;
        return dft.format(endDate);
    }

    public static String today() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }
}
