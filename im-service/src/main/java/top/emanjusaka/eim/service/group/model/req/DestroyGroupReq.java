package top.emanjusaka.eim.service.group.model.req;

import lombok.Data;
import top.emanjusaka.eim.common.model.RequestBase;

import javax.validation.constraints.NotNull;

/**
 * @author xiongwei
 * @description:
 **/
@Data
public class DestroyGroupReq extends RequestBase {

    @NotNull(message = "群id不能为空")
    private String groupId;

}
