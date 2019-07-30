package com.kaywall.springevent.observer.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 无序的事件监听器： 库存服务监听器
 */
@Component
public class AnnotationStockPaymentStatusUpdateListener {

    @EventListener
    public void onApplicationEvent(PaymentStatusUpdateEvent event) {
        System.out.println("库存服务, 收到支付状态更新的事件 - 注解方式监听事件. " + event + " - Thread: " + Thread.currentThread().getName());
    }
}