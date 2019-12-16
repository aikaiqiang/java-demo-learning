package com.kaywall.demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.time.Duration;

/**
 *  Test
 * @author aikaiqiang
 * @date 2019年12月16日 11:21
 */
public class Test {

	@org.junit.Test
	public void stepVerifierTest() {
		StepVerifier.create(Flux.just("a", "b"))
				.expectNext("a")
				.expectNext("b")
				.verifyComplete();
	}

	@org.junit.Test
	public void stepVerifierWithVirtualTimeTest() {
		StepVerifier.withVirtualTime(() -> Flux.interval(Duration.ofHours(4), Duration.ofDays(1)).take(2))
				.expectSubscription()
				.expectNoEvent(Duration.ofHours(4))
				.expectNext(0L)
				.thenAwait(Duration.ofDays(1))
				.expectNext(1L)
				.verifyComplete();
	}


	@org.junit.Test
	public void testPublisherTest(){
		final TestPublisher<String> testPublisher = TestPublisher.create();
		testPublisher.next("a");
		testPublisher.next("b");
		testPublisher.complete();

		StepVerifier.create(testPublisher)
				.expectNext("a")
				.expectNext("b")
				.expectComplete();
	}
}
