package com.kaywall.concurrency.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  障碍器
 * @author aikaiqiang
 * @date 2019年07月04日 17:02
 */
public class CyclicBarrierDemoTwo {


	public static void main(String[] args) throws InterruptedException {
		ExecutorService threadPool = Executors.newFixedThreadPool(5);
		CyclicBarrier barrier = new CyclicBarrier(5, () -> {
			System.out.println("所有子线程执行完毕，开始执行等待任务。。。");

			try {
				System.out.println("模拟主任务");
				for(int j = 0; j < 100000; j++);
				System.out.println("To End");
			} finally {
				// 关闭线程池
				threadPool.shutdown();
			}
		});


		for (int i = 0; i < 10; i++) {
			threadPool.submit(() -> {
				action();
				try {
					// 模拟耗时
					for(int j = 0; j < 20000; j++);
					// 执行完毕，通知障碍器
					barrier.await();

				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			});
		}

	}

	private static void action(){
		System.out.printf("[线程：%s] 正在执行。。。\n", Thread.currentThread().getName());
	}
}
