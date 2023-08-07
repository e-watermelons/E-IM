package top.emanjusaka.eim.service.user.service;

import top.emanjusaka.eim.common.ResponseVO;
import top.emanjusaka.eim.service.user.dao.ImUserDataEntity;
import top.emanjusaka.eim.service.user.model.req.*;
import top.emanjusaka.eim.service.user.model.resp.GetUserInfoResp;

public interface ImUserService {

    public ResponseVO importUser(ImportUserReq req);
    public ResponseVO<GetUserInfoResp> getUserInfo(GetUserInfoReq req);

    public ResponseVO<ImUserDataEntity> getSingleUserInfo(String userId , Integer appId);

    public ResponseVO deleteUser(DeleteUserReq req);

    public ResponseVO modifyUserInfo(ModifyUserInfoReq req);

    public ResponseVO login(LoginReq req);

    ResponseVO getUserSequence(GetUserSequenceReq req);
}
