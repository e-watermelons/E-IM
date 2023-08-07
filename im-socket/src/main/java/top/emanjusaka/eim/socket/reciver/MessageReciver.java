package top.emanjusaka.eim.socket.reciver;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;
import top.emanjusaka.eim.agreement.protocol.MessagePack;
import top.emanjusaka.eim.common.constant.Constants;
import top.emanjusaka.eim.socket.reciver.process.BaseProcess;
import top.emanjusaka.eim.socket.reciver.process.ProcessFactory;
import top.emanjusaka.eim.socket.utils.MqFactory;

import java.io.IOException;

/**
 * @author xiongwei
 */
@Slf4j
public class MessageReciver {
    private static String brokerId;

    private static void startReciverMessage() {
        try {
            Channel channel = MqFactory.getChannel(Constants.RabbitConstants.MessageService2Im + brokerId);
            channel.queueDeclare(Constants.RabbitConstants.MessageService2Im + brokerId, true, false, false, null);
            channel.queueBind(Constants.RabbitConstants.MessageService2Im + brokerId, Constants.RabbitConstants.MessageService2Im + brokerId, brokerId);
            channel.basicConsume(Constants.RabbitConstants.MessageService2Im + brokerId, false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {

                        String msgStr = new String(body);
                        log.info(msgStr);
                        MessagePack messagePack = JSONObject.parseObject(msgStr, MessagePack.class);
                        BaseProcess messageProcess = ProcessFactory.getMessageProcess(messagePack.getCommand());
                        messageProcess.process(messagePack);

                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (Exception e) {
                        e.printStackTrace();
                        channel.basicNack(envelope.getDeliveryTag(), false, false);
                    }
                }
            });
        } catch (Exception e) {
            log.error("接收消息出现异常：{}", e.getCause());

        }
    }

    public static void init() {
        startReciverMessage();
    }

    public static void init(String brokerId) {
        if (StrUtil.isBlank(MessageReciver.brokerId)) {
            MessageReciver.brokerId = brokerId;
        }
        startReciverMessage();
    }

}
