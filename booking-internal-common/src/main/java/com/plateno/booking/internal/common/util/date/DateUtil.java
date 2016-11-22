package com.plateno.booking.internal.common.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	
	private DateUtil() {
	}

	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
	
	//大写H代表24小时制的小时，小写h代表12小时制的小时；小写的m代表分钟，大写的M代表月份！
	public static final String YY_MM_DD = "yy-MM-dd";
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_year_MM_month_DD_day = "yyyy年MM月dd日";
	public static final String MM_DD = "MM月dd日";
	public static final String HH = "HH时";
	public static final String DD = "dd";
	public static final long ONE_DAY = 1000*60*60*24;

	
	/**
	 * 将日期转成字符串
	 * @param date 日期
	 * @param formatStr 格式
	 * @return 日期字符串
	 */
	public static String dateToFormatStr(Date date, String formatStr){
		if (date==null) {
			return "Date is null.";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}
	
	/**
	 * 将日期转成字符串
	 * @param date 日期
	 * @param formatStr 格式
	 * @return 日期字符串
	 */
	public static String dateToFormatStr(long date, String formatStr) {
		Date d = new Date(date);
		return dateToFormatStr(d,formatStr);
	}

	/**
	 * 将字符串转为日期
	 * @return  date,如果抛错,返回 null
	 * @throws ParseException 
	 */
	public static Date dateStrToDate(String dateStr,String formatStr){
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			log.info("日期格式转换失败!");
		}
		return date;
	}
	
	/**
	 * 将字符串转为日期
	 * @return  date,如果抛错,返回 null
	 * @throws ParseException 
	 */
	public static Date dateLongToDate(Long dateLong,String formatStr){
		Date date = null;
		try {
			 date = new Date(dateLong);
		} catch (Exception e) {
			log.info("日期格式转换失败!");
		}
		return date;
	}
	
	public static Date addOrMinusYear(String time, int i) throws Exception {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		time = time + " 00:00:00";
		Date date = dateStrToDate(time, YYYY_MM_DD_HH_MM_SS);
		cal.setTime(date);
		cal.add(Calendar.MINUTE, i);
		rtn = cal.getTime();
		return rtn;
	}
	
	public static Date addOrMinusYear(Date time, int i) throws Exception {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(time);
		cal.add(Calendar.MINUTE, i);
		rtn = cal.getTime();
		return rtn;
	}

	/**
	 * 将日期字符串转成long
	 * @param dateStr 日期字符
	 * @param formatStr 格式
	 * @return
	 */
	public static long dateToFormatStr(String dateStr,String formatStr){
		Date date = dateStrToDate(dateStr,formatStr);
		return date.getTime();
	}

	/**
	 * 将 long 转成 date
	 * @param date
	 * @return
	 */
	public static Date longToDate(long date){
		return new Date(date); 
	}
	
	/**
	 * 将一个带有时分秒参数的日期类对象裁剪为只包含年月日的值而忽略时分秒
	 * @param specDate 传入的时间参数，如果为null则去当前系统时刻
	 * @return 只精确到年月日的日期对象，其时分秒的值为00:00:00
	 * @throws ParseException 如果传入的日期参数错误
	 */
	public static Date cutDatetimeToDay(Date specDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(specDate);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date cutDatetimeToDay(long date){
		Date d = new Date(date);
		return cutDatetimeToDay(d);
	}

	/**
	 * 将类似于"9999-12-31"的时间转为null
	 * @param date
	 * @return
	 */
	public static Date castDate(Date date){
		final long DAY_2100_12_31 = 4133865600000L;
		if(date != null && date.getTime() < DAY_2100_12_31){
			return date;
		}else{
			return null;
		}
	}
	
	public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		XMLGregorianCalendar gc = null;
		try {
			gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gc;
	}

	public static Date convertToDate(XMLGregorianCalendar cal){
		if (cal == null) {
			return null;
		}
		GregorianCalendar ca = cal.toGregorianCalendar();
		return ca.getTime();
	}

	/**
	 * 计算两个日期之间间隔天数
	 * @param begin
	 * @param end
	 * @return 间隔天数
	 */
	public static int calculateDayLength(long begin,long end){
		return (int)((end - begin) / (1000 * 60 * 60 *24) + 0.5); 
	}
	
	/**
	 * 计算两个日期之间间隔天数
	 * @param begin
	 * @param end
	 * @return 间隔天数
	 */
	public static int calculateDayLength(Date begin,Date end){
		return calculateDayLength(begin.getTime(), end.getTime());
	}
	

	/**
	 * 日期加天数
	 * @param begin 日期
	 * @param add 天数
	 * @return long
	 */
	public static long addDay(long begin,int add){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(begin);
		calendar.add(Calendar.DAY_OF_YEAR,add);
		return calendar.getTimeInMillis();
	}
	
	/**
	 * 日期加天数
	 * @param begin 日期
	 * @param add 天数
	 * @return long
	 */
	public static long addMin(long begin,int add){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(begin);
		calendar.add(Calendar.MINUTE,add);
		return calendar.getTimeInMillis();
	}
	
	/**
	 * 日期加天数
	 * @param begin 日期
	 * @param add 天数
	 * @return Date
	 */
	public static Date addDay(Date begin,int add){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(begin);
		calendar.add(Calendar.DAY_OF_YEAR,add);
		return calendar.getTime();
	}

	/**
	 * 日期加天数
	 * @param begin 日期
	 * @param add 天数
	 * @return Date
	 */
	public static Date addDay(Date begin,long add){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(begin);
		calendar.add(Calendar.DAY_OF_YEAR,(int)add);
		return calendar.getTime();
	}
	
	/**
	 * 增加month,返回 long
	 * @param pdate
	 * @param num
	 * @return long
	 */
	public static long addMonth(Date pdate, int num) {
		return addMonthRDate(pdate,num).getTime();
	}
	
	/**
	 * 增加month,返回 date
	 * @param pdate
	 * @param num
	 * @return Date
	 */
	public static Date addMonthRDate(Date pdate, int num){
		return addTimeRDate(pdate, num, Calendar.MONTH);
	}
	
	/**
	 * 改变 date 的 时间 
	 * @param pdate
	 * @param num
	 * @param calendarStatic  {@link Calendar} Calendar.MONTH 这些 
	 * @return 返回 date
	 */
	public static Date addTimeRDate(Date pdate, int num,int calendarStatic){
		Calendar calender = Calendar.getInstance();
		calender.setTime(pdate);
		calender.add(calendarStatic, num);
		return calender.getTime();
	}


	public boolean checkBeforeDay(Date date, int day, int hour) {
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);// 入住日期
		c.add(Calendar.DATE, -day);// 判断提前day天
		c.set(Calendar.HOUR_OF_DAY, hour);
        return now.after(c.getTime());
    }
	
	//判断是不是同一天
	public static boolean isSameDate(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		
		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth&& cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
		
		return isSameDate;
	}
	
	/**
     * 获取指定格式的字符串
     * @param date
     * @param intervalDate  偏移天数  + 或 -
     * @param format  格式
     * @return
     */
    public static Date getDate(Date date, int intervalDay, int intervalHour, int intervalMin, int intervalSec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, intervalDay);
        calendar.add(Calendar.HOUR, intervalHour);
        calendar.add(Calendar.MINUTE, intervalMin);
        calendar.add(Calendar.SECOND, intervalSec);
        return calendar.getTime();
    }
	
	
	public static void main(String args[]){
		Long begin = new Date().getTime();
		Long end   = DateUtil.dateToFormatStr("2016-08-22 19:00:00", DateUtil.YYYY_MM_DD_HH_MM_SS);
		System.out.println("开始时间：" + begin + "结束时间：" + end);
		System.out.println((begin -end) > 60 * 20 * 1000);
	}
	
}
