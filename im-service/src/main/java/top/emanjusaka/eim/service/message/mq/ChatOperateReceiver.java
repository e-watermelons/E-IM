package top.emanjusaka.eim.service.message.mq;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import top.emanjusaka.eim.common.constant.Constants;
import top.emanjusaka.eim.common.enums.command.MessageCommand;
import top.emanjusaka.eim.common.model.message.MessageContent;
import top.emanjusaka.eim.common.model.message.MessageReadedContent;
import top.emanjusaka.eim.common.model.message.MessageReciveAckContent;
import top.emanjusaka.eim.common.model.message.RecallMessageContent;
import top.emanjusaka.eim.service.message.service.MessageSyncService;
import top.emanjusaka.eim.service.message.service.P2PMessageService;

import java.util.Map;
import java.util.Objects;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
@Component
public class ChatOperateReceiver {

    private static Logger logger = LoggerFactory.getLogger(ChatOperateReceiver.class);

    @Autowired
    P2PMessageService p2PMessageService;

    @Autowired
    MessageSyncService messageSyncService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = Constants.RabbitConstants.Im2MessageService, durable = "true"),
                    exchange = @Exchange(value = Constants.RabbitConstants.Im2MessageService, durable = "true")
            ), concurrency = "1"
    )
    public void onChatMessage(@Payload Message message,
                              @Headers Map<String, Object> headers,
                              Channel channel) throws Exception {
        String msg = new String(message.getBody(), "utf-8");
        logger.info("CHAT MSG FORM QUEUE ::: {}", msg);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            JSONObject jsonObject = JSON.parseObject(msg);
            Integer command = jsonObject.getInteger("command");
            if (command.equals(MessageCommand.MSG_P2P.getCommand())) {
                //处理消息
                MessageContent messageContent
                        = jsonObject.toJavaObject(MessageContent.class);
                p2PMessageService.process(messageContent);
            } else if (command.equals(MessageCommand.MSG_RECIVE_ACK.getCommand())) {
                //消息接收确认
                MessageReciveAckContent messageContent
                        = jsonObject.toJavaObject(MessageReciveAckContent.class);
                messageSyncService.receiveMark(messageContent);
            } else if (command.equals(MessageCommand.MSG_READED.getCommand())) {
                //消息接收确认
                MessageReadedContent messageContent
                        = jsonObject.toJavaObject(MessageReadedContent.class);
                messageSyncService.readMark(messageContent);
            } else if (Objects.equals(command, MessageCommand.MSG_RECALL.getCommand())) {
//                撤回消息
                RecallMessageContent messageContent = JSON.parseObject(msg, new TypeReference<RecallMessageContent>() {
                }.getType());
                messageSyncService.recallMessage(messageContent);
            }
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            logger.error("处理消息出现异常：{}", e.getMessage());
            logger.error("RMQ_CHAT_TRAN_ERROR", e);
            logger.error("NACK_MSG:{}", msg);
            //第一个false 表示不批量拒绝，第二个false表示不重回队列
            channel.basicNack(deliveryTag, false, false);
        }

    }


}
