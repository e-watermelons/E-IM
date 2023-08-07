package top.emanjusaka.eim.service.group.model.callback;

import lombok.Data;
import top.emanjusaka.eim.service.group.model.resp.AddMemberResp;

import java.util.List;

/**
 * @description:
 * @author xiongwei
 * @version: 1.0
 */
@Data
public class AddMemberAfterCallback {
    private String groupId;
    private Integer groupType;
    private String operater;
    private List<AddMemberResp> memberId;
}
