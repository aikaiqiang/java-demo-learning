package com.kaywall.concurrency.thread.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5, 5,
                1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10, false),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        threadPool.allowCoreThreadTimeOut(true);  //线程的最大空闲时间，超出这个时间将进行回收
        for (int i = 1; i <= 5; i++) {
            threadPool.execute(new Task(i));
        }

        try {
            System.out.println("等待线程池回收-start");
            Thread.sleep(3 * 1000);
            System.out.println("等待线程池回收-end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("回收后线程池中的线程数量 = " + threadPool.getActiveCount());


        for (int i = 6; i <= 10; i++) {
            threadPool.execute(new Task(i));
        }

    }

    static class Task implements Runnable {
        private int x; // 线程编号

        public Task(int x) {
            this.x = x;
        }

        public void run() {
            System.out.println(x + " thread doing something!");
            System.out.println("第" + x + "个线程执行完毕:" + Thread.currentThread().getName());
        }
    }
}
