package top.emanjusaka.eim.agreement.pack.user;

import lombok.Data;

/**
 * @description:
 * @author xiongwei
 * @version: 1.0
 */
@Data
public class UserCustomStatusChangeNotifyPack {

    private String customText;

    private Integer customStatus;

    private String userId;

}
