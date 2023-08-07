package top.emanjusaka.eim.service.friendship.model.callback;

import lombok.Data;
import top.emanjusaka.eim.service.friendship.model.req.FriendDto;

/**
 * @description:
 * @author xiongwei
 * @version: 1.0
 */
@Data
public class AddFriendAfterCallbackDto {

    private String fromId;

    private FriendDto toItem;
}
