package top.emanjusaka.eim.service.friendship.model.callback;

import lombok.Data;

/**
 * @description:
 * @author xiongwei
 * @version: 1.0
 */
@Data
public class DeleteFriendAfterCallbackDto {

    private String fromId;

    private String toId;
}
