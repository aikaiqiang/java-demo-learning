package com.kaywall.demo.mono;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 *  Mono demo
 * @author aikaiqiang
 * @date 2019年12月16日 10:47
 */
public class MonoDemo {

	public static void main(String[] args) {
		Mono.fromSupplier(() -> "Hello").subscribe(System.out::println);
		Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
		Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);


	}
}
