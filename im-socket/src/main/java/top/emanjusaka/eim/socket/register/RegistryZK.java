package top.emanjusaka.eim.socket.register;

import lombok.extern.slf4j.Slf4j;
import top.emanjusaka.eim.agreement.config.BootstrapConfig;
import top.emanjusaka.eim.common.constant.Constants;

@Slf4j
public class RegistryZK implements Runnable {
    private ZKit zKit;
    private String ip;
    private BootstrapConfig.TcpConfig tcpConfig;

    public RegistryZK(ZKit zKit, String ip, BootstrapConfig.TcpConfig tcpConfig) {
        this.zKit = zKit;
        this.ip = ip;
        this.tcpConfig = tcpConfig;
    }

    @Override
    public void run() {
        zKit.createRootNode();
        String tcpPath = Constants.ImCoreZkRoot + Constants.ImCoreZkRootTcp + "/" + ip + ":" + tcpConfig.getTcpPort();
        zKit.createNode(tcpPath);
        log.info("Registry zookeeper tcpPath success,msg=[{}]", tcpPath);

        String webPath = Constants.ImCoreZkRoot + Constants.ImCoreZkRootWeb + "/" + ip + ":" + tcpConfig.getWebSocketPort();
        zKit.createNode(webPath);
        log.info("Registry zookeeper webPath success,msg=[{}]", webPath);
    }
}
