package top.emanjusaka.eim.agreement.pack.message;

import lombok.Data;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
@Data
public class MessageReadedPack {

    private long messageSequence;

    private String fromId;

    private String groupId;

    private String toId;

    private Integer conversationType;
}
