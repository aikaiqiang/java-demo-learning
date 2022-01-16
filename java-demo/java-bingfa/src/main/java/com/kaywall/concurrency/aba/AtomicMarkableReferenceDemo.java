package com.kaywall.concurrency.aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceDemo {


    public static void main(String[] args) {
        AtomicMarkableReference atomicMarkableReference = new AtomicMarkableReference(100, false);

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicMarkableReference.compareAndSet(100, 101, atomicMarkableReference.isMarked(), !atomicMarkableReference.isMarked());
            atomicMarkableReference.compareAndSet(101, 100, atomicMarkableReference.isMarked(), !atomicMarkableReference.isMarked());
        }, "t1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean marked = atomicMarkableReference.isMarked();
            boolean res = atomicMarkableReference.compareAndSet(100, 102, marked, !marked);
            System.out.println("mark = " + marked);
            System.out.println("res = " + res);
        }, "t2").start();

    }
}
