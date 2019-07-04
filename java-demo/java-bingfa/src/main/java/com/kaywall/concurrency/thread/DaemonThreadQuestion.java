package com.kaywall.concurrency.thread;

/**
 * @description  守候线程：主线程结束后，子守候线程是否执行完毕（视子线程执行时间）
 * @author aikq
 * @date 2019/7/3 9:19
 */
public class DaemonThreadQuestion {

    public static void main(String[] args) {

        Thread thread = new Thread(()->{
            System.out.println("children thread execute 1");
            Thread currentThread = Thread.currentThread();
            System.out.println("children thread execute 2");
            System.out.printf("线程name: %s, daemon: %s", currentThread.getName(), currentThread.isDaemon());
            System.out.println("children thread execute 3");

        }, "daemon ");

        // 设置守候线程
        thread.setDaemon(true);
        thread.start();
    }
}
