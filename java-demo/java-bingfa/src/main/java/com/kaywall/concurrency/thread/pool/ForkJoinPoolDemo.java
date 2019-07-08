package com.kaywall.concurrency.thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 *  ForkJoinPool 线程池，将任务拆分执行
 *  两种任务：RecursiveAction （没有返回值）/ RecursiveTask （有返回值）
 * @author aikaiqiang
 * @date 2019年07月08日 12:46
 */
public class ForkJoinPoolDemo {

	/**
	 *
	 */


	public static void main(String[] args) {

		// 拆分执行没有返回值的任务Action
		ForkJoinPool forkJoinPool = new ForkJoinPool(4);
		MyRecursiveAction myRecursiveAction = new MyRecursiveAction(24);
		forkJoinPool.invoke(myRecursiveAction);

		// 拆分执行有返回值的任务Task
		MyRecursiveTask myRecursiveTask = new MyRecursiveTask(128);
		long mergedResult = forkJoinPool.invoke(myRecursiveTask);
		System.out.println("mergedResult = " + mergedResult);

	}

	/**
	 * ForkJoinPool：没有返回值的任务
	 */
	private static class MyRecursiveAction extends RecursiveAction{

		private long workLoad = 0;

		public MyRecursiveAction(long workLoad) {
			this.workLoad = workLoad;
		}



		@Override
		protected void compute() {
			//if work is above threshold, break tasks up into smaller tasks
			if(this.workLoad > 16) {
				System.out.println("Splitting workLoad : " + this.workLoad);

				List<MyRecursiveAction> subTasks = new ArrayList<>();
				subTasks.addAll(createSubTasks());

				for(RecursiveAction subtask : subTasks){
					subtask.fork();
				}
			} else {
				System.out.println("Doing workLoad myself: " + this.workLoad);
			}
		}

		private List<MyRecursiveAction> createSubTasks() {
			List<MyRecursiveAction> subTasks = new ArrayList<>();
			MyRecursiveAction subtask1 = new MyRecursiveAction(this.workLoad / 2);
			MyRecursiveAction subtask2 = new MyRecursiveAction(this.workLoad / 2);

			subTasks.add(subtask1);
			subTasks.add(subtask2);
			return subTasks;
		}

	}


	/**
	 * ForkJoinPool：有返回值的任务
	 */
	private static class MyRecursiveTask extends RecursiveTask<Long>{

		private long workLoad = 0;

		public MyRecursiveTask(long workLoad) {
			this.workLoad = workLoad;
		}

		@Override
		protected Long compute() {
			//if work is above threshold, break tasks up into smaller tasks
			if(this.workLoad > 16) {
				System.out.println("Splitting workLoad : " + this.workLoad);

				List<MyRecursiveTask> subTasks =new ArrayList<MyRecursiveTask>();
				subTasks.addAll(createSubTasks());

				for(MyRecursiveTask subtask : subTasks){
					subtask.fork();
				}

				long result = 0;
				for(MyRecursiveTask subtask : subTasks) {
					result += subtask.join();
				}
				return result;

			} else {
				System.out.println("Doing workLoad myself: " + this.workLoad);
				// workLoad 乘 3
				return workLoad * 4;
			}
		}

		private List<MyRecursiveTask> createSubTasks() {
			List<MyRecursiveTask> subTasks = new ArrayList<MyRecursiveTask>();
			MyRecursiveTask subtask1 = new MyRecursiveTask(this.workLoad / 2);
			MyRecursiveTask subtask2 = new MyRecursiveTask(this.workLoad / 2);

			subTasks.add(subtask1);
			subTasks.add(subtask2);

			return subTasks;
		}
	}

}
