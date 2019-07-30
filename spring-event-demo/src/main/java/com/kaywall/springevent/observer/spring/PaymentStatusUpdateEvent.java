package com.kaywall.springevent.observer.spring;

import org.springframework.context.ApplicationEvent;

/**
 * 订单状态更新事件
 */
public class PaymentStatusUpdateEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public PaymentStatusUpdateEvent(PaymentInfo source) {
        super(source);
    }
}