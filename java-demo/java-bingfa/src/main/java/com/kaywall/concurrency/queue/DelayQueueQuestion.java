package com.kaywall.concurrency.queue;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 *  延时队列
 * @author aikaiqiang
 * @date 2019年07月05日 10:15
 */
public class DelayQueueQuestion {
	private static final long NANO_ORIGIN = System.nanoTime();

	/**
	 *  1. DelayQueue 在内部阻止元素，直到某个延迟到期
	 *  2. 延时对列里面的元素 element 必须实现接口 Delayed（DelayedTimer, RunnableScheduledFuture, ScheduledFuture, ScheduledFutureTask）
	 */

	public static void main(String[] args) throws InterruptedException {

//		DelayQueue queue = new DelayQueue();
//		Delayed element1 = new DelayedElement(1000);
//		queue.put(element1);
//		Delayed element2 = queue.take();

		// 延时消息队列
		DelayQueue<DelayMessage> messageDelayQueue = new DelayQueue<DelayMessage>();
		ExecutorService exec = Executors.newFixedThreadPool(2);
		// 启动生产者
		exec.execute(new Producer(messageDelayQueue));

		// 启动消费者
		exec.execute(new Consumer(messageDelayQueue));

		// 关闭线程池
		exec.shutdown();
	}

	static class DelayedElement implements Delayed{

		private static final AtomicLong sequencer = new AtomicLong(0);

		private volatile long time;

		/** Sequence number to break ties FIFO */
		private final long sequenceNumber;

		public DelayedElement(long time) {
			this.time = time;
			this.sequenceNumber = sequencer.getAndIncrement();
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return  unit.convert(time - now(), TimeUnit.NANOSECONDS);
		}

		@Override
		public int compareTo(Delayed o) {
			// compare zero ONLY if same object
			if (o == this) {
				return 0;
			}
			if (o instanceof DelayedElement) {
				DelayedElement x = (DelayedElement)o;
				long diff = time - x.time;
				if (diff < 0) {
					return -1;
				} else if (diff > 0) {
					return 1;
				} else if (sequenceNumber < x.sequenceNumber) {
					return -1;
				}  else {
					return 1;
				}
			}
			long d = (getDelay(TimeUnit.NANOSECONDS) -
					o.getDelay(TimeUnit.NANOSECONDS));
			return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
		}

		/**
		 * 获取当前时间
		 * @return
		 */
		private static long now() {
			return System.nanoTime() - NANO_ORIGIN;
		}

	}


	/**
	 * 自定义延时消息
	 */
	static class DelayMessage implements Delayed {
		private Integer id;
		private String comment;
		/**
		 * 延迟时长
		 */
		private volatile long time;

		public DelayMessage(Integer id, String comment, long time) {
			this.id = id;
			this.comment = comment;
			// 当前时间 + 延时时间
			this.time = TimeUnit.NANOSECONDS.convert(time, TimeUnit.MILLISECONDS) + System.nanoTime();;
		}

		public Integer getId() {
			return id;
		}

		public String getComent() {
			return comment;
		}

		public long getTime() {
			return time;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(this.time - System.nanoTime(), TimeUnit.NANOSECONDS);
		}

		@Override
		public int compareTo(Delayed delayed) {
			if(delayed == null){
				return 0;
			}
			if(delayed instanceof DelayQueueQuestion.DelayMessage){
				// 类型转换
				DelayQueueQuestion.DelayMessage message = (DelayQueueQuestion.DelayMessage) delayed;
				Integer messageId = message.getId();
				if(this.id > messageId){
					return 1;
				}else if(this.id < messageId){
					return -1;
				}else {
					return 0;
				}
			}
			return 0;
		}
	}

	/**
	 * 消费者
	 */
	static class Consumer implements Runnable {
		/**
		 * 延时队列 ,消费者从其中获取消息进行消费
		 */
		private DelayQueue<DelayMessage> queue;

		public Consumer(DelayQueue<DelayMessage> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			while (true) {
				try {
					DelayMessage takeMessage = queue.take();
					System.out.printf("--- 消费时间：%s，消费消息id：%s, 消息体：%s \n",
							DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), takeMessage.getId(), takeMessage.getComent());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 生产者
	 */
	static class Producer implements Runnable {
		/**
		 * 延时队列
		 */
		private DelayQueue<DelayMessage> queue;

		public Producer(DelayQueue<DelayMessage> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			for (int i = 0; i < 20; i++) {
				// 延时消息，延时 3 秒
				long time = 3000;
				if(i >= 10){
					// 后 10 条消息延时 5 秒
					time = 5000;
				}
				DelayMessage message = new DelayMessage(i+1, "消息内容" + (i+1), time);
				System.out.printf("+++ 消息生产时间：%s，消息id：%s, 消息体：%s\n",
						DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), message.getId(), message.getComent());
				queue.add(message);
			}
		}
	}

}
