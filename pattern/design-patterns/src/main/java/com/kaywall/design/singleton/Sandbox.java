package com.kaywall.design.singleton;

/**
 * 沙盒测试
 *@desc Sandbox
 *@author aikaiqiang
 *@date 2019-07-07 08:44
 *
 **/
public class Sandbox {

    public static void main(String[] args) {

        // class singleton
        ClassSingleton classSingleton1 = ClassSingleton.getInstance();
        System.out.println(classSingleton1.getInfo());

        ClassSingleton classSingleton2 = ClassSingleton.getInstance();
        classSingleton2.setInfo("edit class info");
        System.out.println(classSingleton2.getInfo());
        System.out.println(classSingleton1.getInfo());

        // Enum singleton
        EnumSingleton enumSingleton1 = EnumSingleton.INSTANCE.getInstance();
        System.out.println(enumSingleton1.getInfo());

        EnumSingleton enumSingleton2 = EnumSingleton.INSTANCE.getInstance();
        enumSingleton2.setInfo("new enum info");
        System.out.println(enumSingleton1.getInfo());
        System.out.println(enumSingleton2.getInfo());
    }

}
