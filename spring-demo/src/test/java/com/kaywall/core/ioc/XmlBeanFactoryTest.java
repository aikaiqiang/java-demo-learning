package com.kaywall.core.ioc;

import com.kaywall.Car;
import com.kaywall.Wheel;
import com.kaywall.core.ioc.xml.XmlBeanFactory;
import org.junit.Test;

public class XmlBeanFactoryTest {

    @Test
    public void getBean() throws Exception {
        System.out.println("--------- IOC test ----------");
        String location = getClass().getClassLoader().getResource("spring-bean.xml").getFile();
        XmlBeanFactory bf = new XmlBeanFactory(location);
        Wheel wheel = (Wheel) bf.getBean("wheel");
        System.out.println(wheel);
        Car car = (Car) bf.getBean("car");
        System.out.println(car);

        System.out.println("\n--------- AOP test ----------");
//        HelloService helloService = (HelloService) bf.getBean("helloService");
//        helloService.sayHelloWorld();
    }
}