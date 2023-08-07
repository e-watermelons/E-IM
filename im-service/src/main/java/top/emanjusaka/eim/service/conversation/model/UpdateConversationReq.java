package top.emanjusaka.eim.service.conversation.model;

import lombok.Data;
import top.emanjusaka.eim.common.model.RequestBase;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
@Data
public class UpdateConversationReq extends RequestBase {

    private String conversationId;

    private Integer isMute;

    private Integer isTop;

    private String fromId;


}
