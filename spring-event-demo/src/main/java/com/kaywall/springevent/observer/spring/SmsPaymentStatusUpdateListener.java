package com.kaywall.springevent.observer.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 有序监听器：短息服务监听器
 */
@Component
public class SmsPaymentStatusUpdateListener implements SmartApplicationListener {
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == PaymentStatusUpdateEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return sourceType == PaymentInfo.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("短信服务, 收到支付状态更新的通知. " + event + " - Thread: " + Thread.currentThread().getName());
    }

    // 排序
    @Override
    public int getOrder() {
        return 2;
    }
}