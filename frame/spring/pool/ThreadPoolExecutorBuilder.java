package frame.spring.pool;

import io.micrometer.core.instrument.util.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import thread.pool.CustomizableRejectedExecutionHandler;

import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * @author zqw
 * @date 2023/11/5
 */
@Slf4j
public class ThreadPoolExecutorBuilder {
    public static Builder builder() {
        return new ThreadPoolExecutorBuilder.Builder();
    }

    @SuppressWarnings("unused")
    public static class Builder {
        /**
         * default corePoolSize 10
         */
        private int corePoolSize = 10;
        /**
         * default maxPoolSize 16
         */
        private int maxPoolSize = 16;
        /**
         * default keepAliveSeconds 60
         */
        private int keepAliveSeconds = 60;
        /**
         * default queueCapacity 100
         */
        private int queueCapacity = 100;
        private boolean preStartAllCore = false;
        private int awaitTerminationSeconds = 0;
        private boolean allowCoreThreadTimeOut = false;
        private List<ThreadPoolTaskDecorator> decorators;
        private boolean waitForTasksToCompleteOnShutdown = true;
        private ThreadFactory threadFactory = new NamedThreadFactory("[micrometer]-pool-thread");
        public Builder corePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
            return this;
        }

        public Builder maxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
            return this;
        }


        public Builder threadFactory(ThreadFactory threadFactory) {
            this.threadFactory = threadFactory;
            return this;
        }

        public Builder preStartAllCore(boolean preStartAllCore) {
            this.preStartAllCore = preStartAllCore;
            return this;
        }


        public Builder keepAliveSeconds(int keepAliveSeconds) {
            this.keepAliveSeconds = keepAliveSeconds;
            return this;
        }


        public Builder queueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
            return this;
        }


        public Builder allowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
            this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
            return this;
        }


        public Builder waitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
            this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
            return this;
        }


        public Builder awaitTerminationSeconds(int awaitTerminationSeconds) {
            this.awaitTerminationSeconds = awaitTerminationSeconds;
            return this;
        }

        public Builder decorators(List<ThreadPoolTaskDecorator> decorators) {
            this.decorators = decorators;
            return this;
        }

        public ThreadPoolTaskExecutor build() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            if (CollectionUtils.isNotEmpty(decorators)) {
                executor.setTaskDecorator(new TaskChainDecorator(decorators));
            }
            executor.setCorePoolSize(this.corePoolSize);
            executor.setMaxPoolSize(this.maxPoolSize);
            // queueCapacity > 0 ? LinkedBlockingQueue : SynchronousQueue
            executor.setQueueCapacity(this.queueCapacity);
            // 默认为60s, Unit 单位无法修改
            executor.setKeepAliveSeconds(this.keepAliveSeconds);
            executor.setThreadFactory(this.threadFactory);
            executor.setWaitForTasksToCompleteOnShutdown(this.waitForTasksToCompleteOnShutdown);
            executor.setRejectedExecutionHandler(new CustomizableRejectedExecutionHandler());
            executor.setPrestartAllCoreThreads(this.preStartAllCore);
            executor.setAllowCoreThreadTimeOut(this.allowCoreThreadTimeOut);
            executor.setAwaitTerminationSeconds(this.awaitTerminationSeconds);
            executor.initialize();
            return executor;
        }
    }
}
