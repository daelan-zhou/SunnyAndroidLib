package com.ikkong.sunnylibrary.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

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
	
}
