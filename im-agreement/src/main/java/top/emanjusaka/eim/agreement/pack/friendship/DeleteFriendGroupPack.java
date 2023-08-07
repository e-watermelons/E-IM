package top.emanjusaka.eim.agreement.pack.friendship;

import lombok.Data;

/**
 * @author xiongwei
 * @description: 删除好友分组通知报文
 **/
@Data
public class DeleteFriendGroupPack {
    public String fromId;

    private String groupName;

    /**
     * 序列号
     */
    private Long sequence;
}
