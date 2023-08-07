package top.emanjusaka.eim.service.group.model.resp;

import lombok.Data;

/**
 * @author xiongwei
 * @description:
 **/
@Data
public class GetRoleInGroupResp {

    private Long groupMemberId;

    private String memberId;

    private Integer role;

    private Long speakDate;

}
