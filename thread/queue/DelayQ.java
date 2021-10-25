package thread.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author:qiming
 * @date: 2021/9/30
 */
public class DelayQ {
    static class DelayItem<T> implements Delayed {

        T value;
        long time;

        public DelayItem(T value, long delay) {
            this.value = value;
            this.time = delay + System.currentTimeMillis();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return time - System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.time - ((DelayItem<T>) o).time);
        }

        @Override
        public String toString() {
            return "DelayItem{" +
                    "value=" + value +
                    ", time=" + time +
                    '}';
        }
    }

    static DelayQueue<DelayItem<Integer>> delay = new DelayQueue<>();

    public static void main(String[] args) {


        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                delay.offer(new DelayItem<>(i, i * 1000));
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    var item = delay.take();
                    System.out.println(item);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
