package com.kaywall.test;

public class ClassLoadDemo {




    static class DeadLoopClass{
        static {
            if(true){
                System.out.println(Thread.currentThread() + "init DeadLoopClass");
                while(true){

                }
            }

        }
    }


    /**
     * 多个线程去初始化同一个类，只会有一个线程去初始化，其他线程等待
     * @param args
     */
    public static void main(String[] args) {

        Runnable runnable = () -> {
            System.out.println(Thread.currentThread() + "start");
            DeadLoopClass dlc = new DeadLoopClass();
            System.out.println(Thread.currentThread() + "run over");
        };

        Thread thread1 = new Thread(runnable);
        thread1.start();
        Thread thread2 = new Thread(runnable);
        thread2.start();
    }
}
