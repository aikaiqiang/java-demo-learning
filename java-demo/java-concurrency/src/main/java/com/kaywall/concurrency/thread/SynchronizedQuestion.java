package com.kaywall.concurrency.thread;

public class SynchronizedQuestion {


    static {
        synchronized (SynchronizedQuestion.class){

        }
    }

    private synchronized void synchronizedMethodBlock(){

    }
}
