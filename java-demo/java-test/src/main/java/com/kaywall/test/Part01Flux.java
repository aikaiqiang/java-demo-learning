package com.kaywall.test;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年07月08日 10:18
 */
public class Part01Flux {

	@Test
	public void emptyFlux() {
		Flux.empty().subscribe(System.out::println);
	}

	//========================================================================================

	@Test
	public void fooBarFluxFromValues() {
		Flux.just("foo", "bar").subscribe(System.out::println);
	}

	//========================================================================================

	@Test
	public void fooBarFluxFromList() {
		List<String> list = new ArrayList();
		list.add("foo");
		list.add("bar");
		Flux.fromIterable(list).subscribe(System.out::println);
	}

	//========================================================================================

	@Test
	public void errorFlux() {
		Flux.error(new Throwable("打印错误信息")).subscribe(System.out::println);
	}

	//========================================================================================

	@Test
	public void counter() {
		Flux.interval(Duration.ofMillis(1000)).take(10).subscribe(System.out::println);
		Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
	}
}
