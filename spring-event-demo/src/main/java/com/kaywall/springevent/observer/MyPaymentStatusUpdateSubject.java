package com.kaywall.springevent.observer;

import java.util.List;
import java.util.Vector;

/**
 *
 *@desc MyPaymentStatusUpdateSubject 被观察者-被监听
 *@author aikaiqiang
 *@date 2019-07-28 17:15
 *
 **/
public class MyPaymentStatusUpdateSubject implements Subject {

    private List<Observer> observers = new Vector<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void updatePaymentStatus(int newStatus) {

        // 业务逻辑操作
        System.out.println("更新新的支付状态为：" + newStatus);

        // 通知观察者
        this.notifyObservers();
    }
}