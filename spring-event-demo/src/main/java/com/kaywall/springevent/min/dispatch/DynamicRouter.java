package com.kaywall.springevent.min.dispatch;

public interface DynamicRouter<E extends Message> {

  /**
   * 注册事件
   * @param contentType
   * @param channel
   */
  void registerChannel(Class<? extends E> contentType,
      Channel<? extends E> channel);

  /**
   * 事件分发
   * @param content
   */
  void dispatch(E content);
}