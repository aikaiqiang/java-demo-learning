package com.kaywall.test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载器测试
 *@desc ClassLoaderTest
 *@author aikaiqiang
 *@date 2019-07-09 23:09
 *
 **/
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        differentLoaderTest();
    }


    public static void differentLoaderTest() throws Exception {

        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {

                try {
                    String fileName = name.substring(name.lastIndexOf(".")+ 1) + "class";
                    System.out.println("fileName = "+ fileName);
                    InputStream ins = getClass().getResourceAsStream(fileName);

                    if (ins == null){
                        System.out.println("super loader");
                        return super.loadClass(name);
                    }

                    byte[] b = new byte[ins.available()];
                    ins.read(b);
                    return defineClass(name, b, 0,  b.length);

                } catch (IOException e) {
                    throw new ClassNotFoundException();
                }
            }
        };

        Object obj = myLoader.loadClass("com.kaywall.test.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoaderTest);

    }
}
