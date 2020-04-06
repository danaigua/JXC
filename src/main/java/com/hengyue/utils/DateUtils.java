package com.hengyue.utils;
/**
 * 日期工具类
 * @author 章家宝
 *
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DateUtils {

	private DateUtils() {
	}

	public static DateUtils build() {
		return new DateUtils();
	}

	/**
	 * 字符串转化为日期对象
	 * @param str
	 * @param format
	 * @return
	 * @throws ZsException
	 */
	public static Date string2Date(String str, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (StringUtils.isNotEmpty(str)) {
			try {
				return sdf.parse(str);
			} catch (ParseException e) {
				System.out.println(str);
				System.out.println(format);
				System.out.println(e.getMessage());
				throw new Exception("数据格式化异常");
			}
		} else {
			return null;
		}
	}
	/**
	 * 把日期对象格式化为字符串
	 * @param date
	 * @param format
	 * @return
	 * @throws ZsException
	 */
	public static String date2String(Date date, String format) throws Exception{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}catch(Exception e) {
			throw new Exception("日期格式化异常");
		}
	}
	/**
	 * 获取近一年的月份
	 * @return
	 * @throws Exception
	 */
	public static List<String> getOneYearMonth(String format) throws Exception{
		List<String> result = new ArrayList<String>();
		Calendar cd = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		cd.setTime(date);
		
		for(int i = 11; i > 0; i--) {
			if(i == 11) {
				cd.add(Calendar.MONTH, -12);
			}
			cd.add(Calendar.MONTH, +1);
			result.add(date2String(cd.getTime(), format));
		}
		result.add(sdf.format(date));
		return result;
 	}
	/**
	 * 获取近13的月份
	 * @return
	 * @throws Exception
	 */
	public static List<String> getOneYearAddOneMonth(String format) throws Exception{
		List<String> result = new ArrayList<String>();
		Calendar cd = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		cd.setTime(date);
		
		for(int i = 12; i > 0; i--) {
			if(i == 12) {
				cd.add(Calendar.MONTH, -12);
			}
			cd.add(Calendar.MONTH, +1);
			result.add(date2String(cd.getTime(), format));
		}
		result.add(sdf.format(date));
		return result;
 	}
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		return sdf.format(date).toString();
	}
	/**
	 * 获取当前年份
	 * @return
	 * @throws ParseException
	 */
	public static Date getCurretYear() throws ParseException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.parse(sdf.format(date).toString());
	}
	/**
	 * 上一年的年份
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastYear() throws ParseException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.parse((Integer.parseInt(sdf.format(date).toString()) - 1) + "");
	}
	/**
	 * 获取输入字符串str的上一年
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Date getLastYearByString(String str) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.parse(Integer.parseInt(str) - 1 + "");
	}
	/**
	 * 获取输入字符串str的下一年
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Date getNextYearByString(String str) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.parse(Integer.parseInt(str) + 1 + "");
	}
	/**
	 * 查看是否周末，因为周末算加班
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static boolean isWeekend(Date date) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int i = cal.get(Calendar.DAY_OF_WEEK);
		if(i == 1) {
			return false;
		}else if(i == 7) {
			return false;
		}else {
			return true;
		}
		
	}
	/**
	 * 获取当前的月份
	 * @return
	 * @throws Exception
	 */
	public static Date getThisMonthsFirstDay() throws Exception{
		Calendar firstCalendar = Calendar.getInstance();
		firstCalendar.setTime(new Date());
		firstCalendar.set(Calendar.DAY_OF_MONTH,1);
		firstCalendar.add(Calendar.MONTH,0);
		Date firstDay = firstCalendar.getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String firstDayString = simpleDateFormat.format(firstDay);
		return simpleDateFormat.parse(firstDayString); 
	}
	/**
	 * 获取今年的第一天
	 * @return
	 * @throws Exception
	 */
	public static Date getThisYearFirstDay() throws Exception{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String thisYear = sdf.format(date).toString();
		return simpleDateFormat.parse(thisYear + "-01-01");
	}
	/**
	 * 获取当前月份的月份数字
	 * @param args
	 * @throws Exception
	 */
	public static int getThisMonthNumber() throws Exception{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
		String date = simpleDateFormat.format(new Date());
		return Integer.parseInt(date);
	}
	/**
	 * 获取某一个月份的第一天
	 * @param mouth
	 * @return
	 * @throws Exception
	 */
	public static Date getOneMonthFitstDayFromNumber(int mouth) throws Exception{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
		String year = formatYear.format(getCurretYear());
		return simpleDateFormat.parse(year + "-" + mouth + "-01 00:00:00");
	}
	/**
	 * 获取某一个月份的第一天
	 * @param mouth
	 * @return
	 * @throws Exception
	 */
	public static Date getOneMonthLastDayFromNumber(int mouth) throws Exception{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
		String year = formatYear.format(getCurretYear());
		mouth += 1;
		return simpleDateFormat.parse(year + "-" + mouth + "-01 00:00:00");
	}
	/**
	 * 获取近两年的年份数字
	 * @param mouth
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Integer> getCurrentTwoYear() throws Exception{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String dateString = simpleDateFormat.format(date);
		int dateNum = Integer.parseInt(dateString);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("currentYear", dateNum);
		map.put("nextYear", dateNum + 1);
		return map;
	}
	public static void main(String[] args) throws Exception {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//		System.out.println(sdf.format(getOneMonthFitstDayFromNumber(10)));
//		System.out.println(sdf.format(getOneMonthLastDayFromNumber(10)));
		System.out.println(getThisMonthNumber());
	}
}
