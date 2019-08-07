package com.kaywall.test.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();
        MyThred myThred = new MyThred(lock);
        Thread thread1 = new Thread(myThred);
        Thread thread2 = new Thread(myThred);
        thread1.start();
        thread2.start();

    }


    private static class MyThred implements Runnable{

        private ReentrantLock lock;

        public MyThred(ReentrantLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                lock.tryLock();
                throw new Exception(String.format("线程[%s], get lock error", Thread.currentThread().getName()));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.printf("线程[%s]锁状态：%s", Thread.currentThread().getName(), lock.isLocked());
                lock.unlock();
            }
        }
    }

}
