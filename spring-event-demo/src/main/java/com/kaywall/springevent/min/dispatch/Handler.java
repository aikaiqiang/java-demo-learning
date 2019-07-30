package com.kaywall.springevent.min.dispatch;


/**
 *
 *@desc Handler  // Event handlers
 *@author aikaiqiang
 *@date 2019-07-26 17:40
 *
 **/
public class Handler implements Channel<Event> {

  @Override
  public void dispatch(Event message) {
    System.out.println(message.getClass());
  }
}
