package com.kaywall.springevent.observer.jdk;

import java.util.Observable;

/**
 *
 *@desc PaymentStatusObservable 支付服务 - 被观察者
 *@author aikaiqiang
 *@date 2019-07-28 17:38
 *
 **/
public class PaymentStatusObservable extends Observable {

    public void updatePaymentStatus(int newStatus) {
        // 业务逻辑操作
        System.out.println("更新新的支付状态为：" + newStatus);

        // 通知观察者
        this.setChanged();//需要调用一下这这方法，表示被观察者的状态已发生变更，Observable才会通知观察者
        this.notifyObservers();
    }
}