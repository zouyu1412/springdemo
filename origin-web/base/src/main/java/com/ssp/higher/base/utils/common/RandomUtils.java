package com.ssp.higher.base.utils.common;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils extends org.apache.commons.lang3.RandomUtils {
	/** 随机数生成器 对每个线程都是安全的，防止高并发的时候争抢seed */
	private static final ThreadLocalRandom RANDOM_NUMBER_GENERATOR = ThreadLocalRandom.current();

	/**
	 * 随机产生 [from ,to] 范围内随机数
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public static int getRandomInt(int from, int to) {
		return from + RANDOM_NUMBER_GENERATOR.nextInt(to - from + 1);
	}

	public static void main(String[] args) {
		int num = 100;
		for (int i = 0; i < num; i++) {
			System.out.println(getRandomInt(10, 14));
			// System.out.println(RANDOM_NUMBER_GENERATOR.nextInt(10));
			// System.out.println(RANDOM_NUMBER_GENERATOR.nextInt(10, 13));
		}
	}
}
