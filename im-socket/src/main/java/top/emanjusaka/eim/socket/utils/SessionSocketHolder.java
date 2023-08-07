package top.emanjusaka.eim.socket.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import top.emanjusaka.eim.common.constant.Constants;
import top.emanjusaka.eim.common.enums.ImConnectStatusEnum;
import top.emanjusaka.eim.common.model.UserClientDto;
import top.emanjusaka.eim.common.model.UserSession;
import top.emanjusaka.eim.socket.redis.RedisManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SessionSocketHolder {

    private static final Map<UserClientDto, NioSocketChannel> CHANNELS = new ConcurrentHashMap<>();

    public static void put(Integer appId, String userId, Integer clientType, String imei, NioSocketChannel channel) {
        UserClientDto dto = new UserClientDto();
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        dto.setImei(imei);
        CHANNELS.put(dto, channel);

    }

    public static NioSocketChannel get(Integer appId, String userId, Integer clientType, String imei) {
        UserClientDto dto = new UserClientDto();
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        dto.setImei(imei);
        return CHANNELS.get(dto);
    }

    public static List<NioSocketChannel> get(Integer appId, String id) {

        Set<UserClientDto> channelInfos = CHANNELS.keySet();
        List<NioSocketChannel> channels = new ArrayList<>();

        channelInfos.forEach(channel -> {
            if (channel.getAppId().equals(appId) && id.equals(channel.getUserId())) {
                channels.add(CHANNELS.get(channel));
            }
        });

        return channels;
    }

    public static void remove(Integer appId, String userId, Integer clientType, String imei) {
        UserClientDto dto = new UserClientDto();
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        dto.setImei(imei);
        CHANNELS.remove(dto);
    }

    public static void remove(NioSocketChannel channel) {
        CHANNELS.entrySet().stream().filter(entity -> entity.getValue() == channel).forEach(entry -> CHANNELS.remove(entry.getKey()));
    }

    public static void removeUserSession(NioSocketChannel channel) {
        String userId = (String) channel.attr(AttributeKey.valueOf(Constants.UserId)).get();
        Integer appId = (Integer) channel.attr(AttributeKey.valueOf(Constants.AppId)).get();
        Integer clientType = (Integer) channel.attr(AttributeKey.valueOf(Constants.ClientType)).get();
        String imei = (String) channel.attr(AttributeKey.valueOf(Constants.Imei)).get();
        SessionSocketHolder.remove(appId, userId, clientType, imei);
        RedissonClient redissonClient = RedisManager.getRedissonClient();
        RMap<Object, Object> map = redissonClient.getMap(appId + Constants.RedisConstants.UserSessionConstants + userId);
        map.remove(clientType + ":" + imei);
        channel.close();
    }

    public static void offlineUserSession(NioSocketChannel channel) {
        String userId = (String) channel.attr(AttributeKey.valueOf(Constants.UserId)).get();
        Integer appId = (Integer) channel.attr(AttributeKey.valueOf(Constants.AppId)).get();
        Integer clientType = (Integer) channel.attr(AttributeKey.valueOf(Constants.ClientType)).get();
        String imei = (String) channel.attr(AttributeKey.valueOf(Constants.Imei)).get();
        SessionSocketHolder.remove(appId, userId, clientType, imei);
        RedissonClient redissonClient = RedisManager.getRedissonClient();
        RMap<String, String> map = redissonClient.getMap(appId + Constants.RedisConstants.UserSessionConstants + userId);
        String sessionStr = map.get(clientType.toString());
        if (StrUtil.isNotBlank(sessionStr)) {
            UserSession userSession = JSONObject.parseObject(sessionStr, UserSession.class);
            userSession.setConnectState(ImConnectStatusEnum.OFFLINE_STATUS.getCode());
            map.put(clientType + ":" + imei, JSONObject.toJSONString(userSession));
        }
        channel.close();
    }
}
