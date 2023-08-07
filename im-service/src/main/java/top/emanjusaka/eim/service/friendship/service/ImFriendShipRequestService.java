package top.emanjusaka.eim.service.friendship.service;

import top.emanjusaka.eim.common.ResponseVO;
import top.emanjusaka.eim.service.friendship.model.req.ApproverFriendRequestReq;
import top.emanjusaka.eim.service.friendship.model.req.FriendDto;
import top.emanjusaka.eim.service.friendship.model.req.ReadFriendShipRequestReq;

public interface ImFriendShipRequestService {

    public ResponseVO addFienshipRequest(String fromId, FriendDto dto, Integer appId);

    public ResponseVO approverFriendRequest(ApproverFriendRequestReq req);

    public ResponseVO readFriendShipRequestReq(ReadFriendShipRequestReq req);

    public ResponseVO getFriendRequest(String fromId, Integer appId);
}
