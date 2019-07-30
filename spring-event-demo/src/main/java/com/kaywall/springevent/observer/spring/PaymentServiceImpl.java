package com.kaywall.springevent.observer.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void pay(Integer id, String msg) {
        PaymentInfo paymentInfo = new PaymentInfo(id, msg);
        PaymentStatusUpdateEvent paymentStatusUpdateEvent = new PaymentStatusUpdateEvent(paymentInfo);
        applicationContext.publishEvent(paymentStatusUpdateEvent);
    }
}
