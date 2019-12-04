package com.kaywall.test.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年12月03日 16:28
 */
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	public CustomBeanFactoryPostProcessor() {
		super();
		System.out.println("【构造器】实例化 BeanFactoryPostProcessor 实现类");
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("执行 BeanFactoryPostProcessor 的 postProcessBeanFactory() 方法");
		BeanDefinition beanDefinition = beanFactory.getBeanDefinition("person");
		beanDefinition.getPropertyValues().addPropertyValue("phone", "18700001111");
	}
}
