package top.emanjusaka.eim.common.model.message;
import lombok.Data;
import top.emanjusaka.eim.common.model.ClientInfo;

/**
 * @description:
 * @author xiongwei
 * @version: 1.0
 */
@Data
public class MessageReadedContent extends ClientInfo {

    private long messageSequence;

    private String fromId;

    private String groupId;

    private String toId;

    private Integer conversationType;

}
