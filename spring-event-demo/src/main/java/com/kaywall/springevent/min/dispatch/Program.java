package com.kaywall.springevent.min.dispatch;

public class Program {
  public static void main(String[] args) {
    EventDispatcher dispatcher = new EventDispatcher();
    dispatcher.registerChannel(Event.class, new Handler());
    dispatcher.dispatch(new Event());
  }
}