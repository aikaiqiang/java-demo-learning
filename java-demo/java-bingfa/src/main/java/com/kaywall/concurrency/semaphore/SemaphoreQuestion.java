package com.kaywall.concurrency.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *
 * 信号量
 *@desc SemaphoreQuestion
 *@author aikaiqiang
 *@date 2019-07-05 00:36
 *
 **/
public class SemaphoreQuestion {

    public static void main(String[] args) {

        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < 13; i++) {
            final int num = i;
            exec.execute(() -> {

                try {
                    // 获取许可
                    semaphore.acquire();
                    System.out.println("线程：" +Thread.currentThread().getName() + "获得许可："  + num);

                    // 模拟耗时
                    for(int j = 0; j < 10000; j++);

                    // 释放许可
                    semaphore.release();
                    System.out.println("线程：" +Thread.currentThread().getName() + "释放许可："  + num);

                    // 打印当前允许进入任务个数
                    System.out.println("available = " + semaphore.availablePermits());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


        // 关闭线程池
        exec.shutdown();


    }
}
