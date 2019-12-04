package com.kaywall.test.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 *  定义实体 Bean
 * @author aikaiqiang
 * @date 2019年12月03日 16:03
 */
public class Person implements BeanFactoryAware, BeanNameAware,
		InitializingBean, DisposableBean {

	private String name;
	private String address;
	private String phone;

	public Person() {
		System.out.println("【构造器】实例化 Person 【Bean】");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("【BeanFactoryAware接口】执行 BeanFactoryAware.setBeanFactory()");
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("【BeanNameAware接口】执行 BeanNameAware.setBeanName()");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("【DisposableBean 接口】执行 DisposableBean.destroy()");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("【InitializingBean 接口】执行 InitializingBean.afterPropertiesSet()");
	}

	public void customInit() {
		System.out.println("【init-method】执行<bean>的init-method属性指定的初始化方法");
	}

	public void customDestory() {
		System.out.println("【destroy-method】执行<bean>的destroy-method属性指定的初始化方法");
	}

	public void setName(String name) {
		System.out.println("【Bean 注入属性】注入属性 name");
		this.name = name;
	}

	public void setAddress(String address) {
		System.out.println("【Bean 注入属性】注入属性 address");
		this.address = address;
	}

	public void setPhone(String phone) {
		System.out.println("【Bean 注入属性】注入属性 phone");
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Person{" + "name='" + name + '\'' + ", address='" + address + '\'' + ", phone='" + phone + '\'' + '}';
	}
}
