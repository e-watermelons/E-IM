package top.emanjusaka.eim.common.model.message;


import lombok.Data;
import top.emanjusaka.eim.common.model.ClientInfo;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
@Data
public class MessageContent extends ClientInfo {

    private String messageId;

    private String fromId;

    private String toId;

    private String messageBody;

    private Long messageTime;

    private String extra;

    private Long messageKey;

    private long messageSequence;

}
