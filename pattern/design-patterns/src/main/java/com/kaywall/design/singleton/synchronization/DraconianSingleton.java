package com.kaywall.design.singleton.synchronization;

/**
 * 严格单例
 *@desc DraconianSingleton
 *@author aikaiqiang
 *@date 2019-07-07 08:26
 *
 **/
public class DraconianSingleton {

	private static DraconianSingleton instance;

	private DraconianSingleton() {
	}

	public static synchronized DraconianSingleton getInstance() {
		if (instance == null) {
			instance = new DraconianSingleton();
		}
		return instance;
	}

	// 该单例性能低：每次获取单例都需要获取锁，synchronized 标注在getInstance() 方法上
	// 优化：先判断是否需要创建对象： instance == null, 然后才去加锁创建对象， 见 DclSingleton 类

}