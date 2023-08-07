package top.emanjusaka.eim.socket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import top.emanjusaka.eim.agreement.config.BootstrapConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("nettyServer")
public class NettyServer {
    private Logger logger = LoggerFactory.getLogger(NettyServer.class);
    BootstrapConfig.TcpConfig config;
    EventLoopGroup parentGroup;
    EventLoopGroup childGroup;
    ServerBootstrap server;

    public NettyServer(BootstrapConfig.TcpConfig config) {
        this.config = config;
        parentGroup = new NioEventLoopGroup(config.getBossThreadSize());
        childGroup = new NioEventLoopGroup(config.getWorkThreadSize());
        server = new ServerBootstrap();
        server.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 10240) // 服务端可连接队列大小
                .option(ChannelOption.SO_REUSEADDR, true) // 参数表示允许重复使用本地地址和端口
                .childOption(ChannelOption.TCP_NODELAY, true) // 是否禁用Nagle算法 简单点说是否批量发送数据 true关闭 false开启。 开启的话可以减少一定的网络开销，但影响消息实时性
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 保活开关2h没有数据服务端会发送心跳包
                .childHandler(new MyChannelInitializer(config));
    }

    public void start() {
        logger.info("启动服务端口号{}", this.config.getTcpPort());
        this.server.bind(this.config.getTcpPort());
    }
}
