package top.emanjusaka.eim.service.group.model.req;

import lombok.Data;
import top.emanjusaka.eim.common.model.RequestBase;

import java.util.List;

/**
 * @author xiongwei
 * @description:
 **/
@Data
public class GetRoleInGroupReq extends RequestBase {

    private String groupId;

    private List<String> memberId;
}
