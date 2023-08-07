package top.emanjusaka.eim.socket;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import top.emanjusaka.eim.agreement.config.BootstrapConfig;
import top.emanjusaka.eim.socket.reciver.MessageReciver;
import top.emanjusaka.eim.socket.redis.RedisManager;
import top.emanjusaka.eim.socket.register.RegistryZK;
import top.emanjusaka.eim.socket.register.ZKit;
import top.emanjusaka.eim.socket.server.NettyServer;
import top.emanjusaka.eim.socket.utils.MqFactory;
import top.emanjusaka.eim.socket.websocket.WebSocketServer;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Starter {
    private Logger logger = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) {
//        start("/Users/xiongwei/project/java/E-IM-Server/im-socket/src/main/resources/config.yml");

        if (args.length > 0) {
            start(args[0]);
        }
    }

    private static void start(String path) {
        Yaml yaml = new Yaml();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            BootstrapConfig bootstrapConfig = yaml.loadAs(inputStream, BootstrapConfig.class);
            new NettyServer(bootstrapConfig.getEim()).start();
            new WebSocketServer(bootstrapConfig.getEim()).start();
            RedisManager.init(bootstrapConfig);
            MqFactory.init(bootstrapConfig.getEim().getRabbitmq());
            MessageReciver.init(bootstrapConfig.getEim().getBrokerId().toString());
            registerZK(bootstrapConfig);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(500);
        }
    }

    public static void registerZK(BootstrapConfig config) throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        ZkClient zkClient = new ZkClient(config.getEim().getZkConfig().getZkAddr(), config.getEim().getZkConfig().getZkConnectTimeOut());
        ZKit zKit = new ZKit(zkClient);
        RegistryZK registryZK = new RegistryZK(zKit, hostAddress, config.getEim());
        Thread thread = new Thread(registryZK);
        thread.start();
    }
}
