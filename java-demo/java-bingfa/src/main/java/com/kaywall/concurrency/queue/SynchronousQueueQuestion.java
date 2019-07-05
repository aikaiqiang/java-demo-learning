package com.kaywall.concurrency.queue;

import java.util.concurrent.SynchronousQueue;

/**
 *  SynchronousQueue : 一个比较特别的队列，由于在线程池方面有所应用
 * @author aikaiqiang
 * @date 2019年07月05日 15:29
 */
public class SynchronousQueueQuestion {


	/**
	 * 1. SynchronousQueue是一个内部只能包含单个元素的队列
	 * 2. 当一个线程往队列 put 元素后，如果没有其他线程 take 元素，当前线程就会一直阻塞
	 * 3. 同理，当一个线程从队列（空队列） take 元素， 如果没有其它线程 put 元素，当前线程就会一直阻塞
	 *
	 *  参考原理：https://blog.csdn.net/yanyan19880509/article/details/52562039
	 * SynchronousQueue直接使用CAS实现线程的安全访问， 不依赖AQS实现并发操作：
	 * 公平模式（TransferQueue）：队尾匹配队头出队，先进先出，体现公平原则
	 * 非公平模式（TransferStack）：
	 *
	 */

	public static void main(String[] args) throws InterruptedException {
		final SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();

		Thread putThread = new Thread(() -> {
			System.out.println("put thread start");
			try {
				queue.put(1);
			} catch (InterruptedException e) {
			}
			System.out.println("put thread end");
		});

		Thread takeThread = new Thread(() -> {
			System.out.println("take thread start");
			try {
				System.out.println("take from putThread: " + queue.take());
			} catch (InterruptedException e) {
			}
			System.out.println("take thread end");
		});

		putThread.start();
		// 等待 1 秒再启动 takeThread
		Thread.sleep(1000);
		takeThread.start();

	}
}
