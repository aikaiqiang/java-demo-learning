package com.kaywall.concurrency;

public class SynchronizedQuestion {


    static {
        synchronized (SynchronizedQuestion.class){

        }
    }

    private synchronized void synchronizedMethodBlock(){

    }
}
