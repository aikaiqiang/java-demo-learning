package com.kaywall.concurrency.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.kaywall.concurrency.queue.Utils.printQueue;

/**
 *  阻塞队列
 * @author aikaiqiang
 * @date 2019年07月05日 9:32
 */
public class BlockingQueueQuestion {

	public static void main(String[] args) throws InterruptedException {

		/**
		 * ArrayBlockingQueue
		 *
		 */
		BlockingQueue blockingQueue = new ArrayBlockingQueue<String>(10);
		for (int i = 0; i < 20; i++) {
			// 将指定元素添加到此队列中
			blockingQueue.put("元素" + i);
			System.out.printf("向阻塞队列中添加了元素: %s \n", i);
			if(i > 8){
				//从队列中获取队头元素，并将其移出队列
				System.out.println("从阻塞队列中移除元素：" + blockingQueue.take());
			}
		}

		if (blockingQueue.remainingCapacity() > 0){
			System.out.printf("阻塞队列剩余空间：%s \n", blockingQueue.remainingCapacity());
		}

		// 打印队列中元素
		printQueue(blockingQueue);

		System.out.println("To End");
	}
}
