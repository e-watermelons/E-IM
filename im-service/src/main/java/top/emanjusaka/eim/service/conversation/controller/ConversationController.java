package top.emanjusaka.eim.service.conversation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.emanjusaka.eim.common.ResponseVO;
import top.emanjusaka.eim.common.model.SyncReq;
import top.emanjusaka.eim.service.conversation.model.DeleteConversationReq;
import top.emanjusaka.eim.service.conversation.model.UpdateConversationReq;
import top.emanjusaka.eim.service.conversation.service.ConversationService;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
@RestController
@RequestMapping("v1/conversation")
public class ConversationController {

    @Autowired
    ConversationService conversationService;

    @RequestMapping("/deleteConversation")
    public ResponseVO deleteConversation(@RequestBody @Validated DeleteConversationReq
                                                 req, Integer appId, String identifier) {
        req.setAppId(appId);
//        req.setOperater(identifier);
        return conversationService.deleteConversation(req);
    }

    @RequestMapping("/updateConversation")
    public ResponseVO updateConversation(@RequestBody @Validated UpdateConversationReq
                                                 req, Integer appId, String identifier) {
        req.setAppId(appId);
//        req.setOperater(identifier);
        return conversationService.updateConversation(req);
    }

    @RequestMapping("/syncConversationList")
    public ResponseVO syncFriendShipList(@RequestBody @Validated SyncReq req, Integer appId) {
        req.setAppId(appId);
        return conversationService.syncConversationSet(req);
    }

}
