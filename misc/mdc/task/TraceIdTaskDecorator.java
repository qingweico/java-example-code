package misc.mdc.task;

import cn.qingweico.concurrent.pool.ThreadPoolTaskDecorator;
import misc.mdc.trace.TraceIdContext;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zqw
 * @date 2023/11/5
 */
public class TraceIdTaskDecorator implements ThreadPoolTaskDecorator {
    @Override
    public Runnable decorator(Runnable runnable) {
        String traceId = TraceIdContext.getTraceId();
        return () -> {
            if (StringUtils.isNotBlank(traceId)) {
                try {
                    TraceIdContext.setTraceId(traceId);
                    runnable.run();
                } finally {
                    TraceIdContext.clearTraceId();
                }
            } else {
                runnable.run();
            }
        };
    }
}
