package top.emanjusaka.eim.service.friendship.service;


import top.emanjusaka.eim.common.ResponseVO;
import top.emanjusaka.eim.service.friendship.model.req.AddFriendShipGroupMemberReq;
import top.emanjusaka.eim.service.friendship.model.req.DeleteFriendShipGroupMemberReq;

/**
 * @author xiongwei
 * @description:
 **/
public interface ImFriendShipGroupMemberService {

    public ResponseVO addGroupMember(AddFriendShipGroupMemberReq req);

    public ResponseVO delGroupMember(DeleteFriendShipGroupMemberReq req);

    public int doAddGroupMember(Long groupId, String toId);

    public int clearGroupMember(Long groupId);
}
