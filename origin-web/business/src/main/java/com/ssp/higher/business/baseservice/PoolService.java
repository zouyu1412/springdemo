package com.ssp.higher.business.baseservice;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class PoolService {

    /**
     * 核心线程池数量
     */
    protected int coreThreadNum = 1;
    /**
     * 最大线程池数量
     */
    protected int maxThreadNum = 3;

    /**
     * 线程池
     */
    protected ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(coreThreadNum, maxThreadNum, 20,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("%d").build());

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

}