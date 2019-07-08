package com.kaywall.concurrency.callable;

import java.util.concurrent.*;

/**
 *  FutureTask:
 * @author aikaiqiang
 * @date 2019年07月08日 11:29
 */
public class FutureTaskQuestion {

	/**
	 * 1. FutureTask 类实现了 RunnableFuture 接口；
	 * 2. RunnableFuture 接口继承了 Runnable 接口和 Future 接口；
	 * 3. 既可以作为 Runnable 被线程执行，又可以作为 Future 得到 Callable 的返回值。
	 */


	/**
	 * 使用 Callable + FutureTask 获取执行结果
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		FutureTask<Integer> futureTask = new FutureTask<>(new Task());
		// 提交子线程任务
		executor.submit(futureTask);
		// 关闭线程池
		executor.shutdown();
		try {
			Integer result = futureTask.get();
			System.out.println("子线程求和 sum = " + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 子线程求和任务
	 */
	private static class Task implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			System.out.println("子线程在进行计算");
			Thread.sleep(3000);
			int sum = 0;
			for(int i=0;i<100;i++)
				sum += i;
			return sum;
		}
	}

}
