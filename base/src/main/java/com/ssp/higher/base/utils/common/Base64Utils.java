package com.ssp.higher.base.utils.common;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public final class Base64Utils {

	private static final Base64 BASE64 = new Base64();
	private static Logger logger = LoggerFactory.getLogger(Base64Utils.class);

	/**
	 * 解码
	 * 
	 * @param text
	 * @return
	 */
	public static String decodeToString(String text) {
		if (text == null) {
			return null;
		}
		try {
			return new String(BASE64.decode(text), StandardCharsets.UTF_8);
		} catch (Exception e) {
			logger.error("Base64Utils decode error", e);
		}
		return text;
	}

	/**
	 * 解码
	 *
	 * @param text
	 * @return
	 */
	public static byte[] decode(String text) {
		if (text == null) {
			return null;
		}
		try {
			return BASE64.decode(text);
		} catch (Exception e) {
			logger.error("Base64Utils decode error", e);
		}
		return null;
	}

	/**
	 * 编码
	 * 
	 * @param text
	 * @return
	 */
	public static String encode(String text) {
		if (text == null) {
			return null;
		}
		try {
			final byte[] textByte = text.getBytes(StandardCharsets.UTF_8);
			// 编码
			return BASE64.encodeToString(textByte);
		} catch (Exception e) {
			logger.error("Base64Utils decode error", e);
		}
		return text;

	}
}
