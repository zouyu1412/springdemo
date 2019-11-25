package com.ssp.higher.base.utils.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * 英文简写（默认）如：2010-12-01
	 */
	public static String FORMAT_MINI = "yyyyMMdd";
	public static String SHORT_MINI = "yyMMdd";
	/**
	 * 英文简写（默认）如：2010-12-01
	 */
	public static String FORMAT_SHORT = "yyyy-MM-dd";
	/**
	 * 英文全称 如：2010-12-01 23:15:06
	 */
	public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * 中文简写 如：2010年12月01日
	 */
	public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
	/**
	 * 中文全称 如：2010年12月01日 23时15分06秒
	 */
	public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
	/**
	 * 精确到毫秒的完整中文时间
	 */
	public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

	public static void main(String[] args) {
		System.out.println(getTimeString());
		System.out.println("返回日期年份:" + getYear(new Date()));
		System.out.println("返回月份：" + getMonth(new Date()));
		System.out.println("返回当天日份" + getDay(new Date()));
		System.out.println("返回当天小时" + getHour(new Date()));
		System.out.println("返回当天分" + getMinute(new Date()));
		System.out.println("返回当天秒" + getSecond(new Date()));
		System.out.println("返回当天毫秒" + getMillis(new Date()));

	}

	/**
	 * 获取当前时间
	 */
	public static String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	public static String getLongTime(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_LONG);
		return df.format(date);
	}

	/**
	 * 比较2个时间是否相等
	 * 
	 * @param original
	 * @param target
	 * @return
	 */
	public static boolean isEquals(Date original, Date target) {
		if (original == null || target == null) {
			return true;
		}
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_LONG);
        return df.format(original).equals(df.format(target));
    }

	/**
	 * 获取日期年份
	 * 
	 * @param date 日期
	 * @return
	 */
	public static String getYear(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		return df.format(date).substring(0, 4);
	}

	/**
	 * 获取日期年份
	 *
	 * @param date 日期
	 * @return
	 */
	public static String getShortYear(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		return df.format(date).substring(2, 4);
	}

	/**
	 * 获取日期年份
	 *
	 * @param date 日期
	 * @return
	 */
	public static String getMini(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_MINI);
		return df.format(date);
	}

	public static String getShortMini(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(SHORT_MINI);
		return df.format(date);
	}

	/**
	 * 功能描述：返回月
	 *
	 * @param date
	 *            Date 日期
	 * @return 返回月份
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 功能描述：返回日期
	 *
	 * @param date
	 *            Date 日期
	 * @return 返回日份
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 功能描述：返回小时
	 *
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 功能描述：返回分
	 *
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 *
	 * @param date
	 *            Date 日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 功能描述：返回毫
	 *
	 * @param date
	 *            日期
	 * @return 返回毫
	 */
	public static long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

}
