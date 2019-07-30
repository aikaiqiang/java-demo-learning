package com.kaywall.springevent.observer.javabean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * 支付状态变更监听器
 *@desc PaymentStatusUpdateListener
 *@author aikaiqiang
 *@date 2019-07-28 17:44
 *
 **/
public class PaymentStatusUpdateListener implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.printf("支付状态变更. eventName : %s, oldValue : %s, newValue : %s", evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }
}