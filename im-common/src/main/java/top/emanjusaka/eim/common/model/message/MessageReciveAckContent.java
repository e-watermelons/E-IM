package top.emanjusaka.eim.common.model.message;
import lombok.Data;
import top.emanjusaka.eim.common.model.ClientInfo;

/**
 * @description:
 * @author xiongwei
 * @version: 1.0
 */
@Data
public class MessageReciveAckContent extends ClientInfo {

    private Long messageKey;

    private String fromId;

    private String toId;

    private Long messageSequence;


}
