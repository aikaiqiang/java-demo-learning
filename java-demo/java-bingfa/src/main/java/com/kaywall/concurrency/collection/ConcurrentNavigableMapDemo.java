package com.kaywall.concurrency.collection;

import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static com.kaywall.concurrency.Utils.partingLine;

/**
 *  线程安全，并发有序 map
 * @author aikaiqiang
 * @date 2019年07月05日 16:28
 */
public class ConcurrentNavigableMapDemo {

	public static void main(String[] args) {
		ConcurrentNavigableMap concurrentNavigableMap = new ConcurrentSkipListMap();

		concurrentNavigableMap.put("1", "one");
		concurrentNavigableMap.put("2", "two");
		concurrentNavigableMap.put("3", "three");
		concurrentNavigableMap.put("4", "four");

		// headMap(T toKey): less than the given key.
		// 获取 key 小于 toKey 的元素
		ConcurrentNavigableMap headMap = concurrentNavigableMap.headMap("2");
		printKey(headMap);
		printKeyAndValue(headMap);

		// 分割线
		partingLine(40);
		// tailMap(T fromKey): greater than or equal to the given fromKey
		// 获取 key 小于或等于 fromKey 的元素
		ConcurrentNavigableMap tailMap = concurrentNavigableMap.tailMap("2");
		printKey(tailMap);
		printKeyAndValue(tailMap);


		// 分割线
		partingLine(40);
		// subMap(T fromKey, T toKey): greater than or equal to fromKey, and smaller than toKey
		// 大于等于 fromKey，小于toKey
		ConcurrentNavigableMap subMap = concurrentNavigableMap.subMap("2", "4");
		printKey(subMap);
		printKeyAndValue(subMap);
	}

	/**
	 * 打印 ConcurrentNavigableMap key
	 * @param map
	 */
	public static void printKey(ConcurrentNavigableMap map){
		NavigableSet navigableSet = map.navigableKeySet();
		navigableSet.stream().forEach(System.out::println);
	}

	/**
	 * 打印 ConcurrentNavigableMap key/value
	 * @param map
	 */
	public static void printKeyAndValue(ConcurrentNavigableMap map){
		Set<Map.Entry<String, String>> entrySet = map.entrySet();
		for (Map.Entry<String, String> entry : entrySet) {
			System.out.printf("key = %s ;value = %s \n", entry.getKey(), entry.getKey());
		}
	}

}
