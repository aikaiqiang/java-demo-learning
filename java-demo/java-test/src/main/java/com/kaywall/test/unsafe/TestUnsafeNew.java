package com.kaywall.test.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class TestUnsafeNew {

    static final Unsafe unsafe;
    static final long stateOffSet;
    private volatile long state = 0;

    static {
        try {
            // 通过反射获取 Unsafe 实例
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);

            stateOffSet = unsafe.objectFieldOffset(TestUnsafeNew.class.getDeclaredField("state"));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        TestUnsafeNew test = new TestUnsafeNew();
        System.out.println("before state = " + test.state);
        boolean success = unsafe.compareAndSwapInt(test, stateOffSet, 0, 1);
        System.out.println(success);
        System.out.println("stateOffSet = " + stateOffSet);
        System.out.println("after state = " + test.state);
    }
}
