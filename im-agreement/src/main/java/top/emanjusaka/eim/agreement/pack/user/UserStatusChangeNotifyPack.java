package top.emanjusaka.eim.agreement.pack.user;

import lombok.Data;
import top.emanjusaka.eim.common.model.UserSession;

import java.util.List;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
@Data
public class UserStatusChangeNotifyPack {

    private Integer appId;

    private String userId;

    private Integer status;

    private List<UserSession> client;

}
