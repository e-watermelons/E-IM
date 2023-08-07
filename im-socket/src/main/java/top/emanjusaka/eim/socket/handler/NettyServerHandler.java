package top.emanjusaka.eim.socket.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import feign.Feign;
import feign.Request;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.redisson.api.RMap;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.emanjusaka.eim.agreement.pack.LoginPack;
import top.emanjusaka.eim.agreement.pack.message.ChatMessageAck;
import top.emanjusaka.eim.agreement.pack.user.LoginAckPack;
import top.emanjusaka.eim.agreement.pack.user.UserStatusChangeNotifyPack;
import top.emanjusaka.eim.agreement.protocol.Message;
import top.emanjusaka.eim.agreement.protocol.MessagePack;
import top.emanjusaka.eim.common.ResponseVO;
import top.emanjusaka.eim.common.constant.Constants;
import top.emanjusaka.eim.common.enums.ImConnectStatusEnum;
import top.emanjusaka.eim.common.enums.command.MessageCommand;
import top.emanjusaka.eim.common.enums.command.SystemCommand;
import top.emanjusaka.eim.common.enums.command.UserEventCommand;
import top.emanjusaka.eim.common.model.UserClientDto;
import top.emanjusaka.eim.common.model.UserSession;
import top.emanjusaka.eim.common.model.message.CheckSendMessageReq;
import top.emanjusaka.eim.socket.feign.FeignMessageService;
import top.emanjusaka.eim.socket.publish.MqMessageProducer;
import top.emanjusaka.eim.socket.redis.RedisManager;
import top.emanjusaka.eim.socket.utils.SessionSocketHolder;

import java.net.InetAddress;

public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    private final static Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private Integer brokerId;

    private String logicUrl;
    private FeignMessageService feignMessageService;

    public NettyServerHandler(Integer brokerId, String logicUrl) {
        this.brokerId = brokerId;
        feignMessageService = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Request.Options(1000, 3500))//设置超时时间
                .target(FeignMessageService.class, logicUrl);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        Integer command = msg.getMessageHeader().getCommand();
        //登录command
        if (command == SystemCommand.LOGIN.getCommand()) {
            LoginPack loginPack = JSON.parseObject(JSONObject.toJSONString(msg.getMessagePack()), new TypeReference<LoginPack>() {
            }.getType());
            /** 登录事件 **/
            String userId = loginPack.getUserId();
            /** 为channel设置用户id **/
            ctx.channel().attr(AttributeKey.valueOf(Constants.UserId)).set(userId);
            String clientImei = msg.getMessageHeader().getClientType() + ":" + msg.getMessageHeader().getImei();
            /** 为channel设置client和imel **/
            ctx.channel().attr(AttributeKey.valueOf(Constants.ClientImei)).set(clientImei);
            /** 为channel设置appId **/
            ctx.channel().attr(AttributeKey.valueOf(Constants.AppId)).set(msg.getMessageHeader().getAppId());
            /** 为channel设置ClientType **/
            ctx.channel().attr(AttributeKey.valueOf(Constants.ClientType))
                    .set(msg.getMessageHeader().getClientType());
            /** 为channel设置Imei **/
            ctx.channel().attr(AttributeKey.valueOf(Constants.Imei))
                    .set(msg.getMessageHeader().getImei());

            // 将channel存起来

            //Redis map
            UserSession userSession = new UserSession();
            userSession.setAppId(msg.getMessageHeader().getAppId());
            userSession.setClientType(msg.getMessageHeader().getClientType());
            userSession.setUserId(loginPack.getUserId());
            userSession.setConnectState(ImConnectStatusEnum.ONLINE_STATUS.getCode());
            userSession.setBrokerId(brokerId);
            userSession.setImei(msg.getMessageHeader().getImei());
            try {
                InetAddress localHost = InetAddress.getLocalHost();
                userSession.setBrokerHost(localHost.getHostAddress());
            } catch (Exception e) {
                e.printStackTrace();

            }
            //TODO 存到redis
            RedissonClient redissonClient = RedisManager.getRedissonClient();
            RMap<String, String> map = redissonClient.getMap(msg.getMessageHeader().getAppId() + Constants.RedisConstants.UserSessionConstants + loginPack.getUserId());
            map.put(msg.getMessageHeader().getClientType() + ":" + msg.getMessageHeader().getImei(), JSONObject.toJSONString(userSession));
            SessionSocketHolder.put(msg.getMessageHeader().getAppId(), loginPack.getUserId(), msg.getMessageHeader().getClientType(), msg.getMessageHeader().getImei(), (NioSocketChannel) ctx.channel());
            //
            UserClientDto dto = new UserClientDto();
            dto.setImei(msg.getMessageHeader().getImei());
            dto.setUserId(loginPack.getUserId());
            dto.setClientType(msg.getMessageHeader().getClientType());
            dto.setAppId(msg.getMessageHeader().getAppId());
            RTopic topic = redissonClient.getTopic(Constants.RedisConstants.UserLoginChannel);
            topic.publish(JSONObject.toJSONString(dto));
            UserStatusChangeNotifyPack userStatusChangeNotifyPack = new UserStatusChangeNotifyPack();
            userStatusChangeNotifyPack.setAppId(msg.getMessageHeader().getAppId());
            userStatusChangeNotifyPack.setUserId(loginPack.getUserId());
            userStatusChangeNotifyPack.setStatus(ImConnectStatusEnum.ONLINE_STATUS.getCode());
            MqMessageProducer.sendMessage(userStatusChangeNotifyPack, msg.getMessageHeader(), UserEventCommand.USER_ONLINE_STATUS_CHANGE.getCommand());

            MessagePack<LoginAckPack> loginSuccess = new MessagePack<>();
            LoginAckPack loginAckPack = new LoginAckPack();
            loginAckPack.setUserId(loginPack.getUserId());
            loginSuccess.setCommand(SystemCommand.LOGINACK.getCommand());
            loginSuccess.setData(loginAckPack);
            loginSuccess.setImei(msg.getMessageHeader().getImei());
            loginSuccess.setAppId(msg.getMessageHeader().getAppId());
            ctx.channel().writeAndFlush(loginSuccess);
        } else if (command == SystemCommand.LOGOUT.getCommand()) {
            //删除session
            //redis 删除
            SessionSocketHolder.removeUserSession((NioSocketChannel) ctx.channel());
        } else if (command == SystemCommand.PING.getCommand()) {
            ctx.channel().attr(AttributeKey.valueOf(Constants.ReadTime)).set(System.currentTimeMillis());
        } else if (command == MessageCommand.MSG_P2P.getCommand()) {
            CheckSendMessageReq req = new CheckSendMessageReq();
            req.setAppId(msg.getMessageHeader().getAppId());
            req.setCommand(msg.getMessageHeader().getCommand());
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(msg.getMessagePack()));
            String fromId = jsonObject.getString("fromId");
            String toId = jsonObject.getString("toId");
            req.setToId(toId);
            req.setFromId(fromId);
            ResponseVO responseVO = feignMessageService.checkSendMessage(req);
            if (responseVO.isOk()) {
                MqMessageProducer.sendMessage(msg, command);
            } else {
                ChatMessageAck chatMessageAck = new ChatMessageAck(jsonObject.getString("messageId"));
                responseVO.setData(chatMessageAck);
                MessagePack<ResponseVO> ack = new MessagePack<>();
                ack.setData(responseVO);
                ack.setCommand(MessageCommand.MSG_ACK.getCommand());
                ctx.channel().writeAndFlush(ack);
            }
        } else {
            MqMessageProducer.sendMessage(msg, command);
        }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);

    }
}
