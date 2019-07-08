package com.kaywall.concurrency.automic;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *  原子性操作变量
 * @author aikaiqiang
 * @date 2019年07月08日 14:47
 */
public class AutomicQuestion {

	/**
	 * Automic** 使用 Jvm CAS 原子性操作修改内存内容, 通过 Unsafe 类编辑
	 */

	public static void main(String[] args) {
		AtomicBoolean atomicBoolean = new AtomicBoolean(true);

		atomicBoolean.getAndSet(false);
	}
}
