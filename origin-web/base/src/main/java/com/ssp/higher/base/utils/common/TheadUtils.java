package com.ssp.higher.base.utils.common;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.*;

/**
 * 一个简单的异步线程池
 * 
 * @author congli216488
 */
public class TheadUtils {
	/**
	 * 一个定时线程池
	 */
	private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("content-schedule-pool-%d").daemon(true).build());
	/**
	 * 一个固定的线程池
	 */
	private static final ExecutorService FIXED_THREAD_POOL = new ThreadPoolExecutor(1, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new BasicThreadFactory.Builder().namingPattern("content-fixed-pool-%d").daemon(true).build());

	public static ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
		return SCHEDULED_EXECUTOR_SERVICE.schedule(command, delay, unit);
	}

	public static Future<?> submit(Callable<?> task) {
		return FIXED_THREAD_POOL.submit(task);
	}

	public static Future<?> submit(Runnable task) {
		return FIXED_THREAD_POOL.submit(task);
	}

	public static Future<?> submit(Runnable task, T result) {
		return FIXED_THREAD_POOL.submit(task, result);
	}

}
