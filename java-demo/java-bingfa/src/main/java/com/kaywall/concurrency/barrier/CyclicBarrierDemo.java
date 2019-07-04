package com.kaywall.concurrency.barrier;

import com.kaywall.concurrency.countdownlatch.CountDownLatchQuestion;

import java.util.concurrent.*;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年07月04日 17:02
 */
public class CyclicBarrierDemo {


	public static void main(String[] args) throws InterruptedException {
		ExecutorService threadPool = Executors.newFixedThreadPool(5);
		CyclicBarrier barrier = new CyclicBarrier(5);

		// 启动线程总数 m 为 CyclicBarrier 初始化个数 * n 倍
		for (int i = 0; i < 15; i++) {
			threadPool.submit(() -> {
				action();
				try {
					// 当计数 > 0 时阻塞
					// 5 -4 = 1 > 0 阻塞
					// CyclicBarrier.await() = CountDownLatch.countDown() + await()
					// 计数先 - 1 再判断
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			});
		}

		// 等待 3 秒
		threadPool.awaitTermination(3, TimeUnit.SECONDS);
		// 再次执行CyclicBarrier 初始化次数
		// 不能等执行完成就 reset
		barrier.reset();

		System.out.println("to end");
		// 关闭线程池
		threadPool.shutdown();
	}



	private static void action(){
		System.out.printf("[线程：%s] 正在执行。。。\n", Thread.currentThread().getName());
	}
}
