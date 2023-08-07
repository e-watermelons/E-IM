package top.emanjusaka.eim.common.model.message;

import lombok.Data;

/**
 * @author xiongwei
 * @description:
 * @version: 1.0
 */
@Data
public class DoStoreP2PMessageDto {

    private MessageContent messageContent;

    private ImMessageBody messageBody;

}
