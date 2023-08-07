package top.emanjusaka.eim.socket.reciver;

import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.listener.MessageListener;
import top.emanjusaka.eim.agreement.protocol.MessagePack;
import top.emanjusaka.eim.common.ClientType;
import top.emanjusaka.eim.common.constant.Constants;
import top.emanjusaka.eim.common.enums.DeviceMultiLoginEnum;
import top.emanjusaka.eim.common.enums.command.SystemCommand;
import top.emanjusaka.eim.common.model.UserClientDto;
import top.emanjusaka.eim.socket.redis.RedisManager;
import top.emanjusaka.eim.socket.utils.SessionSocketHolder;

import java.util.List;

/**
 * 多端同步：1单端登录：一端在线：踢掉除了本clientType + imei 的设备
 * 2双端登录：允许pc/mobile 其中一端登录 + web端 踢掉除了本clientType + imei 以外的web端设备
 * 3 三端登录：允许手机+pc+web，踢掉同端的其他imei 除了web
 * 4 不做任何处理
 *
 * @author xiongwei
 */
@Slf4j
public class UserLoginMessageListener {
    private Integer loginModel;

    public UserLoginMessageListener(Integer loginModel) {
        this.loginModel = loginModel;
    }

    public void listenerUserLogin() {
        RTopic topic = RedisManager.getRedissonClient().getTopic(Constants.RedisConstants.UserLoginChannel);
        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String msg) {
                log.info("收到用户上线通知：" + msg);
                UserClientDto dto = JSONObject.parseObject(msg, UserClientDto.class);
                List<NioSocketChannel> nioSocketChannels = SessionSocketHolder.get(dto.getAppId(), dto.getUserId());
                for (NioSocketChannel nioSocketChannel : nioSocketChannels) {
                    if (loginModel == DeviceMultiLoginEnum.ONE.getLoginMode()) {
                        Integer clientType = (Integer) nioSocketChannel.attr(AttributeKey.valueOf(Constants.ClientType)).get();
                        String imei = (String) nioSocketChannel.attr(AttributeKey.valueOf(Constants.Imei)).get();

                        if (!(clientType + ":" + imei).equals(dto.getClientType() + ":" + dto.getImei())) {
                            //TODO 踢掉客户端
                            // 告诉客户端 其他端登录
                            notifyExit(nioSocketChannel);
                        }

                    } else if (loginModel == DeviceMultiLoginEnum.TWO.getLoginMode()) {
                        if (dto.getClientType() == ClientType.WEB.getCode()) {
                            continue;
                        }
                        Integer clientType = (Integer) nioSocketChannel.attr(AttributeKey.valueOf(Constants.ClientType)).get();
                        if (clientType == ClientType.WEB.getCode()) {
                            continue;
                        }
                        String imei = (String) nioSocketChannel.attr(AttributeKey.valueOf(Constants.Imei)).get();
                        if (!(clientType + ":" + imei).equals(dto.getClientType() + ":" + dto.getImei())) {
                            //TODO 踢掉客户端
                            // 告诉客户端 其他端登录
                            notifyExit(nioSocketChannel);
                        }

                    } else if (loginModel == DeviceMultiLoginEnum.THREE.getLoginMode()) {
                        Integer clientType = (Integer) nioSocketChannel.attr(AttributeKey.valueOf(Constants.ClientType)).get();
                        String imei = (String) nioSocketChannel.attr(AttributeKey.valueOf(Constants.Imei)).get();
                        if (dto.getClientType() == ClientType.WEB.getCode()) {
                            continue;
                        }
                        Boolean isSameClient = false;
                        if ((clientType == ClientType.IOS.getCode() || clientType == ClientType.ANDROID.getCode()) && (dto.getClientType() == ClientType.IOS.getCode() || dto.getClientType() == ClientType.ANDROID.getCode())) {
                            isSameClient = true;
                        }
                        if ((clientType == ClientType.WINDOWS.getCode() || clientType == ClientType.MAC.getCode()) && (dto.getClientType() == ClientType.WINDOWS.getCode() || dto.getClientType() == ClientType.MAC.getCode())) {
                            isSameClient = true;
                        }
                        if (isSameClient && !(clientType + ":" + imei).equals(dto.getClientType() + ":" + dto.getImei())) {
                            //TODO 踢掉客户端
                            // 告诉客户端 其他端登录
                            notifyExit(nioSocketChannel);
                        }

                    }
                }
            }
        });
    }

    private static void notifyExit(NioSocketChannel nioSocketChannel) {
        MessagePack<Object> pack = new MessagePack<>();
        pack.setToId((String) nioSocketChannel.attr(AttributeKey.valueOf(Constants.UserId)).get());
        pack.setUserId((String) nioSocketChannel.attr(AttributeKey.valueOf(Constants.UserId)).get());
        pack.setCommand(SystemCommand.MUTUALLOGIN.getCommand());
        nioSocketChannel.writeAndFlush(pack);
    }
}
