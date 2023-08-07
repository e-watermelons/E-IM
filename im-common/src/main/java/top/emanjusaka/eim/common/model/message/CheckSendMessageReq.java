package top.emanjusaka.eim.common.model.message;

import lombok.Data;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
@Data
public class CheckSendMessageReq {

    private String fromId;

    private String toId;

    private Integer appId;

    private Integer command;

}
