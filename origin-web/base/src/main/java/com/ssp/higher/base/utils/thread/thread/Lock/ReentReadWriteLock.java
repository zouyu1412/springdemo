package com.ssp.higher.base.utils.thread.thread.Lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IDEA
 * Creater: MOBIN
 * Date: 2018/8/15
 * Time: 4:25 PM
 */
public class ReentReadWriteLock {
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    private static ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
//    private static Lock lock = new ReentrantLock();
    private static Map<String, String> maps = new HashMap<>();
    private static CountDownLatch latch = new CountDownLatch(102);
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(102);

    public static void main(String[] args) throws InterruptedException {
        long benginTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i ++){
            new Thread(new ReadThread()).start();
        }
        for (int i = 0; i < 100; i ++){
            new Thread(new WriteThread()).start();
        }
        latch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("Consumer Time is " + (endTime - benginTime) + "ms");
    }

    static class WriteThread implements Runnable{
        @Override
        public void run() {
            try {
                cyclicBarrier.await();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                writeLock.lock();
                maps.put("1", "2");
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                writeLock.unlock();
            }
            latch.countDown();
        }
    }

    static class ReadThread implements Runnable{
        @Override
        public void run() {
            try {
                cyclicBarrier.await();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                readLock.lock();
                maps.get("1");
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                readLock.unlock();
            }
            latch.countDown();
        }
    }
}
