package thread.aqs;

import thread.pool.ThreadObjectPool;
import util.Print;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Exchanger
 *
 * @author zqw
 * @date 2021/10/18
 */
class JdkExchanger {
    static Exchanger<String> exchanger = new Exchanger<>();
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(2);

    public static void main(String[] args) {

        // girl
        pool.execute(() -> {
            try {
                // 女生对男生说的话
                String girl = exchanger.exchange("我其实暗恋你很久了......");
                Print.grace("女生说", girl);
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            }
        });

        // boy
        pool.execute(() -> {
            try {
                System.out.println("女生慢慢地从教室里走出来......");
                TimeUnit.SECONDS.sleep(3);
                // 男生对女生说的话
                String boy = exchanger.exchange("我很喜欢你......");
                Print.grace("男生说", boy);
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            }
        });
        pool.shutdown();
    }
}
