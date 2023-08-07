package top.emanjusaka.eim.socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MyBizHandler<T> extends SimpleChannelInboundHandler<T> {
    protected Logger logger = LoggerFactory.getLogger(MyBizHandler.class);


    public MyBizHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {

        channelRead(ctx.channel(), msg);
    }

    public abstract void channelRead(Channel channel, T msg);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("客户端连接通知：{}", ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("客户端断开通知：{}", ctx.channel());
//        SocketChannelUtil.removeChannel(ctx.channel().id().toString());
//        SocketChannelUtil.removeChannelGroupByChannel(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("服务端异常断开", cause.getMessage());
        System.out.println(cause.getCause());
        System.out.println(cause.getMessage());
//        SocketChannelUtil.removeChannel(ctx.channel().id().toString());
//        SocketChannelUtil.removeChannelGroupByChannel(ctx.channel());
    }
}
