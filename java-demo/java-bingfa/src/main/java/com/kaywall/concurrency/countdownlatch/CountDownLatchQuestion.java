package com.kaywall.concurrency.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
		CustomCountDownLatch countDownLatch = new CustomCountDownLatch(5);

		// jdk 1.5 实现 CountDownLatch
//		CustomCountDownLatch2 countDownLatch = new CustomCountDownLatch2(5);

		for (int i = 0; i < 5; i++) {
			threadPool.execute(() -> {
				action();
				try {
					// 每个线程睡眠 10s
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				countDownLatch.countDown();
			});
		}

		countDownLatch.await();
		notifyWaitThread();
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
				printLockObject(this);
				if(count < 1){
					return;
				}
				count--;
				// 当 count == 0 唤起阻塞线程
				if(count == 0){
//					notifyAll();
					notify();
					notifyThread();
				}
				printReleaseLockObject(this);
			}
		}


		public void await() throws InterruptedException {
			// 判断阻塞状态并清除
			if(Thread.interrupted()){
				throw  new InterruptedException();
			}
			synchronized (this) {
				printLockObject(this);
				while (count > 0){
					currentWaitThread();
					// 阻塞等待被唤醒
					long waitStart = System.currentTimeMillis();
					// 此时调用 wait() 时， 进入 wait() 方法后，当前线程释放锁, 在从 wait() 返回前，线程与其他线程竞争重新获得锁
					// 只能在同步方法或同步块中调用 wait()方法
					wait();
					// 当被其他线程 notify() / notifyAll() 继续执行下面步骤
					System.out.println("weak up, wait cost = " + (System.currentTimeMillis() - waitStart) + "ms");
				}
				printReleaseLockObject(this);
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
					notifyThread();
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
					currentWaitThread();
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


	private static void currentWaitThread(){
		System.out.printf("[线程：%s] 阻塞等待唤醒。。。\n", Thread.currentThread().getName());
	}


	private static void notifyWaitThread(){
		System.out.printf("[线程：%s] 被唤醒 -_-\n", Thread.currentThread().getName());
	}

	private static void notifyThread(){
		System.out.printf("[线程：%s] 调用notify，唤醒等待线程\n", Thread.currentThread().getName());
	}

	private static void printLockObject(Object lock){
		System.out.printf("[线程：%s] 锁住对象={%s}\n", Thread.currentThread().getName(), lock);
	}

	private static void printReleaseLockObject(Object lock){
		System.out.printf("[线程：%s] 释放锁对象={%s}\n", Thread.currentThread().getName(), lock);
	}

}
