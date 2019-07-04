package com.kaywall.concurrency.countdownlatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  CountDownLatch 栅栏
 * @author aikaiqiang
 * @date 2019年07月04日 10:44
 */
public class CountDownLatchQuestion {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService threadPool = Executors.newFixedThreadPool(5);
//		CountDownLatch countDownLatch = new CountDownLatch(5);

		// jdk 1.4 实现 CountDownLatch
//		CustomCountDownLatch countDownLatch = new CustomCountDownLatch(5);

		// jdk 1.5 实现 CountDownLatch
		CustomCountDownLatch2 countDownLatch = new CustomCountDownLatch2(5);

		for (int i = 0; i < 5; i++) {
			threadPool.submit(() -> {
				action();
				countDownLatch.countDown();
			});
		}

		countDownLatch.await();

		System.out.println("to end");
		// 关闭线程池
		threadPool.shutdown();
	}


	/**
	 *  java < 1.5 版本 自定义CountDownLatch
	 */
	private static class CustomCountDownLatch{
		private volatile int count;

		public CustomCountDownLatch(int count) {
			this.count = count;
		}

		public void countDown() {
			synchronized (this) {
				if(count < 1){
					return;
				}
				count--;
				// 当 count == 0 唤起阻塞线程
				if(count == 0){
					notifyAll();
				}
			}
		}

		public void await() throws InterruptedException {
			// 判断阻塞状态并清除
			if(Thread.interrupted()){
				throw  new InterruptedException();
			}
			synchronized (this) {
				while (count > 0){
					wait();
				}
			}
		}
	}


	private static class CustomCountDownLatch2{
		private final ReentrantLock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();
		private volatile int count;

		public CustomCountDownLatch2(int count) {
			this.count = count;
		}

		public void countDown() {
			lock.lock();
			try {
				if(count < 1){
					return;
				}
				count--;
				// 当 count == 0 唤起阻塞线程
				if(count == 0){
					condition.signal();
				}
			} finally {
				lock.unlock();
			}
		}

		public void await() throws InterruptedException {
			// 判断阻塞状态并清除
			if(Thread.interrupted()){
				throw  new InterruptedException();
			}

			lock.lock();
			try {
				while (count > 0){
					condition.await();
				}
			} finally {
				lock.unlock();
			}
		}
	}

	private static void action(){
		System.out.printf("[线程：%s] 正在执行。。。\n", Thread.currentThread().getName());
	}

}
