package com.ssp.higher.base.utils.thread.thread.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Mobin on 2016/3/28.
 */
public class BoundedBeffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[10];
    int putptr,takeptr,count;
    public int size(){
        return count;
    }

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)  //说明队列已满，需要等待（测试条件谓词）
                notFull.await();
            if(++putptr == items.length)
                putptr = 0;
            items[putptr] = x;
            ++count;
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try{
            while (count == 0)  //说明队列元素为空，没有值可等待值的插入（测试条件谓词）
                notEmpty.await();
            if(++takeptr == items.length)
                takeptr = 0;
            Object x = items[takeptr];
            -- count;
            notFull.signal();
            return x;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BoundedBeffer boundedBeffer = new BoundedBeffer();
        try {
            new Thread(()-> {
                try {
                    for (int i = 0; i < 200; i++) {
                        System.out.println("插入元素:" + i);
                        boundedBeffer.put(i + "");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }).start();
            System.out.println("主线程休息2s");
            Thread.sleep(2000);
            new Thread(()->{
                try {
                    while (true) {
                        while (boundedBeffer.size() >= 100) {
                            Object take = boundedBeffer.take();
                            System.out.println("取出元素:" + take.toString());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }).start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
