package com.kaywall.core.ioc;

/**
 * @description 后处理器
 * @author aikaiqiang
 * @date 2020/1/8 16:19
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception;

    Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;
}