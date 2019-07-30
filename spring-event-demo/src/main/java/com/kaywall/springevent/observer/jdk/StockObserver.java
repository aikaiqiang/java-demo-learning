package com.kaywall.springevent.observer.jdk;

import java.util.Observable;
import java.util.Observer;

public class StockObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("库存服务收到通知");
    }
}