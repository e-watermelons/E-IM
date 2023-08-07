package top.emanjusaka.eim.service.user.model;

import lombok.Data;
import top.emanjusaka.eim.common.model.ClientInfo;

/**
 * @description: status区分是上线还是下线
 * @author xiongwei
 * @version: 1.0
 */
@Data
public class UserStatusChangeNotifyContent extends ClientInfo {


    private String userId;

    //服务端状态 1上线 2离线
    private Integer status;



}
