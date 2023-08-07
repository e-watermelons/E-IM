package top.emanjusaka.eim.common.route.algorithm.loop;

import top.emanjusaka.eim.common.enums.UserErrorCode;
import top.emanjusaka.eim.common.exception.ApplicationException;
import top.emanjusaka.eim.common.route.RouteHandle;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description:
 * @version: 1.0
 */
public class LoopHandle implements RouteHandle {

    private AtomicLong index = new AtomicLong();

    @Override
    public String routeServer(List<String> values, String key) {
        int size = values.size();
        if (size == 0) {
            throw new ApplicationException(UserErrorCode.SERVER_NOT_AVAILABLE);
        }
        Long l = index.incrementAndGet() % size;
        if (l < 0) {
            l = 0L;
        }
        return values.get(l.intValue());
    }
}
