package com.kaywall.concurrency.thread;

public class ThreadExecutionQuestion {

    public static void main(String[] args) throws InterruptedException {


//        threadWait();
        threadLoop();
//        threadSleep();
//        threadJoinOnebyOne();

    }

    private static void threadWait(){
        Thread t1 = new Thread(ThreadExecutionQuestion::action, "t1");
        Thread t2 = new Thread(ThreadExecutionQuestion::action, "t2");
        Thread t3 = new Thread(ThreadExecutionQuestion::action, "t3");

        threadStartAndWait(t1);
        threadStartAndWait(t2);
        threadStartAndWait(t3);
    }


    private static void threadStartAndWait(Thread thread){
        if(Thread.State.NEW.equals(thread.getState())){
            thread.start();
        }

        while (thread.isAlive()){
            synchronized (thread){
                try {
                    thread.wait(); // 是谁通知 Thread -> thread.notify();
//                    LockSupport.park(); // 死锁发生
//                    LockSupport.unpark(thread);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }


    private static void threadLoop(){

        Thread t1 = new Thread(ThreadExecutionQuestion::action, "t1");
        Thread t2 = new Thread(ThreadExecutionQuestion::action, "t2");
        Thread t3 = new Thread(ThreadExecutionQuestion::action, "t3");

        t1.start();
        while (t1.isAlive()){
            // 自旋
        }

        t2.start();
        while (t2.isAlive()){

        }

        t3.start();
        while (t3.isAlive()){

        }


    }


    private static void threadSleep() throws InterruptedException {

        Thread t1 = new Thread(ThreadExecutionQuestion::action, "t1");
        Thread t2 = new Thread(ThreadExecutionQuestion::action, "t2");
        Thread t3 = new Thread(ThreadExecutionQuestion::action, "t3");


        t1.start();
        while (t1.isAlive()){
            Thread.sleep(0);
        }

        t2.start();
        while (t2.isAlive()){
            Thread.sleep(0);
        }

        t3.start();
        while (t3.isAlive()){
            Thread.sleep(0);
        }

    }


    private static void threadJoinOnebyOne() throws InterruptedException {

        Thread t1 = new Thread(ThreadExecutionQuestion::action, "t1");
        Thread t2 = new Thread(ThreadExecutionQuestion::action, "t2");
        Thread t3 = new Thread(ThreadExecutionQuestion::action, "t3");


        t1.start();
        t1.join();

        t2.start();
        t2.join();

        t3.start();
        t3.join();



    }

    private static void action(){
        System.out.printf("[线程：%s] 正在执行。。。\n", Thread.currentThread().getName());
    }
}
