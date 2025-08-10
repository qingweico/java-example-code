package jvm;

import cn.qingweico.datetime.DateUtil;
import cn.qingweico.io.Print;

/**
 * 可数循环中的Safepoint问题
 * t1 输出暂停, t2 的循环阻塞了 Safepoint, 导致所有线程暂停
 * 存在JDK8中的特定行为(JDK8以后没有了此问题)
 * 在 JDK 8 中可数循环默认不会插入 Safepoint 检查, 除非使用 VM args: {@code -XX:+UseCountedLoopSafepoints}
 * 双重循环会被优化掉, 循环内部没有 Safepoint 检查, 只有当循环结束时, t2 才能到达 Safepoint, 其他线程才能继续
 * 从 JDK 9 开始, JVM 默认对所有循环(无论是否可数)插入 Safepoint 检查
 * 可数循环是指循环次数可以在循环开始前确定的循环
 * @author zqw
 * @date 2025/8/7
 */
class CountableLoopSafepoint {

    private static int y = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Print.println(DateUtil.now());
            }
        });
        t1.start();

        Thread t2 = new Thread(CountableLoopSafepoint::loop);
        Thread.sleep(3000);
        t2.start();
        t1.join();
    }

    private static void loop() {
        int x = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            for (int j = 0; j < Integer.MAX_VALUE; j++) {
                x++;
                // 此处赋值决定了循环是否被 JIT 优化为可数循环, 循环中存在对静态变量 y 的写操作, JVM 认为这个循环可能影响其他线程可见的状态
                // 因此 JIT 不会将其优化为纯可数循环, 导致 t2 线程长时间无法进入 Safepoint, 阻塞所有线程
                y = x;
            }
        }
        Print.print("loop end!");
    }
}
