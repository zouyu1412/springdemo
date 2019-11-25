package com.ssp.higher.base.utils.thread.thread.Executor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IDEA
 * Creater: MOBIN
 * Date: 2018/8/10
 * Time: 4:26 PM
 */
public class BlockingQueueExecutor {
    final BlockingQueue<Runnable> queue = new SynchronousQueue<>();
    final AtomicInteger completedTask = new AtomicInteger(0);
    final AtomicInteger rejectedTask= new AtomicInteger(0);
    static long beginTime;
    final int count = 1000;

    final ThreadPoolExecutor executor = new ThreadPoolExecutor(10,600,30,
            TimeUnit.SECONDS,queue,
            Executors.defaultThreadFactory(),blockingExecutorHandler);

    public static final RejectedExecutionHandler blockingExecutorHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
            BlockingQueue<Runnable> queue = executor.getQueue();
            while (true) {
                if (executor.isShutdown()) {
                    throw  new RejectedExecutionException("TheadPoolExecutor has shut down!");
                }
                try {
                    if (queue.offer(task, 5000, TimeUnit.MILLISECONDS)){
                        break;
                    }
                } catch (InterruptedException e) {
                    throw new AssertionError(e);
                }
            }

        }
    };





    public void start(){
        CountDownLatch latch = new CountDownLatch(count);
        CyclicBarrier barrier = new CyclicBarrier(count);
        for (int i = 0; i < count; i ++){
            new Thread(new TestThread(latch, barrier)).start();
        }
    }

    public static void main(String[] args) {
        beginTime = System.currentTimeMillis();
        BlockingQueueExecutor blockingQueueExecutor = new BlockingQueueExecutor();
        blockingQueueExecutor.start();
    }


    class TestThread implements Runnable{
        private CountDownLatch latch;
        private CyclicBarrier barrier;

        public TestThread(CountDownLatch latch, CyclicBarrier barrier) {
            this.latch = latch;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try{
                barrier.await();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                //barrier的值到达1000时才执行任务
                executor.execute(new Task(latch));
            }catch (RejectedExecutionException e){
                latch.countDown();
                System.out.println("被拒绝的任务数：" + Thread.currentThread().getName() + "---" + rejectedTask.incrementAndGet());
            }
        }

    }
    class Task implements Runnable{
        private CountDownLatch latch;

        public Task(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("执行的任务数：" + Thread.currentThread().getName() + "---" + completedTask.incrementAndGet());
            System.out.println("任务耗时为：" + (System.currentTimeMillis() - beginTime) + "ms");
            latch.countDown();
        }
    }


}

