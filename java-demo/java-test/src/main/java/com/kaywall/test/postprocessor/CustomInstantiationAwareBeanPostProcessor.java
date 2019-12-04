package com.kaywall.test.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

/**
 *  InstantiationAwareBeanPostProcessor 接口本质是BeanPostProcessor的子接口，
 *  一般我们继承Spring为其提供的适配器类InstantiationAwareBeanPostProcessor Adapter来使用它
 * @author aikaiqiang
 * @date 2019年12月03日 16:21
 */
public class CustomInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {
	public CustomInstantiationAwareBeanPostProcessor() {
		super();
		System.out.println("【构造器】实例化 InstantiationAwareBeanPostProcessorAdapter 实现类");
	}

	// 接口方法、实例化Bean之前调用
	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		System.out.println("执行 InstantiationAwareBeanPostProcessor 的 postProcessBeforeInstantiation() 方法");
		return super.postProcessBeforeInstantiation(beanClass, beanName);
	}

	// // 接口方法、实例化Bean之后调用
	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		System.out.println("执行 InstantiationAwareBeanPostProcessor 的 postProcessAfterInitialization() 方法");
		return super.postProcessAfterInstantiation(bean, beanName);
	}

	// // 接口方法、设置某个属性时调用
	@Override
	public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
			throws BeansException {
		System.out.println("执行 InstantiationAwareBeanPostProcessor 的 postProcessPropertyValues() 方法");
		return super.postProcessProperties(pvs, bean, beanName);
	}


}
