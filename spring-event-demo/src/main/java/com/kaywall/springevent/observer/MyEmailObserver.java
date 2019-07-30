package com.kaywall.springevent.observer;


/**
 *
 *@desc MyEmailObserver 邮件服务-监听器
 *@author aikaiqiang
 *@date 2019-07-28 17:15
 *
 **/
public class MyEmailObserver implements Observer{
    @Override
    public void update() {
        System.out.println("邮件服务收到通知.");
    }
}