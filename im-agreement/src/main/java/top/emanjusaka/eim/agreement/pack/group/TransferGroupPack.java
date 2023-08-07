package top.emanjusaka.eim.agreement.pack.group;

import lombok.Data;

/**
 * @author xiongwei
 * @description: 转让群主通知报文
 **/
@Data
public class TransferGroupPack {

    private String groupId;

    private String ownerId;

}
