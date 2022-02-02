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
     * guava
     *
     * @return {@link ThreadFactory}
     */
    public static ThreadFactory guavaThreadFactory() {
        return new ThreadFactoryBuilder().setNameFormat("pool-thread-%s").build();
    }


    /**
     * apache-common-lang3
     *
     * @return the {@link BasicThreadFactory} implements {@link ThreadFactory}
     */
    public static BasicThreadFactory basicThreadFactory() {
        return new BasicThreadFactory.Builder().namingPattern("pool-thread-%s").build();
    }
}
