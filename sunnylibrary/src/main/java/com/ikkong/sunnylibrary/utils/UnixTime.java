package com.ikkong.sunnylibrary.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class UnixTime
{
	
	@SuppressLint("SimpleDateFormat")
	public static String unixTime2Simplese(String unixtime,String format)
	{
		Long timestamp = Long.parseLong(unixtime + "") * 1000;
		return new SimpleDateFormat(format).format(new Date(timestamp));
	}
	
	@SuppressLint("SimpleDateFormat")
	public static long simpleTime2Unix(String simpletime) 
	{
		Date date =	null;
		try
		{
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(simpletime);
		}
		catch (ParseException e)
		{
			return -1;
		}
		return date.getTime() / 1000;
	}
	
	public static String getStrCurrentUnixTime()
	{
		return  System.currentTimeMillis()/1000+"";

	}
	
	public static String getStrCurrentSimleTime()
	{
		return   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

	}

	public static String getStrCurrentSimleDate()
	{
		return   new SimpleDateFormat("yyyy-MM-dd").format(new Date());

	}
	public static String getImgNameTime()
	{
		return   new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

	}

	/**
	 * （未测试）判断当前系统时间是否在指定时间的范围内
	 *
	 * @param beginHour
	 *            开始小时，例如22
	 * @param beginMin
	 *            开始小时的分钟数，例如30
	 * @param endHour
	 *            结束小时，例如 8
	 * @param endMin
	 *            结束小时的分钟数，例如0
	 * @return true表示在范围内，否则false
	 */
	public static boolean isCurrentInTimeScope(int beginHour, int beginMin, int endHour, int endMin) {
		boolean result = false;
		final long aDayInMillis = 1000 * 60 * 60 * 24;
		final long currentTimeMillis = System.currentTimeMillis();

		GregorianCalendar now = new GregorianCalendar();
		now.setTimeInMillis(currentTimeMillis);

		GregorianCalendar startTime = new GregorianCalendar();
		startTime.setTimeInMillis(currentTimeMillis);
		startTime.set(GregorianCalendar.HOUR_OF_DAY,beginHour);
		startTime.set(GregorianCalendar.MINUTE,beginMin);

		GregorianCalendar endTime = new GregorianCalendar();
		endTime.setTimeInMillis(currentTimeMillis);
		endTime.set(GregorianCalendar.HOUR_OF_DAY,endHour);
		endTime.set(GregorianCalendar.MINUTE,endMin);

		if (!startTime.before(endTime)) {
			// 跨天的特殊情况（比如22:00-8:00）
			startTime.setTimeInMillis(startTime.getTimeInMillis() - aDayInMillis);
			result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
			GregorianCalendar startTimeInThisDay = new GregorianCalendar();
			startTimeInThisDay.setTimeInMillis(startTime.getTimeInMillis() + aDayInMillis);
			if (!now.before(startTimeInThisDay)) {
				result = true;
			}
		} else {
			// 普通情况(比如 8:00 - 14:00)
			result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
		}
		return result;
	}
	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	/**
	 * 将字符串转为日期类型
	 *
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 以友好的方式显示时间
	 *
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = null;
		if (TimeZoneUtil.isInEasternEightZones()) {
			time = toDate(sdate);
		} else {
			time = TimeZoneUtil.transformTime(toDate(sdate),
					TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
		}
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}

	/**
	 * 判断给定字符串时间是否为今日
	 *
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 返回long类型的今天的日期
	 *
	 * @return
	 */
	public static long getToday() {
		Calendar cal = Calendar.getInstance();
		String curDate = dateFormater2.get().format(cal.getTime());
		curDate = curDate.replace("-", "");
		return Long.parseLong(curDate);
	}
}
