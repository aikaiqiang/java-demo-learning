package com.kaywall.design.singleton.synchronization;

/**
 * jdk 早期版本初始化单例
 *@desc EarlyInitSingleton
 *@author aikaiqiang
 *@date 2019-07-07 08:33
 *
 **/
public class EarlyInitSingleton {

    private static final EarlyInitSingleton INSTANCE = new EarlyInitSingleton();

    private EarlyInitSingleton(){}

    public static EarlyInitSingleton getInstance(){
        return INSTANCE;
    }
}
