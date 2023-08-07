package top.emanjusaka.eim.server.service;

import top.emanjusaka.eim.server.common.ResponseVO;
import top.emanjusaka.eim.server.dao.User;
import top.emanjusaka.eim.server.model.req.RegisterReq;
import top.emanjusaka.eim.server.model.req.SearchUserReq;

public interface UserService {

    public ResponseVO<User> getUserByUserNameAndPassword(String userName, String password);

    public ResponseVO<User> getUserByMobile(String mobile);

    public ResponseVO<User> getUserByUserName(String userName);

    public ResponseVO<User> getUserById(Integer userId);

    public ResponseVO<User> registerUser(RegisterReq req);

    public ResponseVO searchUser(SearchUserReq req);

}
