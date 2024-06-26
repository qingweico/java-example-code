package misc.mdc.trace;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * TraceId上下文
 *
 * @author zqw
 * @date 2023/11/5
 */
public class TraceIdContext {

    public static final ThreadLocal<String> CURRENT_TRACE_ID = new InheritableThreadLocal<>();

    public static String generateTraceId() {
        return UUID.randomUUID().toString();
    }

    public static String getTraceId() {
        return MDC.get(TraceIdConstant.TRACE_ID);
    }

    public static void setTraceId(String traceId) {
        MDC.put(TraceIdConstant.TRACE_ID, traceId);
    }

    public static void clearTraceId() {
        CURRENT_TRACE_ID.remove();
    }
}
