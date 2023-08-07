package top.emanjusaka.eim.agreement;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import top.emanjusaka.eim.agreement.protocol.Message;
import top.emanjusaka.eim.agreement.utils.ByteBufToMessageUtils;

import java.util.List;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
public class WebSocketMessageDecoder extends MessageToMessageDecoder<BinaryWebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame msg, List<Object> out) throws Exception {

        ByteBuf content = msg.content();
        if (content.readableBytes() < 28) {
            return;
        }
        Message message = ByteBufToMessageUtils.transition(content);
        if (message == null) {
            return;
        }
        out.add(message);
    }
}
