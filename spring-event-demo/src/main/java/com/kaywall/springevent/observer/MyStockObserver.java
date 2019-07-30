package com.kaywall.springevent.observer;

/**
 *
 *@desc MyStockObserver 库存服务-监听器
 *@author aikaiqiang
 *@date 2019-07-28 17:16
 *
 **/
public class MyStockObserver implements Observer {
    @Override
    public void update() {
        System.out.println("库存服务收到通知");
    }
}