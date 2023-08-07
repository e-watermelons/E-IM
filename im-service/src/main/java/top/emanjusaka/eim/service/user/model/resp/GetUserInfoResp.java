package top.emanjusaka.eim.service.user.model.resp;

import lombok.Data;
import top.emanjusaka.eim.service.user.dao.ImUserDataEntity;

import java.util.List;

/**
 * @author xiongwei
 * @description:
 **/
@Data
public class GetUserInfoResp {

    private List<ImUserDataEntity> userDataItem;

    private List<String> failUser;


}
