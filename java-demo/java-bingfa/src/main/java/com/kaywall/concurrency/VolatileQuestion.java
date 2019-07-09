package com.kaywall.concurrency;


import java.util.concurrent.CountDownLatch;

/**
 *
 * Volatile 可见性，线程安全，但不是原子性操作
 *@desc VolatileQuestion
 *@author aikaiqiang
 *@date 2019-07-08 23:44
 *
 **/
public class VolatileQuestion {

    public static volatile int race = 0;

    public static void increase(){
        race++;
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {

        CountDownLatch downLatch = new CountDownLatch(20);
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    increase();
                }

                // 累加完毕信号量 -1
                downLatch.countDown();
            }, "t" + i);
            threads[i].start();
        }

        // 等待所有累加线程都结束
//        while (Thread.activeCount() > 1){
//            Thread.yield();
//        }
        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.interrupted();
        }

        System.out.println("To End；race = " + race);
    }


    /**
     * 上述例子，起 20 个线程做加法运算，race 用关键字 volatile 标注，最终运算结果不是 200000
     *
     * 问题原因：race++；先取值再加，取值后，虽然标志 volatile ，可能其他执行完的线程已经将值回写
     * 到主存，此时读取到的值已经变更
     *
     */
}
