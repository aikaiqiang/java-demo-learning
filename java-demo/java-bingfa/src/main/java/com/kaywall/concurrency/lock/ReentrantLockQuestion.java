package com.kaywall.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 *  E
 *  重进入锁
 * @author aikaiqiang
 * @date 2019年07月03日 13:39
 */
public class ReentrantLockQuestion {

	/**
	 * T1, T2, T3
	 *
	 * T1(lock), T2(park), T3(park)
	 * Waited Queue:  Head -> T2  next -> T3
	 * T1(unlock) -> unpark all
	 * T2(free), T3(free)
	 *
	 * T2(lock), T3(park)
	 * Waited queue: Head T3
	 * T2(unlock) -> unpark all
	 * T3(free)
	 *
	 * T3(lock)
	 * Waited queue：Head -> null
	 * T3(unlock)
	 *
	 *
	 */

	private static ReentrantLock lock = new ReentrantLock();

	public static void main(String[] args) {
		// Thread[main]
		// lock     lock       lock
		// main -> action1 -> action2 -> action3
		synchronizedAction(ReentrantLockQuestion::action1);
	}


	private static void action1(){
		System.out.println("action 1: hello");
		synchronizedAction(ReentrantLockQuestion::action2);
	}

	private static void action2(){
		System.out.println("action 2: hello");
		synchronizedAction(ReentrantLockQuestion::action3);
	}

	private static void action3(){
		System.out.println("action 3: hello");
	}

	private static void synchronizedAction(Runnable runnable){
		lock.lock();
		try {
			runnable.run();
		} finally {
			lock.unlock();
		}
	}
}
