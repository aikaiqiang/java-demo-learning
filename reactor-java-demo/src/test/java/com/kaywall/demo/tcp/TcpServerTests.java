package com.kaywall.demo.tcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaywall.demo.SocketUtils;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.Connection;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import reactor.netty.tcp.TcpClient;
import reactor.netty.tcp.TcpServer;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年12月16日 18:24
 */
public class TcpServerTests {

	final Logger log     = Loggers.getLogger(TcpServerTests.class);
	final int    msgs    = 10;
	final int    threads = 4;

	ExecutorService threadPool;
	CountDownLatch latch;

	@Before
	public void loadEnv() {
		latch = new CountDownLatch(msgs * threads);
		threadPool = Executors.newCachedThreadPool();
	}

	@After
	public void cleanup() {
		threadPool.shutdownNow();
		Schedulers.shutdownNow();
	}

	@Test
	public void tcpServerHandlesJsonPojosOverSsl() throws Exception {
		final CountDownLatch latch = new CountDownLatch(2);

		SelfSignedCertificate cert = new SelfSignedCertificate();
		SslContextBuilder serverOptions = SslContextBuilder.forServer(cert.certificate(), cert.privateKey())
				.sslProvider(SslProvider.JDK);
		SslContext clientOptions = SslContextBuilder.forClient()
				.trustManager(InsecureTrustManagerFactory.INSTANCE)
				.sslProvider(SslProvider.JDK)
				.build();

		log.debug("Using SslContext: {}", clientOptions);

		final TcpServer server =
				TcpServer.create()
						.host("localhost")
						.secure(sslContextSpec -> sslContextSpec.sslContext(serverOptions));

		ObjectMapper m = new ObjectMapper();

		DisposableServer connectedServer = server.handle((in, out) -> {
			in.receive()
					.asByteArray()
					.map(bb -> {
						try {
							return m.readValue(bb, Pojo.class);
						}
						catch (IOException io) {
							throw Exceptions.propagate(io);
						}
					})
					.log("conn")
					.subscribe(data -> {
						if ("John Doe".equals(data.getName())) {
							latch.countDown();
						}
					});

			return out.sendString(Mono.just("Hi"))
					.neverComplete();
		})
				.wiretap(true)
				.bindNow();

		assertNotNull(connectedServer);

		final TcpClient client = TcpClient.create()
				.host("localhost")
				.port(connectedServer.address().getPort())
				.secure(spec -> spec.sslContext(clientOptions));

		Connection connectedClient = client.handle((in, out) -> {
			//in
			in.receive()
					.asString()
					.log("receive")
					.subscribe(data -> {
						if (data.equals("Hi")) {
							latch.countDown();
						}
					});

			//out
			return out.send(Flux.just(new Pojo("John" + " Doe"))
					.map(s -> {
						try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
							m.writeValue(os, s);
							return out.alloc()
									.buffer()
									.writeBytes(os.toByteArray());
						}
						catch (IOException ioe) {
							throw Exceptions.propagate(ioe);
						}
					}))
					.neverComplete();
		})
				.wiretap(true)
				.connectNow();

		assertNotNull(connectedClient);

		assertTrue("Latch was counted down", latch.await(5, TimeUnit.SECONDS));

		connectedClient.disposeNow();
		connectedServer.disposeNow();
	}

	@Test(timeout = 10000)
	public void testHang() {
		DisposableServer httpServer =
				HttpServer.create()
						.port(0)
						.host("0.0.0.0")
						.route(r -> r.get("/data", (request, response) -> response.send(Mono.empty())))
						.wiretap(true)
						.bindNow();

		assertNotNull(httpServer);

		httpServer.disposeNow();
	}

	@Test
	public void exposesRemoteAddress() throws InterruptedException {
		final int port = SocketUtils.findAvailableTcpPort();
		System.out.println("port: " + port);
		final CountDownLatch latch = new CountDownLatch(1);

		DisposableServer server = TcpServer.create()
				.port(port)
				.handle((in, out) -> {
					in.withConnection(c -> {
						InetSocketAddress addr = c.address();
						assertNotNull("remote address is not null", addr.getAddress());
						latch.countDown();
					});

					return Flux.never();
				})
				.wiretap(true)
				.bindNow();

		assertNotNull(server);

		Connection client = TcpClient.create().port(port)
				.handle((in, out) -> out.sendString(Flux.just("Hello World!")))
				.wiretap(true)
				.connectNow();

		assertNotNull(client);

		assertTrue("Latch was counted down", latch.await(5, TimeUnit.SECONDS));

		client.disposeNow();
		server.disposeNow();
	}

	public static class Pojo {

		private String name;

		private Pojo() {
		}

		private Pojo(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Pojo{" + "name='" + name + '\'' + '}';
		}
	}
}
