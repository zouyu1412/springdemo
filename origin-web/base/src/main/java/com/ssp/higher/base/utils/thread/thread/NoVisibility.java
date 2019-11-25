package com.ssp.higher.base.utils.thread.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by Mobin on 2016/4/4.
 */
public class NoVisibility {
    private static  boolean ready;
    private static int number;

    private static class ReaderThread extends  Thread{
        public void run() {
            while (! ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws Exception{
        new ReaderThread().start();
        number = 42;
        TimeUnit.SECONDS.sleep(2);
        ready = true;
    }
}
