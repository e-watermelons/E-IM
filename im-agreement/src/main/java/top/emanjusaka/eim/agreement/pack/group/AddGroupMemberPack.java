package top.emanjusaka.eim.agreement.pack.group;

import lombok.Data;

import java.util.List;

/**
 * @author xiongwei
 * @description: 群内添加群成员通知报文
 **/
@Data
public class AddGroupMemberPack {

    private String groupId;

    private List<String> members;

}
