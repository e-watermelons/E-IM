package top.emanjusaka.eim.message.mq;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
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
import org.springframework.stereotype.Service;
import top.emanjusaka.eim.common.constant.Constants;
import top.emanjusaka.eim.message.dao.ImMessageBodyEntity;
import top.emanjusaka.eim.message.model.DoStoreGroupMessageDto;
import top.emanjusaka.eim.message.service.StoreMessageService;

import java.util.Map;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
@Service
public class StroeGroupMessageReceiver {
    private static Logger logger = LoggerFactory.getLogger(StroeGroupMessageReceiver.class);

    @Autowired
    StoreMessageService storeMessageService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = Constants.RabbitConstants.StoreGroupMessage, durable = "true"),
                    exchange = @Exchange(value = Constants.RabbitConstants.StoreGroupMessage, durable = "true")
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
            DoStoreGroupMessageDto doStoreGroupMessageDto = jsonObject.toJavaObject(DoStoreGroupMessageDto.class);
            ImMessageBodyEntity messageBody = jsonObject.getObject("messageBody", ImMessageBodyEntity.class);
            doStoreGroupMessageDto.setImMessageBodyEntity(messageBody);
            storeMessageService.doStoreGroupMessage(doStoreGroupMessageDto);
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
