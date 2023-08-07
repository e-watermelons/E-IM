package top.emanjusaka.eim.message.model;


import lombok.Data;
import top.emanjusaka.eim.common.model.message.GroupChatMessageContent;
import top.emanjusaka.eim.message.dao.ImMessageBodyEntity;

/**
 * @author xiongwei
 * @description:
 **/
@Data
public class DoStoreGroupMessageDto {

    private GroupChatMessageContent groupChatMessageContent;

    private ImMessageBodyEntity imMessageBodyEntity;

}
