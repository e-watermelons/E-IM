package top.emanjusaka.eim.service.user.model.req;

import lombok.Data;
import top.emanjusaka.eim.common.model.RequestBase;

/**
 * @description:
 * @author xiongwei
 * @version: 1.0
 */
@Data
public class SetUserCustomerStatusReq extends RequestBase {

    private String userId;

    private String customText;

    private Integer customStatus;

}
