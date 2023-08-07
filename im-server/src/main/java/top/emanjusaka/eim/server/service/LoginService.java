package top.emanjusaka.eim.server.service;

import top.emanjusaka.eim.server.common.ResponseVO;
import top.emanjusaka.eim.server.model.req.LoginReq;
import top.emanjusaka.eim.server.model.req.RegisterReq;

/**
 * @author xiongwei
 * @description:
 **/
public interface LoginService {

    public ResponseVO login(LoginReq req);

    public ResponseVO register(RegisterReq req);
}
