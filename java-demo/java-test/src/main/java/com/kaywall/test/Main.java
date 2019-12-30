package com.kaywall.test;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年07月04日 16:13
 */
public class Main {

	public static void main(String[] args) {
		int[] array = {49,38,65,97,76,13,27,49};
		printArray(array);
		sort(array);
		printArray(array);
		System.out.println("java-test");
	}

	public static void sort(int[] arr){
		for(int i = 1; i < arr.length; i++) {
			//插入的数
			int insertVal = arr[i];
			//被插入的位置(准备和前一个数比较)
			int index = i-1;
			//如果插入的数比被插入的数小
			while(index>=0&&insertVal<arr[index]) {
				//将把 arr[index] 向后移动
				arr[index+1] =arr[index];
				//让 index 向前移动
				index--;
			}
			//把插入的数放入合适位置
			arr[index+1] = insertVal;
		}
	}

	public static void printArray(int[] array){
		for(int i = 1; i < array.length; i++) {
			System.out.print(array[i]);
		}
		System.out.println();
	}
}
