package com.kaywall.springevent.observer.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        PaymentService paymentService = applicationContext.getBean(PaymentService.class);

        paymentService.pay(1, "支付成功");
    }

}