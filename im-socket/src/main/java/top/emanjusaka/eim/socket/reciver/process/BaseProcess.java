package top.emanjusaka.eim.socket.reciver.process;

import io.netty.channel.socket.nio.NioSocketChannel;
import top.emanjusaka.eim.agreement.protocol.MessagePack;
import top.emanjusaka.eim.socket.utils.SessionSocketHolder;

/**
 * @author xiongwei
 */
public abstract class BaseProcess {
    public abstract void processBefore();

    public void process(MessagePack messagePack) {
        processBefore();
        NioSocketChannel channel = SessionSocketHolder.get(messagePack.getAppId(), messagePack.getToId(), messagePack.getClientType(), messagePack.getImei());
        if (channel != null) {
            channel.writeAndFlush(messagePack);
        }
        processAfter();

    }

    public abstract void processAfter();
}
