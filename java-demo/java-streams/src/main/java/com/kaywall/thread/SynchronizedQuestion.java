package com.kaywall.thread;

public class SynchronizedQuestion {


    static {
        synchronized (SynchronizedQuestion.class){

        }
    }

    private synchronized void synchronizedMethodBlock(){

    }
}
