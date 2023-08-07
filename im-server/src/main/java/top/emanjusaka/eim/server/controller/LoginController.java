package top.emanjusaka.eim.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.emanjusaka.eim.server.common.ResponseVO;
import top.emanjusaka.eim.server.model.req.LoginReq;
import top.emanjusaka.eim.server.model.req.RegisterReq;
import top.emanjusaka.eim.server.service.LoginService;

/**
 * @author: Chackylee
 * @description:
 **/
@RestController
@RequestMapping("v1")
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping("/login")
    public ResponseVO login(@RequestBody @Validated LoginReq req) {

        return loginService.login(req);
    }

    @RequestMapping("/register")
    public ResponseVO register(@RequestBody @Validated RegisterReq req) {
        return loginService.register(req);
    }

}
