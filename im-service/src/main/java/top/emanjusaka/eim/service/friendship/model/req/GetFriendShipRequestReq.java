package top.emanjusaka.eim.service.friendship.model.req;
import lombok.Data;
import top.emanjusaka.eim.common.model.RequestBase;

import javax.validation.constraints.NotBlank;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
@Data
public class GetFriendShipRequestReq extends RequestBase {

    @NotBlank(message = "用户id不能为空")
    private String fromId;

}
