package top.emanjusaka.eim.service.group.model.resp;


import lombok.Data;
import top.emanjusaka.eim.service.group.dao.ImGroupEntity;

import java.util.List;

/**
 * @author xiongwei
 * @description:
 **/
@Data
public class GetJoinedGroupResp {

    private Integer totalCount;

    private List<ImGroupEntity> groupList;

}
