package com.kaywall.design.singleton;

/**
 * 枚举单例
 *@desc EnumSingleton
 *@author aikaiqiang
 *@date 2019-07-07 08:43
 *
 **/
public enum EnumSingleton {
    
    INSTANCE("Initial enum info"); //Name of the single instance
    
    private String info;
    
    private EnumSingleton(String info) {
        this.info = info;
    }
    
    public EnumSingleton getInstance(){
        return INSTANCE;
    }
    
    //getters and setters
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }    
}