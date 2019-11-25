package com.ssp.higher.base.utils.thread.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    public static void main(String[] args) {
        Thread parkThread = new Thread(new ParkThread());
        parkThread.start();
        System.out.println("开始线程唤醒");
        LockSupport.unpark(parkThread);
        System.out.println("结束线程唤醒");

    }

    static class ParkThread implements Runnable{

        @Override
        public void run() {
            System.out.println("开始线程阻塞");
//            try {
//                Thread.sleep(1000);
//            }catch (Exception e){
//                System.out.println(e);
//            }
            LockSupport.park();
            System.out.println("结束线程阻塞");
        }
    }
}