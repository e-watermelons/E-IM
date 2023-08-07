package top.emanjusaka.eim.service.friendship.service;


import top.emanjusaka.eim.common.ResponseVO;
import top.emanjusaka.eim.service.friendship.dao.ImFriendShipGroupEntity;
import top.emanjusaka.eim.service.friendship.model.req.AddFriendShipGroupReq;
import top.emanjusaka.eim.service.friendship.model.req.DeleteFriendShipGroupReq;

/**
 * @author xiongwei
 * @description:
 **/
public interface ImFriendShipGroupService {

    public ResponseVO addGroup(AddFriendShipGroupReq req);

    public ResponseVO deleteGroup(DeleteFriendShipGroupReq req);

    public ResponseVO<ImFriendShipGroupEntity> getGroup(String fromId, String groupName, Integer appId);

    public Long updateSeq(String fromId, String groupName, Integer appId);
}
