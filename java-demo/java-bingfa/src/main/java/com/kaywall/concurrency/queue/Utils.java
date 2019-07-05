package com.kaywall.concurrency.queue;

import java.util.concurrent.BlockingQueue;

/**
 *  工具
 * @author aikaiqiang
 * @date 2019年07月05日 10:12
 */
public class Utils {


	/**
	 * 打印队列
	 * @param queue
	 */
	public static void printQueue(final BlockingQueue queue){
		queue.stream().forEach(System.out::println);
	}

	/**
	 * 分割线
	 * @param length
	 */
	public static void partingLine(final int length){
		for (int i = 0; i < length; i++) {
			System.out.print("=");
			if(i == length - 1){
				System.out.print("=\n");
			}
		}
	}
}
