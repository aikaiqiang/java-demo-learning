package com.kaywall.design.factory;

/**
 *  测试
 * @author aikaiqiang
 * @date 2019年10月11日 15:03
 */
public class FactoryTest {
	public static void main(String[] args) {
		Product a;
		AbstractFactory af;
		af = (AbstractFactory) ReadXmlUtils.getObject();
		a = af.newProduct();
		a.show();
	}
}
