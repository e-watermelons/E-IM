package top.emanjusaka.eim.service.user.model.req;


import lombok.Data;
import top.emanjusaka.eim.common.model.RequestBase;

import java.util.List;

/**
 * @description:
 * @author xiongwei
 * @version: 1.0
 */
@Data
public class PullUserOnlineStatusReq extends RequestBase {

    private List<String> userList;

}
