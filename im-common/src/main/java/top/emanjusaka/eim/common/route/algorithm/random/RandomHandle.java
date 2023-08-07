package top.emanjusaka.eim.common.route.algorithm.random;

import top.emanjusaka.eim.common.enums.UserErrorCode;
import top.emanjusaka.eim.common.exception.ApplicationException;
import top.emanjusaka.eim.common.route.RouteHandle;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
public class RandomHandle implements RouteHandle {
    @Override
    public String routeServer(List<String> values, String key) {
        int size = values.size();
        if (size == 0) {
            throw new ApplicationException(UserErrorCode.SERVER_NOT_AVAILABLE);
        }
        int i = ThreadLocalRandom.current().nextInt(size);
        return values.get(i);
    }
}
