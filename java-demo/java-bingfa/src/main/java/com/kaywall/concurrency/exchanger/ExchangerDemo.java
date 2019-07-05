package com.kaywall.concurrency.exchanger;

import java.util.concurrent.Exchanger;

/**
 *  使用 Exchanger 在同一集合点线程间交换数据
 * @author aikaiqiang
 * @date 2019年07月05日 17:01
 */
public class ExchangerDemo {

	public static void main(String[] args) {

		Exchanger exchanger = new Exchanger();
		Message messageA = new Message(1, "消息A");
		Message messageB = new Message(2, "消息B");

		ExchangerRunnable runnableA = new ExchangerRunnable(exchanger, messageA);
		ExchangerRunnable runnableB = new ExchangerRunnable(exchanger, messageB);

		// 启动两个线程
		new Thread(runnableA, "Thread-A").start();
		new Thread(runnableB, "Thread-B").start();

	}


	static class ExchangerRunnable implements Runnable{
		private Exchanger exchanger;
		private Message message;

		public ExchangerRunnable(Exchanger exchanger, Message message) {
			this.exchanger = exchanger;
			this.message = message;
		}

		@Override
		public void run() {
			try {
				Message myselfMessage = this.message;
				// 交换信息
				this.message = (Message) exchanger.exchange(this.message);
				System.out.println(
						"线程" + Thread.currentThread().getName() + " exchanged " + myselfMessage.toString() + " for "
								+ this.message.toString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	static class Message{
		private Integer id;
		private String comment;

		public Message(Integer id, String comment) {
			this.id = id;
			this.comment = comment;
		}

		@Override
		public String toString() {
			return "Message{" + "id=" + id + ", comment='" + comment + '\'' + '}';
		}
	}
}
