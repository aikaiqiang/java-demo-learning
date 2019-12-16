package com.kaywall.demo.flux;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

/**
 *  Flux Demo
 *  学习地址： https://www.ibm.com/developerworks/cn/java/j-cn-with-reactor-response-encode/index.html
 * @author aikaiqiang
 * @date 2019年12月16日 10:47
 */
public class FluxDemo {


	public static void main(String[] args) {
		Flux.just("Hello", "World").subscribe(System.out::println);
		Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
		Flux.empty().subscribe(System.out::println);
		Flux.range(1, 10).subscribe(System.out::println);
		Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
		Flux.interval(Duration.of(1000, ChronoUnit.MILLIS)).subscribe(System.out::println);

		Flux.generate(sink -> {
			sink.next("Hello");
			sink.complete();
		}).subscribe(System.out::println);

		final Random random = new Random();
		Flux.generate(ArrayList::new, (list, sink) -> {
			int value = random.nextInt(100);
			list.add(value);
			sink.next(value);
			if (list.size() == 10) {
				sink.complete();
			}
			return list;
		}).subscribe(System.out::println);


		Flux.create(sink -> {
			for (int i = 0; i < 10; i++) {
				sink.next(i);
			}
			sink.complete();
		}).subscribe(System.out::println);


		// buffer 相关操作符的使用示例
		System.out.println("buffer 操作符使用示例");
		Flux.range(1, 100).buffer(20).subscribe(System.out::println);
		System.out.println("bufferMillis - start");
		Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).buffer(Duration.of(1001, ChronoUnit.MILLIS)).take(2).toStream().forEach(System.out::println);
		System.out.println("bufferMillis - end");

		// bufferUntil 会一直收集直到 Predicate 返回为 true。使得 Predicate 返回 true 的那个元素可以选择添加到当前集合或下一个集合中
		Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
		Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);


		// filter 操作符使用示例
		System.out.println("filter 操作符使用示例");
		Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);

		// window 操作符使用示例
		System.out.println("window 操作符使用示例");
		Flux.range(1, 100).window(20).subscribe(System.out::println);
		System.out.println("window -start");
		Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).window(Duration.of(1001, ChronoUnit.MILLIS)).take(2).toStream().forEach(System.out::println);


		//   zipWith 操作符使用示例
		System.out.println("zipWith 操作符使用示例");
		Flux.just("a", "b")
				.zipWith(Flux.just("c", "d"))
				.subscribe(System.out::println);
		Flux.just("a", "b")
				.zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
				.subscribe(System.out::println);


		//   take  操作符使用示例
		// take 系列操作符用来从当前流中提取元素
		System.out.println("take 操作符使用示例");
		Flux.range(1, 1000).take(10).subscribe(System.out::println);
		Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
		Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
		Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);

		// reduce 和 reduceWith
		System.out.println("reduce 和 reduceWith 操作符使用示例");
		Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
		Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println);

		// merge 和 mergeSequential 操作符使用示例
		System.out.println("merge 和 mergeSequential 操作符使用示例");
		Flux.merge(Flux.interval(Duration.of(0, ChronoUnit.MILLIS), Duration.of(100, ChronoUnit.MILLIS)).take(5),
				Flux.interval(Duration.of(50, ChronoUnit.MILLIS), Duration.of(100, ChronoUnit.MILLIS)).take(5))
				.toStream()
				.forEach(System.out::println);
		Flux.mergeSequential(Flux.interval(Duration.ofMillis(0), Duration.ofMillis(100)).take(5),
				Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5))
				.toStream()
				.forEach(System.out::println);

		// 热序列: 实时序列
		System.out.println(" ========== 热序列 ==========");
		try {
			final Flux<Long> source = Flux.interval(Duration.ofSeconds(1))
					.take(10)
					.publish()
					.autoConnect();
			source.subscribe();
			Thread.sleep(5000);
			source
					.toStream()
					.forEach(System.out::println);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
