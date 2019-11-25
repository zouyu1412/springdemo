package com.ssp.higher.base.utils.thread.lock;

public class WaitNotifyTest {
    private static Object obj = new Object();
    public static void main(String[] args) {
        new Thread(new WaitThread()).start();
        new Thread(new NotifyThread()).start();
    }
    static class WaitThread implements Runnable {
        @Override
        public void run() {
            synchronized (obj) {
                System.out.println("start wait!");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end wait!");
            }
        }
    }
    static class NotifyThread implements Runnable {
        @Override
        public void run() {
            synchronized (obj) {
                System.out.println("start notify!");
                obj.notify();
                System.out.println("end notify");
            }
        }
    }
}