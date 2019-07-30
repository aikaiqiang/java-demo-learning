package com.kaywall.springevent.observer;


/**
 *
 *@desc Subject 自定义被观察者
 *@author aikaiqiang
 *@date 2019-07-28 17:06
 *
 **/
public interface Subject {
    /**
     * 添加观察者
     * @param observer
     */
    void addObserver(Observer observer);

    /**
     * 删除观察者
     * @param observer
     */
    void removeObserver(Observer observer);

    /**
     * 通知观察者
     */
    void notifyObservers();
}