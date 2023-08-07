package top.emanjusaka.eim.service.friendship.model.req;
import lombok.Data;
import top.emanjusaka.eim.common.model.RequestBase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author xiongwei
 * @description: 删除分组，同时删除分组下的成员
 **/
@Data
public class DeleteFriendShipGroupReq extends RequestBase {

    @NotBlank(message = "fromId不能为空")
    private String fromId;

    @NotEmpty(message = "分组名称不能为空")
    private List<String> groupName;

}
