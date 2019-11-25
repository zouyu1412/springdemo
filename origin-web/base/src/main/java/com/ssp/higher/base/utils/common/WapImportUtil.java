package com.ssp.higher.base.utils.common;

import java.util.Random;

public class WapImportUtil {

	/**
	 * 随机产生 [from ,to] 范围内随机数
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public static int getRandomInt(int from, int to) {
		return from + new Random().nextInt(to - from + 1);
	}

	public static String unifyUrl(String url) {
		if (StringUtils.isEmpty(url)) {
			return url;
		}
		url = url.replace("http:", "");
		url = url.replace("https:", "");
		if (!url.startsWith("//")) {
			url = "//" + url;
		}
		return url;
	}

}
