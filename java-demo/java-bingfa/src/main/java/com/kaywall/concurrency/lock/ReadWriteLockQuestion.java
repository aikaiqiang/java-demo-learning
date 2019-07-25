package com.kaywall.concurrency.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年07月10日 9:36
 */
public class ReadWriteLockQuestion {

	public static void main(String[] args) {
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

		readWriteLock.readLock().lock();
		// multiple readers can enter this section
		// if not locked for writing, and not writers waiting
		// to lock for writing.
		readWriteLock.readLock().unlock();


		readWriteLock.writeLock().lock();
		// only one writer can enter this section,
		// and only if no threads are currently reading.
		readWriteLock.writeLock().unlock();
	}
}
