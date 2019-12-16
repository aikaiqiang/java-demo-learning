package com.kaywall.test.util;

import java.util.function.Consumer;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年12月05日 17:19
 */
public class Try {

	static <T, E extends Exception> Consumer<T> consumerWrapper(Consumer<T> consumer, Class<E> clazz) {

		return i -> {
			try {
				consumer.accept(i);
			} catch (Exception ex) {
				try {
					E exCast = clazz.cast(ex);
					System.err.println(
							"Exception occured : " + exCast.getMessage());
				} catch (ClassCastException ccEx) {
					throw ex;
				}
			}
		};
	}
}
