package com.kaywall.springevent.observer;

/**
 *
 *@desc Test 观察者模式测试
 *@author aikaiqiang
 *@date 2019-07-28 17:17
 *
 **/
public class Test {
    public static void main(String[] args) {

        // "支付状态更新"->看做一个事件，可以被监听的事件

        // 被观察者。即事件源
        MyPaymentStatusUpdateSubject myPaymentStatusUpdateSubject = new MyPaymentStatusUpdateSubject();

        // 观察者。即事件监听器
        MyEmailObserver myEmailObserver = new MyEmailObserver();
        MyStockObserver myStockObserver = new MyStockObserver();

        // 添加观察者。
        myPaymentStatusUpdateSubject.addObserver(myEmailObserver);
        myPaymentStatusUpdateSubject.addObserver(myStockObserver);

        // 发布事件。支付状态更新。
        myPaymentStatusUpdateSubject.updatePaymentStatus(2);
    }
}