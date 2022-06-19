package thread;

import lombok.extern.slf4j.Slf4j;
import thread.pool.CustomThreadPool;

import java.util.concurrent.ThreadLocalRandom;
@Slf4j
public class ClearThreadLocalStatus {
    private static final CustomThreadPool pool = new CustomThreadPool(1, 1);
    static ThreadLocal<Integer> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        for(int i = 0;i < 10;i++) {
          pool.execute(() -> {
             try {
                 Integer userId = ThreadLocalRandom.current().nextInt(100);
                 log.info("thread: {}, before userId: {}",Thread.currentThread().getName(), tl.get());
                 tl.set(userId);
                 log.info("thread: {}, after userId: {}",Thread.currentThread().getName(), tl.get());
             }finally {
                 // 需要显示清空ThreadLocal中的数据
                  tl.remove();
             }
          });
        }
    }
}
