package com.kaywall.design.singleton;


/**
 * 金典单例
 *@desc ClassSingleton
 *@author aikaiqiang
 *@date 2019-07-07 08:42
 *
 **/
public class ClassSingleton {

    private static ClassSingleton INSTANCE;
    private String info = "Initial class info";
    
    private ClassSingleton(){        
    }
    
    public static ClassSingleton getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ClassSingleton();
        }
        
        return INSTANCE;
    }
    
    // getters and setters
    public String getInfo() {
        return info;
    }
    
    public void setInfo(String info) {
        this.info = info;
    }
}