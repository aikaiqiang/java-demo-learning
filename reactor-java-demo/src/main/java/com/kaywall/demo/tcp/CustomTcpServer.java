package com.kaywall.demo.tcp;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

/**
 *  Tcp server
 * @author aikaiqiang
 * @date 2019年12月16日 13:49
 */
public class CustomTcpServer {

	public static void main(String[] args) {
		// Tcp Server
		DisposableServer server =
				TcpServer.create()
						.host("localhost")
						.port(8080)
						.handle((inbound, outbound) -> outbound.sendString(Mono.just("hello from server")))
						.wiretap(true)
						.bindNow();
		server.onDispose()
				.block();
	}
}
