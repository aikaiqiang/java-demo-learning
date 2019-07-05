package com.kaywall.concurrency.queue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static com.kaywall.concurrency.Utils.partingLine;
import static com.kaywall.concurrency.Utils.printQueue;

/**
 *  阻塞双端队列： Double Ended Queue
 * @author aikaiqiang
 * @date 2019年07月05日 9:44
 */
public class BlockingDequeQuestion {

	public static void main(String[] args) {

		// LinkedBlockingDeque
		BlockingDeque<String> deque = new LinkedBlockingDeque<String>(5);

		// 头部放置 2 个元素
		for (int i = 0; i < 2; i++) {
			deque.addFirst("字符串-" + i);
		}

		// 尾部放置 3 个元素
		for (int i = 2; i < 5; i++) {
			deque.addLast("字符串-" + i);
		}

		// 打印队列
		printQueue(deque);
		partingLine(30);

		try {
			// 取出第一个
			String first = deque.takeFirst();
			System.out.println("first = " + first);
			// 取出最后一个
			String last = deque.takeLast();
			System.out.println("last = " + last);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		partingLine(30);
		printQueue(deque);
	}


}
