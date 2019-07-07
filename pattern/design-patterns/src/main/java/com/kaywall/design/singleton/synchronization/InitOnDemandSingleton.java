package com.kaywall.design.singleton.synchronization;

/**
 * 按需初始化单例（惰性加载）： 使用内部类实现
 *@desc InitOnDemandSingleton
 *@author aikaiqiang
 *@date 2019-07-07 08:38
 *
 **/
public class InitOnDemandSingleton {


    private static class InstanceHolder{
        private static final InitOnDemandSingleton INSTANCE = new InitOnDemandSingleton();

    }

    private InitOnDemandSingleton(){}

    public static InitOnDemandSingleton getInstance(){
        return InstanceHolder.INSTANCE;
    }

}
