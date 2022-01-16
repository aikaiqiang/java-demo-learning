package top.kaywall.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class AppServerHello {

    private final int port;

    public AppServerHello(int port) {
        this.port = port;
    }

    private void run() throws Exception {
        // Netty的Reactor线程池，初始化了一个NioEventLoop数组，用来处理I/O操作,如接受新的连接和读/写数据
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 用于启动NIO服务
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    // ChannelInitializer是一个特殊的处理类，他的目的是帮助使用者配置一个新的Channel,用于把许多自定义的处理类增加到pipline上来
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HandlerServerHello());
                        }
                    });

            // 绑定服务器，该实例将提供有关IO操作的结果或状态的信息
            ChannelFuture future = serverBootstrap.bind().sync();
            System.out.println("在端口" + future.channel().localAddress() + "上开启监听");
            // 阻塞操作，closeFuture()开启了一个channel的监听器（这期间channel在进行各项工作），直到链路断开
            future.channel().closeFuture().sync();
        } finally {
            // 关闭EventLoopGroup并释放所有资源，包括所有创建的线程
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new AppServerHello(28080).run();
    }
}
