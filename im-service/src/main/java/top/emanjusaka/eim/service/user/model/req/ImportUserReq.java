package top.emanjusaka.eim.service.user.model.req;

import lombok.Data;
import top.emanjusaka.eim.common.model.RequestBase;
import top.emanjusaka.eim.service.user.dao.ImUserDataEntity;

import java.util.List;


@Data
public class ImportUserReq extends RequestBase {

    private List<ImUserDataEntity> userData;


}
