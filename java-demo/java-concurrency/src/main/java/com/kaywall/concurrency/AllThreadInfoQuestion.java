package com.kaywall.concurrency;

import com.sun.management.ThreadMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;

/**
 *  打印所有线程状态
 * @author aikaiqiang
 * @date 2019年07月02日 17:43
 */
public class AllThreadInfoQuestion {
	public static void main(String[] args) {

		ThreadMXBean threadMXBean = (com.sun.management.ThreadMXBean)ManagementFactory.getThreadMXBean();
		long[] threadIds = threadMXBean.getAllThreadIds();

		for (long threadId : threadIds) {
			ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
			System.out.printf("线程[ID: %d] 线程信息：%s \n", threadId, threadInfo.toString());
			long bytes = threadMXBean.getThreadAllocatedBytes(threadId);
			long KB = bytes / 1024;
			System.out.printf("线程[ID: %d] 分配内存：%s KB \n", threadId, KB);
		}

	}
}
