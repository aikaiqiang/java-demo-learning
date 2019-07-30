package com.kaywall.springevent.observer.javabean;

public class Test {
    public static void main(String[] args) {
        PaymentStatusUpdateBean paymentStatusUpdateBean = new PaymentStatusUpdateBean();

        // 添加监听器
        paymentStatusUpdateBean.addPropertyChangeListener(new PaymentStatusUpdateListener());

        // 更新支付状态
        paymentStatusUpdateBean.updateStatus(3);
    }
}