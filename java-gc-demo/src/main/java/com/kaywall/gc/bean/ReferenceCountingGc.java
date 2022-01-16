package com.kaywall.gc.bean;

public class ReferenceCountingGc {

    public  Object instance = null;

    private static final int _1Mb = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1Mb];

    private static void testGC(){
        ReferenceCountingGc objA = new ReferenceCountingGc();
        ReferenceCountingGc objB = new ReferenceCountingGc();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        System.gc();
    }

    public static void main(String[] args) {
        testGC();
    }
}
