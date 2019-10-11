package com.kaywall.design.factory;

/**
 *  具体工厂2
 * @author aikaiqiang
 * @date 2019年10月11日 14:53
 */
public class ConcreteFactory2 implements AbstractFactory {
	@Override
	public Product newProduct() {
		System.out.println("工厂[2] --生产--> 产品[2]");
		return new ConcreteProduct2();
	}
}
