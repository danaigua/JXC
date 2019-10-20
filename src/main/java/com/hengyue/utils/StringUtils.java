package com.hengyue.utils;

public class StringUtils {

	/**
	 * 判断是否是空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否不是空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if ((str != null) && !"".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 生成四位编号
	 * @param code
	 * @return
	 */
	public static String formatCode(String code) {
		int length = code.length();
		Integer num =  Integer.parseInt(code.substring(length - 4, length)) +1;
		String codeNum =  num.toString();
		int codeLength = codeNum.length();
		for(int i = 4; i > codeLength; i--) {
			codeNum = "0" + codeNum;
		}
		return codeNum;
	}
}
