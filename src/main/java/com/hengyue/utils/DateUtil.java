package com.hengyue.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期工具类
 * @author 章家宝
 *
 */
public class DateUtil {

	/**
	 * 获取当前年月日时间段字符串
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentDateStr() throws Exception{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}
}
