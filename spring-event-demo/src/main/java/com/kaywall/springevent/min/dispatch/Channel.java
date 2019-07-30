package com.kaywall.springevent.min.dispatch;

public interface Channel<E extends Message> {

  void dispatch(E message);

}