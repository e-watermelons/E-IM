package top.emanjusaka.eim.service.friendship.model.req;

import lombok.Data;
import top.emanjusaka.eim.common.model.RequestBase;

import javax.validation.constraints.NotBlank;


@Data
public class DeleteFriendReq extends RequestBase {

    @NotBlank(message = "fromId不能为空")
    private String fromId;

    @NotBlank(message = "toId不能为空")
    private String toId;

}
