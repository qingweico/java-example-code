package thread.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * Customizable ThreadFactory
 *
 * @author zqw
 * @date 2022/2/2
 */
public class CustomThreadFactory {
    /**
     * Set Daemon Thread
     *
     * @return {@link ThreadFactory}
     */
    public static ThreadFactory guavaThreadFactory(boolean daemon) {
        return new ThreadFactoryBuilder().setNameFormat("[guava]pool-thread-%s").setDaemon(daemon).build();
    }
    /**
     * guava
     *
     * @return {@link ThreadFactory}
     */
    public static ThreadFactory guavaThreadFactory() {
        return guavaThreadFactory(false);
    }


    /**
     * apache-common-lang3
     *
     * @return the {@link BasicThreadFactory} implements {@link ThreadFactory}
     */
    public static BasicThreadFactory basicThreadFactory() {
        return new BasicThreadFactory.Builder().namingPattern("[lang3]pool-thread-%s").build();
    }
}
