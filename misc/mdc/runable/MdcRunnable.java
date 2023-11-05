package misc.mdc.runable;

import misc.mdc.trace.TraceIdContext;

/**
 * @author zqw
 * @date 2023/11/5
 */
public class MdcRunnable extends MdcAround implements Runnable {

    private final Runnable runnable;

    public MdcRunnable(Runnable runnable) {
        traceId = TraceIdContext.getTraceId();
        this.runnable = runnable;
    }

    @Override
    public void run() {
        beforeRun();
        runnable.run();
        afterRun();
    }
}
