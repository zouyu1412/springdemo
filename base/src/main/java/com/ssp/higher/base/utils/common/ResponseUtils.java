package com.ssp.higher.base.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseUtils {
	private static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

	public static void write2PageUTF8Json(Object message, HttpServletResponse response) {
		String json = FastJsonUtils.toJSON(message);
		write2PageUTF8Json(json, response);
	}

	public static void write2PageUTF8(String message, HttpServletResponse response) {
		PrintWriter out = null;
		if (message == null) {
			message = "";
		}
		try {
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache", "no-cache");
			out = response.getWriter();
			out.print(message);
			out.flush();
		} catch (Exception e) {
			logger.error("write2PageUTF8 ", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static void write2PageUTF8Json(String message, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache", "no-cache");
			out = response.getWriter();
			out.print(message);
			out.flush();
		} catch (Exception e) {
			logger.error("write2PageUTF8Json ", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
