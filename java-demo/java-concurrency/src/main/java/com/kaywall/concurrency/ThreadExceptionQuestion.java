package com.kaywall.concurrency;

public class ThreadExceptionQuestion {


    public static void main(String[] args) {

        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {

            System.out.printf("线程[%s] 遇到异常，msg： %s", thread.getName(), throwable.getMessage());
        });

        // main 主线程 -> 子线程
        Thread t1 = new Thread(() -> {
            throw new RuntimeException("子线程抛出异常信息：执行失败");
        }, "t1");

        t1.start();

    }







    private static void action(){
        System.out.printf("[线程：%s] 正在执行。。。\n", Thread.currentThread().getName());
    }
}
