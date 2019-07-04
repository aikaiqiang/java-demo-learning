package com.kaywall.concurrency.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  CountDownLatch 栅栏
 * @author aikaiqiang
 * @date 2019年07月04日 10:44
 */
public class CountDownLatchQuestion {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(5);
		CountDownLatch countDownLatch = new CountDownLatch(5);

		for (int i = 0; i < 5; i++) {
			threadPool.submit(() -> {
				action();
				countDownLatch.countDown();
			});
		}

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			Thread.interrupted();
		}

		System.out.println("to end");
	}

	private static void action(){
		System.out.printf("[线程：%s] 正在执行。。。\n", Thread.currentThread().getName());
	}

}
