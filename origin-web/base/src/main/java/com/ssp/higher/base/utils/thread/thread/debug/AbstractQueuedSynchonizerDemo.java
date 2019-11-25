package com.ssp.higher.base.utils.thread.thread.debug;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MyThread extends Thread {
    private Lock lock;
    public MyThread(String name, Lock lock) {
        super(name);
        this.lock = lock;
    }
    
    @Override
    public void run () {
        lock.lock();
        try {
            System.out.println(Thread.currentThread() + " running");
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 　对于AbstractQueuedSynchronizer的分析，最核心的就是sync queue的分析。
 * 　　① 每一个结点都是由前一个结点唤醒
 * 　　② 当结点发现前驱结点是head并且尝试获取成功，则会轮到该线程运行。
 * 　　③ condition queue中的结点向sync queue中转移是通过signal操作完成的。
 * 　　④ 当结点的状态为SIGNAL时，表示后面的结点需要运行。
 */
public class AbstractQueuedSynchonizerDemo {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        
        MyThread t1 = new MyThread("t1", lock);
        MyThread t2 = new MyThread("t2", lock);
        t1.start();
        t2.start();    
    }
}