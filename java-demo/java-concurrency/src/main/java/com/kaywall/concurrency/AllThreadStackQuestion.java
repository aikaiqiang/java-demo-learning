package com.kaywall.concurrency;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;


/**
 *  打印所有线程状态
 * @author aikaiqiang
 * @date 2019年07月02日 17:43
 */
public class AllThreadStackQuestion {
	public static void main(String[] args) {
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		long[] threadIds = threadMXBean.getAllThreadIds();

		for (long threadId : threadIds) {
			ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
			System.out.println(threadInfo.toString());
		}

	}
}
