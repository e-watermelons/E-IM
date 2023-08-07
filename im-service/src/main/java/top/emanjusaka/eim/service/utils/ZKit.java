package top.emanjusaka.eim.service.utils;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.emanjusaka.eim.common.constant.Constants;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongwei
 * @description: Zookeeper 工具
 **/
@Component
public class ZKit {

    private static Logger logger = LoggerFactory.getLogger(ZKit.class);

    @Resource
    private ZkClient zkClient;

    /**
     * get all TCP server node from zookeeper
     *
     * @return
     */
    public List<String> getAllTcpNode() {
        List<String> children = zkClient.getChildren(Constants.ImCoreZkRoot + Constants.ImCoreZkRootTcp);
//        logger.info("Query all node =[{}] success.", JSON.toJSONString(children));
        return children;
    }

    /**
     * get all WEB server node from zookeeper
     *
     * @return
     */
    public List<String> getAllWebNode() {
        List<String> children = zkClient.getChildren(Constants.ImCoreZkRoot + Constants.ImCoreZkRootWeb);
//        logger.info("Query all node =[{}] success.", JSON.toJSONString(children));
        return children;
    }
}
