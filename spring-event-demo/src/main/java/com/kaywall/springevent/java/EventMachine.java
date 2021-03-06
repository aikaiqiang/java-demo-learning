package com.kaywall.springevent.java;

import java.util.LinkedList;
import java.util.Queue;

public class EventMachine {


    // Event definition
    public static class Event {

        /**
         * 事件类型
         */
        public char type;
        /**
         * 事件要处理的数据
         */
        public String data;

        public Event(char type, String data) {
            this.type = type;
            this.data = data;
        }

    }


    // Event handler A
    public static void printEventA(Event e) {
        System.out.println(e.data);
    }

    // Event handler B
    public static void printEventB(Event e) {
        System.out.println(e.data.toUpperCase());
    }


    public static void main(String[] args) {
        Queue<Event> events = new LinkedList<Event>();
        events.add(new Event('A', "Hello"));
        events.add(new Event('B', "event-driven"));
        events.add(new Event('A', "world!"));

        // Event loop
        Event e;
        while (!events.isEmpty()) {
            e = events.remove();

            if (e.type == 'A')
                printEventA(e);
            if (e.type == 'B')
                printEventB(e);
        }
    }
}
