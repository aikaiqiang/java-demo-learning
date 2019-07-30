package com.kaywall.springevent.min.event.machine;

import com.kaywall.springevent.min.dispatch.Event;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 *
 * 单例事件队列
 *@desc EventQueue
 *@author aikaiqiang
 *@date 2019-07-26 18:01
 *
 **/
public class EventQueue {
    private static EventQueue instance;

    private Queue<Event> eventQueue;

    private EventQueue(){
        eventQueue = new LinkedList<Event>();
    }

    public static EventQueue getInstance(){
        if(instance == null){
            instance = new EventQueue();
        }

        return instance;
    }

    /**
     * 添加事件
     * @param e
     */
    public void enqueue(Event e) {
        eventQueue.add(e);
    }

    /**
     * 返回队列头部的元素
     * @return
     */
    public Event peek() {
        return eventQueue.peek();
    }

    /**
     * 移除并返回队列头部的元素，如果队列为空，则抛出一个 NoSuchElementException 异常
     * @return
     * @throws NoSuchElementException
     */
    public Event dequeue() throws NoSuchElementException {
        return eventQueue.remove();
    }

    public boolean isEmpty() {
        return eventQueue.isEmpty();
    }

    /**
     * 队列大小
     * @return
     */
    public int size() {
        return eventQueue.size();
    }
}
