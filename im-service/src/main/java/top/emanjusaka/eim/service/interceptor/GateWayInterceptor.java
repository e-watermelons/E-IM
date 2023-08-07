package top.emanjusaka.eim.service.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.emanjusaka.eim.common.BaseErrorCode;
import top.emanjusaka.eim.common.ResponseVO;
import top.emanjusaka.eim.common.enums.GateWayErrorCode;
import top.emanjusaka.eim.common.exception.ApplicationExceptionEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
@Component
public class GateWayInterceptor implements HandlerInterceptor {

    @Autowired
    IdentityCheck identityCheck;


    //appService -》im接口 -》 userSign
    //appService（gen userSig）

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        if (1 == 1){
//            return true;
//        }

        //获取appId 操作人 userSign
        String appIdStr = request.getParameter("appId");
        if (StrUtil.isBlank(appIdStr)) {
            resp(ResponseVO.errorResponse(GateWayErrorCode
                    .APPID_NOT_EXIST), response);
            return false;
        }

        String identifier = request.getParameter("identifier");
        if (StrUtil.isBlank(identifier)) {
            resp(ResponseVO.errorResponse(GateWayErrorCode
                    .OPERATER_NOT_EXIST), response);
            return false;
        }

        String userSign = request.getParameter("userSign");
        if (StrUtil.isBlank(userSign)) {
            resp(ResponseVO.errorResponse(GateWayErrorCode
                    .USERSIGN_NOT_EXIST), response);
            return false;
        }

        //签名和操作人和appid是否匹配
        ApplicationExceptionEnum applicationExceptionEnum = identityCheck.checkUserSig(identifier, appIdStr, userSign);
        if (applicationExceptionEnum != BaseErrorCode.SUCCESS) {
            resp(ResponseVO.errorResponse(applicationExceptionEnum), response);
            return false;
        }

        return true;
    }


    private void resp(ResponseVO respVo, HttpServletResponse response) {

        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            String resp = JSONObject.toJSONString(respVo);

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Max-Age", "3600");

            writer = response.getWriter();
            writer.write(resp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.checkError();
            }
        }

    }
}
