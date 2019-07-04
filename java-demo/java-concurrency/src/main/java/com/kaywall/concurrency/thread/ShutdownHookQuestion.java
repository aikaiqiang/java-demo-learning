package com.kaywall.concurrency.thread;


/**
 * 线程退出 Hook 子线程做任务
 *@desc ShutdownHookQuestion
 *@author aikaiqiang
 *@date 2019-07-02 23:13
 *
 **/
public class ShutdownHookQuestion {

    public static void main(String[] args) {

        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Thread(ShutdownHookQuestion::action, "shutdown hook thread"));
    }


    private static void action(){
        System.out.printf("[线程：%s] 正在执行。。。\n", Thread.currentThread().getName());
    }
}