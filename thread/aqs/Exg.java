package thread.aqs;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author:qiming
 * @date: 2021/10/18
 */
public class Exg {
    private final static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(2);
        // girl
        exec.execute(() -> {
            try {
                // 女生对男生说的话
                String girl = exchanger.exchange("我其实暗恋你很久了......");
                System.out.println("女生说: " + girl);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // boy
        exec.execute(() -> {
            try {
                System.out.println("女生慢慢地从教室里走出来......");
                TimeUnit.SECONDS.sleep(3);
                // 男生对女生说的话
                String boy = exchanger.exchange("我很喜欢你......");
                System.out.println("男生说: " + boy);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        exec.shutdown();
    }
}
