package top.emanjusaka.eim.socket.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import top.emanjusaka.eim.agreement.MessageDecoder;
import top.emanjusaka.eim.agreement.MessageEncoder;
import top.emanjusaka.eim.agreement.config.BootstrapConfig;
import top.emanjusaka.eim.socket.handler.HeartbeatHandler;
import top.emanjusaka.eim.socket.handler.NettyServerHandler;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    private BootstrapConfig.TcpConfig config;

    public MyChannelInitializer(BootstrapConfig.TcpConfig config) {
        this.config = config;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //对象传输处理【解码】
//        pipeline.addLast(new ObjDecoder());
        // websocket 基于http协议，所以要有http编解码器
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        // 几乎在netty中的编程，都会使用到此hanler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        pipeline.addLast(new MessageDecoder());
        pipeline.addLast(new MessageEncoder());

        pipeline.addLast(new IdleStateHandler(0, 0, 1));

        pipeline.addLast(new HeartbeatHandler(config.getHeartBeatTime()));

        // ====================== 以下是支持httpWebsocket ======================

        /**
         * websocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
         * 本handler会帮你处理一些繁重的复杂的事
         * 会帮你处理握手动作： handshaking（close, ping, pong） ping + pong = 心跳
         * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 自定义的handler
//        pipeline.addLast(new ChatHandler());
        //在管道中添加我们自己的接收数据实现方法
        pipeline.addLast(new NettyServerHandler(config.getBrokerId(), config.getLogicUrl()));
        //对象传输处理[编码]
//        pipeline.addLast(new ObjEncoder());

    }
}
