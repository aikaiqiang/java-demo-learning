package com.kaywall.test.postprocessor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年12月03日 16:35
 */
public class Main {
	public static void main(String[] args) {

		System.out.println("===================现在开始初始化容器 - start");
		ApplicationContext factory = new ClassPathXmlApplicationContext("beans.xml");
		System.out.println("===================容器初始化成功 Success");
		// 获取实体 person 打印属性信息
		Person person = factory.getBean("person",Person.class);
		System.out.println(person);
		System.out.println("===================现在开始关闭容器，销毁容器");
		((ClassPathXmlApplicationContext)factory).registerShutdownHook();
	}
}
