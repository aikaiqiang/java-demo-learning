package com.kaywall.core.ioc.factory;

/**
 *  E
 * @author aikaiqiang
 * @date 2020年01月08日 16:21
 */
public interface BeanFactory {
	Object getBean(String beanId) throws Exception;
}
