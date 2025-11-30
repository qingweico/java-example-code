package thinking.concurrency.interrupted;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zqw
 * @date 2025/11/30
 */
@Slf4j
class SleepInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // 被其他线程调用 {@link Thread#interrupt()} 方法中断时抛出
                // 正常sleep结束不会抛出此异常
                throw new RuntimeException(e);
            }
            System.out.println("task");
        });
        thread.start();
        System.out.println("main");
        // 注释掉下面的 interrupt 则正常先打印 main 再输出 task
        Thread.sleep(2000);
        thread.interrupt();
    }
}

