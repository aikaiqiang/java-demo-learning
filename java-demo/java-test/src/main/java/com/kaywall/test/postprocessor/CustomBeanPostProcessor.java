package com.kaywall.test.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年12月03日 16:11
 */
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

	public CustomBeanPostProcessor() {
		super();
		System.out.println("【构造器】实例化 BeanPostProcessor 实现类");
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("BeanPostProcessor 接口方法 postProcessBeforeInitialization 对属性进行更改");
		return null;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("BeanPostProcessor 接口方法 postProcessAfterInitialization 对属性进行更改");
		return null;
	}
}
