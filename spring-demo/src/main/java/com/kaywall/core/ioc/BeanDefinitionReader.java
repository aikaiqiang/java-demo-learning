package com.kaywall.core.ioc;

import java.io.FileNotFoundException;

/**
 * @description
 * @author aikaiqiang
 * @date 2020/1/8 17:01
 */
public interface BeanDefinitionReader {

    void loadBeanDefinitions(String location) throws FileNotFoundException, Exception;

}