package com.kaywall.demo;

import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年12月16日 10:47
 */
public class Application {

	public static void main(String[] args) {

		// Tcp Server
		DisposableServer server =
				TcpServer.create()
						.bindNow();
		server.onDispose()
				.block();
	}
}
