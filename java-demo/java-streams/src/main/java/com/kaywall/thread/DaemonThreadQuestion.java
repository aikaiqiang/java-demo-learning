package com.kaywall.thread;

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
