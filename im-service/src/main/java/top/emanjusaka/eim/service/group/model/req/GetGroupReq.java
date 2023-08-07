package top.emanjusaka.eim.service.group.model.req;

import lombok.Data;
import top.emanjusaka.eim.common.model.RequestBase;

/**
 * @author xiongwei
 * @description:
 **/
@Data
public class GetGroupReq extends RequestBase {

    private String groupId;

}
