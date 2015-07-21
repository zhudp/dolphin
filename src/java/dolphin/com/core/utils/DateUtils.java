package com.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

/**
 * 日期操作工具类
 *
 *
 * @author: wanglf
 * @since: Dec 19, 2007 1:42:49 PM
 */
public class DateUtils {
	
	public static final String Simple_Date_Format = "yyyy-MM-dd";
	
	/** 日期格式：YYYYMMDD */
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	
	private static final int Simple_Date_Format_Length = Simple_Date_Format
			.length();
	public static final String Simple_DateTime_Format = "yyyy-MM-dd HH:mm:ss";
	
	public static final String SeqNo_DateTime_Format = "yyyyMMddHHmmss";

	public static java.util.Date stringToUtilDate(String str) {
		str = str.replaceAll("/", "-");
		if (null != str && str.length() > 0) {
			try {
				if (str.length() <= Simple_Date_Format_Length) {
					return (new SimpleDateFormat(Simple_Date_Format))
							.parse(str);
				} else {
					return (new SimpleDateFormat(Simple_DateTime_Format))
							.parse(str);
				}
			} catch (ParseException ex) {
				return null;
			}
		} else
			return null;
	}

	public static java.sql.Date stringToSqlDate(String str) {
		if (stringToUtilDate(str) == null || str.length() < 1)
			return null;
		else
			return new java.sql.Date(stringToUtilDate(str).getTime());
	}

	public static String toDateTimeString(java.sql.Date d) {
		if (d == null) {
			return null;
		} else {
			return (new SimpleDateFormat(Simple_DateTime_Format)).format(d);
		}
	}

	public static String toDateTimeString(java.util.Date d) {
		if (d == null) {
			return null;
		} else {
			return (new SimpleDateFormat(Simple_DateTime_Format)).format(d);
		}
	}

	public static String toDateString(java.sql.Date d) {
		if (d == null) {
			return null;
		} else {
			return (new SimpleDateFormat(Simple_Date_Format)).format(d);
		}
	}

	public static String toDateString(java.util.Date d) {
		if (d == null) {
			return null;
		} else {
			return (new SimpleDateFormat(Simple_Date_Format)).format(d);
		}
	}

	public static Date getCurrentDate() {
		return new java.util.Date();
	}

	public static Calendar getNow() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		return calendar;
	}
	
	/**
	 * 获得传入日期的下days天
	 * @param date 日期
	 * @param dayss 天数
	 * @return 
	 * @description：
	 * 
	 */
	public static Date getNextDate(Date date, int dayss) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, dayss);
		return new Date(calendar.getTime().getTime());
	}
	
  	/**
  	 * 将日期转换为指定格式的字符串
  	 * @param date
  	 * @param dateFormat
  	 * @return
  	 */
  	public static String toDateString(Date date,String dateFormat){
		if(date!=null){
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			String dateString = format.format(date);
			return dateString;
		}
		return null;
  	}
  	

  	/**
  	 * 日期是否是今天
  	 * @param date
  	 * @return 
  	 * @create  2010-10-15 下午05:28:26 zhudp
  	 * @history
  	 */
  	public static boolean isToday(Date date){
  		String today = new SimpleDateFormat("yyyyMMdd").format(getCurrentDate());
  		String strDate = new SimpleDateFormat("yyyyMMdd").format(date);
  		
		return today.equals(strDate);
  	}
  	
  	
  	/**
  	 * 日期是不是当前月
  	 * @param date
  	 * @return 
  	 * @create  2010-10-15 下午05:28:26 zhudp
  	 * @history
  	 */
  	public static boolean isThisMonth(Date date){
  		String today = new SimpleDateFormat("yyyyMM").format(getCurrentDate());
  		String strDate = new SimpleDateFormat("yyyyMM").format(date);
  		
		return today.equals(strDate);
  	}
  	
  	
  	
  	
  	
  	/**
  	 * 判断(startDate1-endDate1)与(startDate2-endDate2)两个时间段是否存在交集。
  	 * @param startDate1
  	 * @param endDate1
  	 * @param startDate2
  	 * @param endDate2
  	 * @return true:无交集; false:有交集
  	 * @create  2010-10-15 上午09:27:31 zhudp
  	 * @history
  	 */
  	public static boolean noCross(Date startDate1, Date endDate1, Date startDate2, Date endDate2){
		Assert.isTrue(endDate1.compareTo(startDate1) >= 0);
		Assert.isTrue(endDate2.compareTo(startDate2) >= 0);
		return endDate1.before(startDate2) || startDate1.after(endDate2);
  	}
  	
  	/**
  	 * 获取两个日期之间的所有日期List
  	 * @param beginDate		开始日期
  	 * @param endDate		结束日期
  	 * @param includeBegin	包括开始日期
  	 * @param includeEnd	包括结束日期
  	 * @return 
  	 * @create  2010-10-23 下午01:08:28 zhudp
  	 * @history
  	 */
  	public static List<Date> getBetweenDateList(Date beginDate, Date endDate, boolean includeBegin,boolean includeEnd) {
  	
  		List<Date> dateList = new ArrayList<Date>();
  		
  		if(includeBegin) {
  			dateList.add(beginDate);
  		}
  		Date date = getNextDate(beginDate, 1);
  		while(date.before(endDate)) {
  			dateList.add(date);
  			date = getNextDate(date, 1);
  		};
  		
  		if(includeEnd && beginDate.compareTo(endDate) < 0) {
  			dateList.add(endDate);
  		}
  		
  		return dateList;
  	}
  	
  	/**
  	 * 获取两个日期之间的所有日期List
  	 * @param start		开始日期
  	 * @param end		结束日期
  	 * @param includeBegin	包括开始日期
  	 * @param includeEnd	包括结束日期
  	 * @return 
  	 * @create  2010-10-23 下午01:08:28 zhudp
  	 * @history
  	 */
  	public static List<String> getBetweenMonthList(String start, String end, boolean includeBegin,boolean includeEnd) {
  		
		String splitSign = "-";

		List<String> list = new ArrayList<String>();
		if (start.compareTo(end) > 0) {
			String temp = start;
			start = end;
			end = temp;
		}

		String temp = start;
		while (temp.compareTo(start) >= 0 && temp.compareTo(end) < 0) {
			list.add(temp);
			String[] arr = temp.split(splitSign);
			int year = Integer.valueOf(arr[0]);
			int month = Integer.valueOf(arr[1]) + 1;
			if (month > 12) {
				month = 1;
				year++;
			}
			if (month < 10) {
				temp = year + splitSign + "0" + month;
			} else {
				temp = year + splitSign + month;
			}
		}

		if (!includeBegin) {
			list.remove(0);
		}
		if (includeEnd) {
			list.add(end);
		}
		return list;
  	}
  	/**
  	 * 获取两个年份之间的所有日期List
  	 * @return 
  	 * @create  2012-06-27 下午01:08:28 gaowx
  	 * @history
  	 */
  	public static List<String> getBetweenYearList(String start, String end, boolean includeBegin,boolean includeEnd) {
  		
		List<String> list = new ArrayList<String>();
		if (start.compareTo(end) > 0) {
			String temp = start;
			start = end;
			end = temp;
		}

		String temp = start;
		while (temp.compareTo(start) >= 0 && temp.compareTo(end) < 0) {
			list.add(temp);
			int year = Integer.valueOf(temp);
			temp = String.valueOf(++year);
		}

		if (!includeBegin) {
			list.remove(0);
		}
		if (includeEnd) {
			list.add(end);
		}
		return list;
  	}
  	
  	/**
  	 * 获取月有几天
  	 * @param month
  	 * @return
  	 */
  	public static int getMonthHasDays(Date month){
  		Calendar calendar = new GregorianCalendar();
  		calendar.setTime(month); 
  		
  		int day;
  		if(isThisMonth(month)){
  			day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
  		}else{
  			day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
  		}
  		
  		return day;
  	}
  	
  	/**
  	 * 获取下一年的今天
  	 * @return
  	 */
  	public static Date getNextYearToday() {
		Date dat = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dat);
		
		cal.add(cal.YEAR,1);//用Calendar对年加1,绕开判断闰年
		dat = cal.getTime();//还原为DATE
		
		return dat;
	}

  	
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(toDateString(getNextYearToday()));
	}

}
