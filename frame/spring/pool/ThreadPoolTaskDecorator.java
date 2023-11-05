package frame.spring.pool;

/**
 * The interface Thread pool task decorator
 *
 * @author zqw
 * @date 2023/11/5
 */
public interface ThreadPoolTaskDecorator {

    /**
     * Decorator runnable.
     *
     * @param runnable the runnable
     * @return the runnable
     */
    Runnable decorator(Runnable runnable);
}
