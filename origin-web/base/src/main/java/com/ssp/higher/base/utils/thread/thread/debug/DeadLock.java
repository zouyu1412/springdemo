package com.ssp.higher.base.utils.thread.thread.debug;

/**
 * @Author:zouyu
 * @Date:2019/10/20 10:30
 */
public class DeadLock {

    static Object A = new Object();
    static Object B = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (A){
                try {
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                synchronized (B){
                    System.out.println("能进来 我也叫你爸爸~");
                }
            }
        }).start();

        synchronized (B){
            try {
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            synchronized (A){
                System.out.println("能进来 我叫你爸爸~");
            }
        }

        System.out.println("dead~~~OOO");
    }

}
