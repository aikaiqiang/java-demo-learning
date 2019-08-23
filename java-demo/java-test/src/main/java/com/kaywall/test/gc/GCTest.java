package com.kaywall.test.gc;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年08月12日 12:41
 */
public class GCTest {

	static class ReferenceCountingGC{
		public Object instance = null;
		private static final int _1MB = 1024 * 1024;

		private byte[] bigSize = new byte[2 * _1MB];

		public static void main(String[] args) {
			ReferenceCountingGC objectA = new ReferenceCountingGC();
			ReferenceCountingGC objectB = new ReferenceCountingGC();
			objectA.instance = objectB;
			objectB.instance = objectA;
			System.gc();
		}
	}
}
