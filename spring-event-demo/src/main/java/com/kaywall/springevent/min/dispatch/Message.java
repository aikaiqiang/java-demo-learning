package com.kaywall.springevent.min.dispatch;

public interface Message {

  Class<? extends Message> getType();

}