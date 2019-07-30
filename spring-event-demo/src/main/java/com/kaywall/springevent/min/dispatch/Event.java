package com.kaywall.springevent.min.dispatch;

public class Event implements Message {

  @Override
  public Class<? extends Message> getType() {
    return getClass();
  }
}