package top.emanjusaka.eim.service.friendship.model.req;

import lombok.Data;
import top.emanjusaka.eim.common.model.RequestBase;


@Data
public class ApproverFriendRequestReq extends RequestBase {

    private Long id;

    //1同意 2拒绝
    private Integer status;
}
