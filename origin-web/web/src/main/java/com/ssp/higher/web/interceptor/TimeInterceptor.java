package com.ssp.higher.web.interceptor;

import com.ssp.higher.web.annotation.RequestPage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TimeInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(TimeInterceptor.class);

	private String incDomain;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		long time = System.currentTimeMillis();
		request.setAttribute("start_time", time);
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {
		Long start_time = (Long) request.getAttribute("start_time");
		logger.info("call RequestPage[{}], RequestURL[{}], TIMES[{} ms]", getPageName((HandlerMethod) handler),
				request.getRequestURL().toString(), System.currentTimeMillis() - start_time);

		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	public String getIncDomain() {
		return incDomain;
	}

	public void setIncDomain(String incDomain) {
		this.incDomain = incDomain;
	}


	/**
	 * 获取请求页面的名称
	 * @param handlerMethod
	 * @return
	 */
	private String getPageName(HandlerMethod handlerMethod) {
		Class controllerClass = handlerMethod.getBean().getClass();
		RequestPage pageAnnotation =  (RequestPage) controllerClass.getAnnotation(RequestPage.class);
		if (pageAnnotation == null) {
			return StringUtils.EMPTY;
		}
		return pageAnnotation.name();
	}

}
