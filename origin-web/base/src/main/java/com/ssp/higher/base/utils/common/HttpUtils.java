/**
 * Copyright 2014 Sohu.com Inc. All Rights Reserved.
 */
package com.ssp.higher.base.utils.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtils {
	// 默认失败尝试次数
	private final static int DEFAULT_RETRY_TIMES = 0;
	// 普通访问超时时间
	private final static int TIMEOUT = 1500;
	static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	/**
	 * 从指定URL获取数据，指定编码
	 * 
	 * @param url
	 * @param encoding
	 * @return
	 */
	public static String httpGet(String url, String encoding) {
		return httpGet(url, null, encoding, DEFAULT_RETRY_TIMES);
	}

	/**
	 * 从指定URL获取数据，指定编码
	 * 
	 * @param url
	 * @param encoding
	 * @param retryTimes
	 * @return
	 */
	public static String httpGet(String url, String encoding, int retryTimes) {
		return httpGet(url, null, encoding, retryTimes);
	}

	/**
	 * 从指定URL获取数据，指定编码
	 * 
	 * @param url
	 * @param referer
	 * @param encoding
	 * @return
	 */
	public static String httpGet(String url, String referer, String encoding) {
		return httpGet(url, referer, encoding, DEFAULT_RETRY_TIMES);
	}

	/**
	 * 从指定URL获取数据，指定编码
	 * 
	 * @param url
	 * @param refererer
	 * @param encoding
	 * @param useProxy
	 * @param retryTimes
	 * @return
	 */
	public static String httpGet(String url, String referer, String encoding, int retryTimes) {
		String result = StringUtils.EMPTY;
		HttpClientBuilder builder = HttpClientBuilder.create();
		HttpClient client = builder.build();

		HttpGet request = new HttpGet(url);
		try {
			// 设置请求头
			request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
			// 用逗号分隔显示可以同时接受多种编码
			request.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
			request.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");

			if (StringUtils.isNotEmpty(referer)) {
				request.setHeader("Referer", referer);
			}

			RequestConfig.Builder configBuilder = null;
			if (request.getConfig() != null) {
				configBuilder = RequestConfig.copy(request.getConfig());
			} else {
				configBuilder = RequestConfig.custom();
			}
			RequestConfig config = configBuilder.setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).build();
			request.setConfig(config);

			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == (HttpStatus.SC_OK)) {
				result = readString(entity, encoding);
			} else {
				if (response.getStatusLine().getStatusCode() == (HttpStatus.SC_NOT_FOUND)) {
					result = String.valueOf(HttpStatus.SC_NOT_FOUND);
				} else if (response.getStatusLine().getStatusCode() == (HttpStatus.SC_MOVED_TEMPORARILY)) {
					result = String.valueOf(HttpStatus.SC_MOVED_TEMPORARILY);
				} else {
					logger.info("接口[{}]读取失败，返回码[{}]，剩余重试次数[{}]", url, response.getStatusLine().getStatusCode(), retryTimes);
					if (retryTimes > 0) {
						return httpGet(url, referer, encoding, --retryTimes);
					}
				}
			}
			EntityUtils.consume(entity);
		} catch (SocketTimeoutException e) {
			logger.info("接口[{}]读取超时，剩余重试次数[{}]", new Object[] { url, retryTimes });
			if (retryTimes > 0) {
				return httpGet(url, referer, encoding, --retryTimes);
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("接口[{}]读取异常，异常信息[{}]，剩余重试次数[{}]", url, e.getMessage(), retryTimes);
			if (retryTimes > 0) {
				return httpGet(url, referer, encoding, --retryTimes);
			}

			return result;
		}

		return result;
	}

	/**
	 * 从指定URL获取数据，指定编码
	 * 
	 * @param url
	 * @param encoding
	 * @return
	 */
	public static String mobileGet(String url, String encoding) {
		return mobileGet(url, null, encoding, DEFAULT_RETRY_TIMES);
	}

	/**
	 * 从指定URL获取数据，指定编码
	 * 
	 * @param url
	 * @param encoding
	 * @param retryTimes
	 * @return
	 */
	public static String mobileGet(String url, String encoding, int retryTimes) {
		return mobileGet(url, null, encoding, retryTimes);
	}

	/**
	 * 从指定URL获取数据，指定编码
	 * 
	 * @param url
	 * @param referer
	 * @param encoding
	 * @return
	 */
	public static String mobileGet(String url, String referer, String encoding) {
		return mobileGet(url, referer, encoding, DEFAULT_RETRY_TIMES);
	}

	/**
	 * 从指定URL获取数据，指定编码
	 * 
	 * @param url
	 * @param refererer
	 * @param encoding
	 * @param useProxy
	 * @param retryTimes
	 * @return
	 */
	public static String mobileGet(String url, String referer, String encoding, int retryTimes) {
		String result = StringUtils.EMPTY;
		HttpClientBuilder builder = HttpClientBuilder.create();
		HttpClient client = builder.build();

		HttpGet request = new HttpGet(url);
		try {
			// 设置请求头
			request.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B350 Safari/8536.25");
			// 用逗号分隔显示可以同时接受多种编码
			request.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
			request.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");

			if (StringUtils.isNotEmpty(referer)) {
				request.setHeader("Referer", referer);
			}

			RequestConfig.Builder configBuilder = null;
			if (request.getConfig() != null) {
				configBuilder = RequestConfig.copy(request.getConfig());
			} else {
				configBuilder = RequestConfig.custom();
			}
			RequestConfig config = configBuilder.setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).build();
			request.setConfig(config);

			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == (HttpStatus.SC_OK)) {
				result = readString(entity, encoding);
			} else {
				if (response.getStatusLine().getStatusCode() == (HttpStatus.SC_NOT_FOUND)) {
					result = String.valueOf(HttpStatus.SC_NOT_FOUND);
				} else if (response.getStatusLine().getStatusCode() == (HttpStatus.SC_MOVED_TEMPORARILY)) {
					result = String.valueOf(HttpStatus.SC_MOVED_TEMPORARILY);
				} else {
					logger.info("接口[{}]读取失败，返回码[{}]，剩余重试次数[{}]", url, response.getStatusLine().getStatusCode(), retryTimes);
					if (retryTimes > 0) {
						return mobileGet(url, referer, encoding, --retryTimes);
					}
				}
			}
			EntityUtils.consume(entity);
		} catch (SocketTimeoutException e) {
			logger.info("接口[{}]读取超时，剩余重试次数[{}]", new Object[] { url, retryTimes });
			if (retryTimes > 0) {
				return mobileGet(url, referer, encoding, --retryTimes);
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("接口[{}]读取异常，异常信息[{}]，剩余重试次数[{}]", url, e.getMessage(), retryTimes);
			if (retryTimes > 0) {
				return mobileGet(url, referer, encoding, --retryTimes);
			}

			return result;
		}

		return result;
	}

	/**
	 * 向指定URL提交数据，指定编码
	 * 
	 * @param url
	 * @param data
	 * @param encoding
	 * @return
	 */
	public static String httpPost(String url, Map<String, String> data, String encoding) {
		return httpPost(url, null, data, encoding, DEFAULT_RETRY_TIMES);
	}

	/**
	 * 向指定URL提交数据，指定编码
	 * 
	 * @param url
	 * @param data
	 * @param encoding
	 * @param retryTimes
	 * @return
	 */
	public static String httpPost(String url, Map<String, String> data, String encoding, int retryTimes) {
		return httpPost(url, null, data, encoding, retryTimes);
	}

	/**
	 * 向指定URL提交数据，指定编码
	 * 
	 * @param url
	 * @param referer
	 * @param data
	 * @param encoding
	 * @return
	 */
	public static String httpPost(String url, String referer, Map<String, String> data, String encoding) {
		return httpPost(url, referer, data, encoding, DEFAULT_RETRY_TIMES);
	}

	/**
	 * 向指定URL提交数据，指定编码
	 * 
	 * @param url
	 * @param refererer
	 * @param data
	 * @param encoding
	 * @param retryTimes
	 * @return
	 */
	public static String httpPost(String url, String referer, Map<String, String> data, String encoding, int retryTimes) {
		String result = StringUtils.EMPTY;
		HttpClientBuilder builder = HttpClientBuilder.create();
		HttpClient client = builder.build();

		HttpPost request = new HttpPost(url);
		try {
			// 设置请求头
			request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
			// 用逗号分隔显示可以同时接受多种编码
			request.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
			request.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");

			if (StringUtils.isNotEmpty(referer)) {
				request.setHeader("Referer", referer);
			}

			// 封装提交参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			Set<String> keySet = data.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, data.get(key)));
			}
			request.setEntity(new UrlEncodedFormEntity(nvps, encoding));

			RequestConfig.Builder configBuilder = null;
			if (request.getConfig() != null) {
				configBuilder = RequestConfig.copy(request.getConfig());
			} else {
				configBuilder = RequestConfig.custom();
			}
			RequestConfig config = configBuilder.setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).build();
			request.setConfig(config);

			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == (HttpStatus.SC_OK)) {
				result = readString(entity, encoding);
			} else {
				if (response.getStatusLine().getStatusCode() == (HttpStatus.SC_NOT_FOUND)) {
					result = String.valueOf(HttpStatus.SC_NOT_FOUND);
				} else if (response.getStatusLine().getStatusCode() == (HttpStatus.SC_MOVED_TEMPORARILY)) {
					result = String.valueOf(HttpStatus.SC_MOVED_TEMPORARILY);
				} else {
					logger.info("接口[{}]读取失败，返回码[{}]，剩余重试次数[{}]", url, response.getStatusLine().getStatusCode(), retryTimes);
					if (retryTimes > 0) {
						return httpPost(url, referer, data, encoding, --retryTimes);
					}
				}
			}
			EntityUtils.consume(entity);
		} catch (SocketTimeoutException e) {
			logger.info("接口[{}]读取超时，剩余重试次数[{}]", new Object[] { url, retryTimes });
			if (retryTimes > 0) {
				return httpPost(url, referer, data, encoding, --retryTimes);
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("接口[{}]读取异常，异常信息[{}]，剩余重试次数[{}]", url, e.getMessage(), retryTimes);
			if (retryTimes > 0) {
				return httpPost(url, referer, data, encoding, --retryTimes);
			}

			return result;
		}

		return result;
	}

	/**
	 * 从InputStream读取文字，指定编码
	 * 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	private static String readString(HttpEntity entity, String encoding) throws Exception {
		StringBuilder sb = new StringBuilder();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), encoding));
		String lineMessage;
		while ((lineMessage = bufferedReader.readLine()) != null) {
			sb.append(lineMessage);
			sb.append("\r\n");
		}
		bufferedReader.close();

		return sb.toString();
	}

	/**
	 * 从指定URL下载数据
	 * 
	 * @param url
	 * @return
	 */
	public static byte[] httpDownload(String url) {
		return httpDownload(url, null, DEFAULT_RETRY_TIMES);
	}

	/**
	 * 从指定URL下载数据
	 * 
	 * @param url
	 * @param retryTimes
	 * @return
	 */
	public static byte[] httpDownload(String url, int retryTimes) {
		return httpDownload(url, null, retryTimes);
	}

	/**
	 * 从指定URL下载数据
	 * 
	 * @param url
	 * @param referer
	 * @return
	 */
	public static byte[] httpDownload(String url, String referer) {
		return httpDownload(url, referer, DEFAULT_RETRY_TIMES);
	}

	/**
	 * 从指定URL下载数据
	 * 
	 * @param url
	 * @param refererer
	 * @param useProxy
	 * @param retryTimes
	 * @return
	 */
	public static byte[] httpDownload(String url, String referer, int retryTimes) {
		byte[] result = null;
		HttpClientBuilder builder = HttpClientBuilder.create();
		HttpClient client = builder.build();

		HttpGet request = new HttpGet(url);
		try {
			// 设置请求头
			request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");

			// 用逗号分隔显示可以同时接受多种编码
			request.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
			request.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");

			if (StringUtils.isNotEmpty(referer)) {
				request.setHeader("Referer", referer);
			}

			RequestConfig.Builder configBuilder = null;
			if (request.getConfig() != null) {
				configBuilder = RequestConfig.copy(request.getConfig());
			} else {
				configBuilder = RequestConfig.custom();
			}
			RequestConfig config = configBuilder.setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).build();
			request.setConfig(config);

			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == (HttpStatus.SC_OK)) {
				InputStream in = response.getEntity().getContent();
				result = readByteArray(in);
			} else {
				if (response.getStatusLine().getStatusCode() == (HttpStatus.SC_NOT_FOUND)) {
					result = null;
				} else if (response.getStatusLine().getStatusCode() == (HttpStatus.SC_MOVED_TEMPORARILY)) {
					result = null;
				} else {
					logger.info("URL[{}] Download Failed, Return Code[{}], Remain Retry Count[{}]", url, response.getStatusLine().getStatusCode(), retryTimes);
					if (retryTimes > 0) {
						return httpDownload(url, referer, --retryTimes);
					}
				}
			}
			EntityUtils.consume(entity);
		} catch (SocketTimeoutException e) {
			logger.info("URL[{}] Download Timeout, Remain Retry Count[{}]", new Object[] { url, retryTimes });
			if (retryTimes > 0) {
				return httpDownload(url, referer, --retryTimes);
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("URL[{}] Download Error, Error Message[{}], Remain Retry Count[{}]", url, e.getMessage(), retryTimes);
			if (retryTimes > 0) {
				return httpDownload(url, referer, --retryTimes);
			}

			return result;
		}

		return result;
	}

	/**
	 * 从InputStream读取文字，指定编码
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	private static byte[] readByteArray(InputStream in) throws Exception {
		byte[] data = new byte[1024];
		int length = 0;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		while ((length = in.read(data)) != -1) {
			bout.write(data, 0, length);
		}
		return bout.toByteArray();
	}
}
