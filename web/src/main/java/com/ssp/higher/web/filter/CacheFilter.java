package com.ssp.higher.web.filter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.ssp.higher.base.utils.HtmlResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@ConfigurationProperties(prefix = "cached")
public class CacheFilter extends OncePerRequestFilter {
    private final static Logger logger = LoggerFactory.getLogger(CacheFilter.class);

    private static final Cache<String, String> CACHE_KEY_STATUS = Caffeine.newBuilder().recordStats().expireAfterWrite(60, TimeUnit.SECONDS).build();

    private static final String KEY_PREFIX = "newpage-";

    private int expire;
    private int newexpire;
    public List<String> urlPatterns;

    public int getNewexpire() {
        return newexpire;
    }

    public void setNewexpire(int newexpire) {
        this.newexpire = newexpire;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public List<String> getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(List<String> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        long starttime = System.currentTimeMillis();

        httpServletResponse.setCharacterEncoding("UTF-8");

        String reqUrl = httpServletRequest.getRequestURI();
        String queryString = httpServletRequest.getQueryString();
        Boolean shouldCached = false;
        String key = getKey(reqUrl, queryString);

        String value = CACHE_KEY_STATUS.getIfPresent(key);
        if(value != null){
            logger.info("get from cache with key[{}]",key);
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(value);
        }else {

            shouldCached = urlPatterns.stream().anyMatch(pattern -> reqUrl.matches(pattern));

            if (shouldCached) {
                HtmlResponseWrapper htmlResponseWrapper = new HtmlResponseWrapper(httpServletResponse);
                filterChain.doFilter(httpServletRequest, htmlResponseWrapper);
                byte[] captureAsBytes = htmlResponseWrapper.getCaptureAsBytes();
                String content = new String(captureAsBytes,"UTF-8");
                CACHE_KEY_STATUS.put(key, content);

                long responsetime = System.currentTimeMillis() - starttime;
                logger.info("cached filter response with url[{}] time cost[{}]ms,", reqUrl +"?"+ queryString, responsetime);
            } else {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }
    }

    private String getKey(String url, String queryString) {
        String s = url + "?" + queryString;
        return s;
    }

}
