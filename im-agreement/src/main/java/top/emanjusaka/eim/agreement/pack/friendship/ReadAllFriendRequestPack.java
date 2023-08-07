package top.emanjusaka.eim.agreement.pack.friendship;

import lombok.Data;

/**
 * @author xiongwei
 * @description: 已读好友申请通知报文
 **/
@Data
public class ReadAllFriendRequestPack {

    private String fromId;

    private Long sequence;
}
