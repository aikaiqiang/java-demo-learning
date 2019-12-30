package com.kaywall.test.unsafe;

import sun.misc.Unsafe;

public class TestUnsafe {

    static final Unsafe unsafe = Unsafe.getUnsafe();
    static final long stateOffSet;
    private volatile long state = 0;

    static {
        try {
            stateOffSet = unsafe.objectFieldOffset(TestUnsafe.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        TestUnsafe test = new TestUnsafe();
        boolean success = unsafe.compareAndSwapInt(test, stateOffSet, 0, 1);
        System.out.println(success);
    }

}
