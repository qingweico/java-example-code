package misc.mdc;

import cn.qingweico.concurrent.pool.ThreadPoolExecutorBuilder;
import lombok.extern.slf4j.Slf4j;
import misc.mdc.runable.MdcRunnable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import cn.qingweico.concurrent.pool.ThreadPoolBuilder;
import cn.qingweico.supplier.RandomDataGenerator;

import java.util.concurrent.ExecutorService;

/**
 * @author zqw
 * @date 2023/11/5
 * @see org.slf4j.MDC
 * MDC : Mapped Diagnostic Context 诊断映射上下文
 * MDC 提供了一种在并发系统中记录日志信息的方式; 它在不同线程中储存不同的值, 保证线程安全的同时, 能够让日志信息具有可追溯性
 */
@Slf4j
public class TraceLog {

    public static void main(String[] args) {
        // logback.xml <pattern></pattern> 增加 %X{TRACE_ID}
        final ExecutorService pool = ThreadPoolBuilder.builder().build();

        final ThreadPoolTaskExecutor poolTaskExecutor = ThreadPoolExecutorBuilder.builder().build();

        pool.execute(new MdcRunnable(() -> log.info("{}", RandomDataGenerator.address())));

        pool.execute(new MdcRunnable(() -> log.info("{}", RandomDataGenerator.address())));

        // No log TraceId If not ues MdcRunnable class wrap
        poolTaskExecutor.execute(() -> log.info("{}", RandomDataGenerator.address()));

        poolTaskExecutor.execute(() -> log.info("{}", RandomDataGenerator.address()));
        pool.shutdown();
    }
}
