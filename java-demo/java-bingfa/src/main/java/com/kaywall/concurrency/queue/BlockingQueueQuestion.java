package com.kaywall.concurrency.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import static com.kaywall.concurrency.Utils.printQueue;

/**
 *  阻塞队列
 * @author aikaiqiang
 * @date 2019年07月05日 9:32
 */
public class BlockingQueueQuestion {

	public static void main(String[] args) throws InterruptedException {

		// ArrayBlockingQueue
		arrayBlockingQueueTest();

		// LinkedBlockingQueueTest
		linkedBlockingQueueTest();

		// PriorityBlockingQueue
		priorityBlockingQueueTest();

		System.out.println("To End");
	}

	private static void arrayBlockingQueueTest() throws InterruptedException {
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
	}

	/**
	 *
	 * @throws InterruptedException
	 */
	private static void linkedBlockingQueueTest() throws InterruptedException {
		/**
		 * 无界队列
		 */
		BlockingQueue<String> unBoundedQueue = new LinkedBlockingQueue<String>();

		/**
		 * 有界队列
		 */
		BlockingQueue<String> boundedQueue  = new LinkedBlockingQueue<String>(1024);

		unBoundedQueue.put("Value");
		String take = unBoundedQueue.take();

		boundedQueue.put("test");
		String value = boundedQueue.take();
	}


	/**
	 *  无界阻塞优先级队列
	 *  内容元素 element 必须实现 Comparable 接口
	 * @throws InterruptedException
	 */
	private static void priorityBlockingQueueTest() throws InterruptedException {
		/**
		 * 无界队列
		 */
		PriorityBlockingQueue<String> priorityBlockingQueue = new PriorityBlockingQueue<String>();

		for(int i = 9; i > 0; i--){
			priorityBlockingQueue.add("" + i);
		}

		for(int i = 0; i < 12; i++){
			if(!priorityBlockingQueue.isEmpty()){
				System.out.println(priorityBlockingQueue.take());
			}
			else {
				System.out.println("队列已经空了。。。");
			}
		}
	}
}
