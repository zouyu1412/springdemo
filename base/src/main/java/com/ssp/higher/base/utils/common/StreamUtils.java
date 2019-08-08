package com.ssp.higher.base.utils.common;

import java.util.ArrayList;
import java.util.List;

public class StreamUtils {

	/**
	 * 将含分隔符的字符串根据分隔符切开，返回list
	 * 
	 * @param str
	 * @param separator 分隔符
	 * @return
	 */
	public static List<String> stringToList(String str, String separator) {
		List<String> strList = new ArrayList<>();
		// 输入校验
		if (str == null || StringUtils.EMPTY.equals(str)) {
			return strList;
		}
		if (separator == null || StringUtils.EMPTY.equals(separator)) {
			strList.add(str);
		}
		// 切分字符串，转为list
		String[] strArray = org.springframework.util.StringUtils.tokenizeToStringArray(str, separator);
		for (int i = 0; i < strArray.length; i++) {
			strList.add(strArray[i]);
		}
		return strList;
	}

	/**
	 * 将list中的字符串拼接，各个字符串之间使用分隔符切分
	 * 
	 * @param strList
	 * @param separator
	 * @return
	 */
	public static String listToString(List<String> strList, String separator) {
		// 输入校验
		if (strList == null || strList.size() == 0) {
			return StringUtils.EMPTY;
		}
		if (separator == null) {
			separator = StringUtils.EMPTY;
		}
		// 拼接list中的字符串，用分隔符切分
		StringBuilder sb = new StringBuilder();
		for (String str : strList) {
			if (!StringUtils.isEmpty(str)) { // 跳过空字符串
				sb.append(str).append(separator);
			}
		}
		if (sb.length() == 0) {
			return StringUtils.EMPTY;
		}
		return sb.substring(0, sb.length() - 1);
	}

}
