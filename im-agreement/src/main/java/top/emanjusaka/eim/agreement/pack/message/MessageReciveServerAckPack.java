package top.emanjusaka.eim.agreement.pack.message;

import lombok.Data;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
@Data
public class MessageReciveServerAckPack {

    private Long messageKey;

    private String fromId;

    private String toId;

    private Long messageSequence;

    private Boolean serverSend;
}
