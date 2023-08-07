package top.emanjusaka.eim.agreement.pack.conversation;

import lombok.Data;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
@Data
public class UpdateConversationPack {

    private String conversationId;

    private Integer isMute;

    private Integer isTop;

    private Integer conversationType;

    private Long sequence;

}
