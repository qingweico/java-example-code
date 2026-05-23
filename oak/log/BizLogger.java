package oak.log;

import misc.mdc.trace.TraceIdContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * {@link LocationAwareLogger} 通过 FQCN, 当日志底层实现在遍历调用栈时会跳过该类, 继续向上找,
 * 从而在日志中打印出真正发起调用的业务代码位置, 而非包装类自身
 *
 * @author zqw
 * @date 2026/5/23
 */
public class BizLogger {
    /**传入包装类的全限定名*/
    private static final String FQCN = BizLogger.class.getName();

    private final LocationAwareLogger logger;
    private final Marker bizMarker = MarkerFactory.getMarker("BIZ");

    public BizLogger(Class<?> clazz) {
        Logger raw = LoggerFactory.getLogger(clazz);
        if (raw instanceof LocationAwareLogger) {
            this.logger = (LocationAwareLogger) raw;
        } else {
            throw new IllegalStateException("当前日志实现不支持 LocationAwareLogger");
        }
    }

    public void info(String message, Object... args) {

        String traceId = TraceIdContext.getTraceId();

        String enriched = String.format("[trace=%s] %s", traceId, message);

        logger.log(
                bizMarker,
                FQCN,
                LocationAwareLogger.INFO_INT,
                enriched,
                args,
                null
        );
    }

    public void error(String message, Throwable t) {
        String traceId = TraceIdContext.getTraceId();
        String enriched = String.format("[trace=%s] %s", traceId, message);

        logger.log(
                bizMarker,
                FQCN,
                LocationAwareLogger.ERROR_INT,
                enriched,
                null,
                t
        );
    }
}
