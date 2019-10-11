package com.kaywall.design.factory;

/**
 *  具体工厂1
 * @author aikaiqiang
 * @date 2019年10月11日 14:53
 */
public class ConcreteFactory1 implements AbstractFactory {
	@Override
	public Product newProduct() {
		System.out.println("工厂[1] --生产--> 产品[1]");
		return new ConcreteProduct1();
	}
}
