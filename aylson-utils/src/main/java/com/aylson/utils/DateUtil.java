package com.aylson.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * 
 */
public class DateUtil {

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @return 日期字符串
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @return 日期字符串
	 */
	public static String format(Date date, boolean isOnlyDate) {
		if (isOnlyDate) {
			return format(date, "yyyy-MM-dd");
		} else {
			return format(date, "yyyy-MM-dd HH:mm:ss");
		}
	}

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @param pattern
	 *            字符串格式
	 * @return 日期字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "null";
		}
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return new java.text.SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @return 日期类型
	 */
	public static Date format(String date) {
		return format(date, null);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @param pattern
	 *            格式
	 * @return 日期类型
	 */
	public static Date format(String date, String pattern) {
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (date == null || date.equals("") || date.equals("null")) {
			return new Date();
		}
		Date d = null;
		try {
			d = new java.text.SimpleDateFormat(pattern).parse(date);
		} catch (ParseException pe) {
		}
		return d;
	}

	/**
	 * 计算两个日期间的天数
	 * 
	 * @param fromDate
	 *            起始日期
	 * @param toDate
	 *            结束日期
	 * @return
	 * @throws ParseException
	 */
	public static int dateDiff(Date fromDate, Date toDate) {
		int days = 0;
		days = (int) Math.abs((toDate.getTime() - fromDate.getTime())
				/ (24 * 60 * 60 * 1000)) + 1;
		return days;
	}

	/**
	 * 计算两个日期间的时间差(秒)
	 * 
	 * @param fromDate
	 *            起始日期
	 * @param toDate
	 *            结束日期
	 * @return
	 * @throws ParseException
	 */
	public static int timeDiff(Date fromDate, Date toDate) {
		int minutes = 0;
		minutes = (int) Math
				.abs((toDate.getTime() - fromDate.getTime()) / 1000);
		return minutes;
	}

	/**
	 * 判断一个时间是否今天的时间
	 * 
	 * @param fromDate
	 * @return
	 */
	public static boolean isToday(Date fromDate) {
		Calendar current = Calendar.getInstance();

		Calendar today = Calendar.getInstance(); // 今天
		// Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);

		current.setTime(fromDate);
		if (current.after(today)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断一个时间是否昨天的时间
	 * 
	 * @param fromDate
	 * @return
	 */
	public static boolean isYesterday(Date fromDate) {
		Calendar current = Calendar.getInstance();

		Calendar today = Calendar.getInstance(); // 今天

		// Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);

		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_MONTH, -1); // 减一天
		// Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		yesterday.set(Calendar.HOUR_OF_DAY, 0);
		yesterday.set(Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);

		current.setTime(fromDate);
		if (current.after(yesterday) && current.before(today)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 当前日期添加几个月
	 * @param month
	 * @return
	 */
	public static Date addMonth(int addMonth){
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH,addMonth);
		return now.getTime();
	}
	
	/**
	 * 计算两个日期间的时间差(秒)
	 * 
	 * @param fromDate
	 *            起始日期
	 * @param toDate
	 *            结束日期
	 * @return 
	 * @throws ParseException
	 */
	public static int compareDate(Date fromDate, Date toDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
		String s1 = df.format(fromDate);  
		String s2 = df.format(toDate);  
		return s1.compareTo(s2);
	}
	
	public static String subMonth(Date date, int Month) throws ParseException {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar rightNow = Calendar.getInstance();  
        rightNow.setTime(date);  
        rightNow.add(Calendar.MONTH, Month);  
        Date dt1 = rightNow.getTime();  
        String reStr = sdf.format(dt1);  
        return reStr;  
    }  
	
	/**
	 * 按月加减时间
	 * @param date
	 * @param month
	 * @return
	 * @throws ParseException
	 */
	public static Date operMonth(Date date,int month) {
		 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	        Calendar rightNow = Calendar.getInstance();  
	        rightNow.setTime(date);  
	        rightNow.add(Calendar.MONTH, month);  
	        Date dt1 = rightNow.getTime();
	        return dt1;
	}
	
	/**
	 * 按天加减时间
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date operDay(Date date,int day) {
	 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar rightNow = Calendar.getInstance();  
        rightNow.setTime(date);  
        rightNow.add(Calendar.DAY_OF_YEAR, day);  
        Date dt1 = rightNow.getTime();
        return dt1;
	}
	
	public static void main(String[] args) throws ParseException {
		String a = DateUtil.format(operMonth(new Date(),-1));
		System.out.println(a);
		String b = DateUtil.format(operDay(new Date(),-8),true);
		System.out.println(b);
	}
	
	
}
