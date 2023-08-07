package top.emanjusaka.eim.service.user.model.resp;

import lombok.Data;
import top.emanjusaka.eim.common.model.UserSession;

import java.util.List;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
@Data
public class UserOnlineStatusResp {

    private List<UserSession> session;

    private String customText;

    private Integer customStatus;

}
