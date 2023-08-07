package top.emanjusaka.eim.socket.feign;


import feign.Headers;
import feign.RequestLine;
import top.emanjusaka.eim.common.ResponseVO;
import top.emanjusaka.eim.common.model.message.CheckSendMessageReq;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
public interface FeignMessageService {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @RequestLine("POST /message/checkSend")
    public ResponseVO checkSendMessage(CheckSendMessageReq o);

}
