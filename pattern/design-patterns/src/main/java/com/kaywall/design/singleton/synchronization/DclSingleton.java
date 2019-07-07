package com.kaywall.design.singleton.synchronization;

/**
 * 双检锁单例
 *@desc DclSingleton
 *@author aikaiqiang
 *@date 2019-07-07 08:21
 *
 **/
public class DclSingleton {

    // 实例
    private static volatile DclSingleton instance;

    // 私有化构造器
    private DclSingleton(){}

    // 获取实例
    public static DclSingleton getInstance(){
        if (instance == null) {
            synchronized (DclSingleton.class){
                if (instance == null) {
                    instance = new DclSingleton();
                }
            }
        }
        return instance;
    }

}
