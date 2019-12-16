package com.kaywall.demo.tcp;

import io.netty.handler.timeout.ReadTimeoutHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年12月16日 13:54
 */
public class CustomTcpClient {


	public static void main(String[] args) {

		Connection connection =
				TcpClient.create()
						.doOnConnect(conn -> System.out.println(">>>链接前的操作"))
//						.doOnConnected(conn ->
//								conn.addHandler(new ReadTimeoutHandler(10, TimeUnit.SECONDS)))
						.doOnDisconnected(conn -> System.out.println("断开链接之后的操作"))
						.host("localhost")
						.port(8080)
						.handle((inbound, outbound) -> outbound.sendString(Mono.just("hello world from client")))
						.handle((inbound, outbound) -> inbound.receive().then())
						.wiretap(true)
						.connectNow();

		connection.onDispose()
				.block();

	}
}
