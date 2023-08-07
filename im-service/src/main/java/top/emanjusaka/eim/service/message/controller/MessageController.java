package top.emanjusaka.eim.service.message.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.emanjusaka.eim.common.ResponseVO;
import top.emanjusaka.eim.common.model.SyncReq;
import top.emanjusaka.eim.common.model.message.CheckSendMessageReq;
import top.emanjusaka.eim.service.message.model.req.SendMessageReq;
import top.emanjusaka.eim.service.message.service.MessageSyncService;
import top.emanjusaka.eim.service.message.service.P2PMessageService;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
@RestController
@RequestMapping("v1/message")
public class MessageController {

    @Autowired
    P2PMessageService p2PMessageService;

    @Autowired
    MessageSyncService messageSyncService;

    @RequestMapping("/send")
    public ResponseVO send(@RequestBody @Validated SendMessageReq req, Integer appId) {
        req.setAppId(appId);
        return ResponseVO.successResponse(p2PMessageService.send(req));
    }

    @RequestMapping("/checkSend")
    public ResponseVO checkSend(@RequestBody @Validated CheckSendMessageReq req) {
        return p2PMessageService.imServerPermissionCheck(req.getFromId(), req.getToId()
                , req.getAppId());
    }

    @RequestMapping("/syncOfflineMessage")
    public ResponseVO syncOfflineMessage(@RequestBody
                                         @Validated SyncReq req, Integer appId) {
        req.setAppId(appId);
        return messageSyncService.syncOfflineMessage(req);
    }

}
