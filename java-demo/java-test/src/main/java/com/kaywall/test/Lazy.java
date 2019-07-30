package com.kaywall.test;

public class Lazy {

    private static boolean initialized = false;

    // Lazy static 模块执行时， Runnable 匿名内置类随之初始化

    static {
        // static 模块通过 main 线程运行
        // 子线程
        println("static 模块执行");
        Thread t = new Thread(
            // case 1: 匿名内置类(不能依赖外部类的初始化)
            // 如果是匿名内置类的话，其初始化依赖于外部类初始化
//          new Runnable() {
//               @Override
//               public void run() {
//                   initialized = true;
//                   System.out.printf("线程[%s] - %s \n", Thread.currentThread().getName(), "子线程 run 方法执行");
//               }
//          }

             // case 2: Lambda 表达式： invokedynamic 指令实现
             () -> {
//                    println("run 方法执行");
//                    initialized = true;
                }


             // case 3: 方法引用，invokedynamic 指令实现
             // System.out::println 方法属于 System.out 对象类java.io.PrintStream 它被 Bootstrap ClassLoader启动类加载器加载
             // 早于 Lazy.class App/System ClassLoader (应用类加载器/系统类加载器)
             // System.out::println
             // 加载的外部类和当前类在类初始化阶段不能有相互依赖，否则容易相互等待（死锁）
        );
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            new AssertionError(e);
        }


    }

    public static void main(String[] args) {
        // 主线程 main 5
        println("main 方法执行");
        System.out.println(Lazy.initialized);
    }



    private static void println(Object o){
        System.out.printf("线程[%s] - %s \n", Thread.currentThread().getName(), o);
    }
}
