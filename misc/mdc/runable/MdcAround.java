package misc.mdc.runable;

import misc.mdc.trace.TraceIdContext;

/**
 * @author zqw
 * @date 2023/11/5
 */
public class MdcAround {

    protected String traceId;
    protected void beforeRun() {
        TraceIdContext.setTraceId(traceId);
    }

    protected void afterRun() {
        TraceIdContext.clearTraceId();
    }
}
