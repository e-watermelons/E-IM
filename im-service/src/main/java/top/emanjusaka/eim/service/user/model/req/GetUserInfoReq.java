package top.emanjusaka.eim.service.user.model.req;

import lombok.Data;
import top.emanjusaka.eim.common.model.RequestBase;

import java.util.List;


@Data
public class GetUserInfoReq extends RequestBase {

    private List<String> userIds;


}
