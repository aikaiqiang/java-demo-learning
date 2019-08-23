package com.kaywall.test.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年08月06日 13:42
 */
public class ConcurrentHashMapOutCPU {

	static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

	public static void main(String[] args) {

		Map<String, String> map = new ConcurrentHashMap<>();
		map.computeIfAbsent("AaAa", key -> map.computeIfAbsent("BBBB", key2 -> "value"));
	}


	static final int spread(int h) {
		return (h ^ (h >>> 16)) & HASH_BITS;
	}

	static final Map<Integer, Integer> concurrentMap = new ConcurrentHashMap<>();
	static int fibonacci(int i) {
		if (i == 0)
			return i;

		if (i == 1)
			return 1;

		return concurrentMap.computeIfAbsent(i, (key) -> {
			System.out.println("Value is " + key);
			return fibonacci(i - 2) + fibonacci(i - 1);
		});
	}
}
