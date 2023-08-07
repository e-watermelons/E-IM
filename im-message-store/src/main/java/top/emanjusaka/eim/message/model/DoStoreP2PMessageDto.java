package top.emanjusaka.eim.message.model;

import lombok.Data;
import top.emanjusaka.eim.common.model.message.MessageContent;
import top.emanjusaka.eim.message.dao.ImMessageBodyEntity;

/**
 * @author xiongwei
 * @description:
 **/
@Data
public class DoStoreP2PMessageDto {

    private MessageContent messageContent;

    private ImMessageBodyEntity imMessageBodyEntity;

}
