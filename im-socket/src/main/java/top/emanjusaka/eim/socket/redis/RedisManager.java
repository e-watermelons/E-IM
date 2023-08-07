package top.emanjusaka.eim.socket.redis;

import top.emanjusaka.eim.agreement.config.BootstrapConfig;
import top.emanjusaka.eim.socket.reciver.UserLoginMessageListener;
import org.redisson.api.RedissonClient;

public class RedisManager {
    private static RedissonClient redissonClient;

    public static void init(BootstrapConfig config) {
        SingleClientStrategy singleClientStrategy = new SingleClientStrategy();
        redissonClient = singleClientStrategy.getRedissonClient(config.getEim().getRedis());
        UserLoginMessageListener userLoginMessageListener = new UserLoginMessageListener(config.getEim().getLoginModel());
        userLoginMessageListener.listenerUserLogin();
    }

    public static RedissonClient getRedissonClient() {
        return redissonClient;
    }
}
