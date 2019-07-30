package com.kaywall.springevent.observer.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 无序的事件监听器： 库存服务监听器
 */
@Component
public class StockPaymentStatusUpdateListener implements ApplicationListener<PaymentStatusUpdateEvent> {

//    @Async
    @Override
    public void onApplicationEvent(PaymentStatusUpdateEvent event) {
        System.out.println("库存服务, 收到支付状态更新的事件. " + event + " - Thread: " + Thread.currentThread().getName());
    }
}