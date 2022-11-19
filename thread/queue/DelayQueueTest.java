package thread.queue;

import org.apache.commons.lang3.builder.ToStringBuilder;
import thread.pool.CustomThreadPool;
import util.constants.Constants;

import javax.annotation.Nonnull;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2021/9/30
 */
public class DelayQueueTest {
    static class DelayItem<T> implements Delayed {

        T value;
        long time;

        public DelayItem(T value, long delay) {
            this.value = value;
            this.time = delay + System.currentTimeMillis();
        }

        @Override
        public long getDelay(@Nonnull TimeUnit unit) {
            return time - System.currentTimeMillis();
        }

        @Override
        @SuppressWarnings("unchecked")
        public int compareTo(@Nonnull Delayed o) {
            return (int) (this.time - ((DelayItem<T>) o).time);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("value", value)
                    .append("time", time)
                    .toString();
        }
    }

    static DelayQueue<DelayItem<Integer>> delay = new DelayQueue<>();

    public static void main(String[] args) {
        ExecutorService pool = CustomThreadPool.newFixedThreadPool(2, 2, 1);

        pool.execute(() -> {
            for (int i = 0; i < Constants.TWENTY; i++) {
                delay.offer(new DelayItem<>(i, i * 1000));
            }
        });

        pool.execute(() -> {
            while (true) {
                try {
                    var item = delay.take();
                    System.out.println(item);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        pool.shutdown();
    }
}
