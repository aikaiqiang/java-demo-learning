package top.kaywall.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


@ChannelHandler.Sharable // 这个注解是为了线程安全
public class HandlerClientHello extends SimpleChannelInboundHandler<ByteBuf> {


    /**
     *
     * @param ctx 通道上下文，代指Channel
     * @param msg 字节序列，通过ByteBuf操作基础的字节数组和缓冲区，因为JDK原生操作字节麻烦、效率低，所以Netty对字节的操作进行了封装
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("接收消息：" + msg.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
