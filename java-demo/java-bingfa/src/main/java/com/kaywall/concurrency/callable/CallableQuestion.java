package com.kaywall.concurrency.callable;

import java.util.concurrent.*;

/**
 *  Callable 可以由单独的线程执行的异步任务
 * @author aikaiqiang
 * @date 2019年07月05日 18:33
 */
public class CallableQuestion {

	/**
	 *  Callable + Future 获取执行结果
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();

		Future<String> future = executor.submit(new Task());
		// 关闭线程池
		executor.shutdown();

		// 获取子线程任务返回结果
		try {
			// get() 阻塞当前线程
			System.out.printf("线程[%s]阻塞。。。 \n", Thread.currentThread().getName());
			String s = future.get();
			System.out.println("子线程结果：" + s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		System.out.println("To End");

	}


	private static class Task implements Callable<String> {

		@Override
		public String call() throws Exception {
			return "子线程返回字符串";
		}
	}
}
