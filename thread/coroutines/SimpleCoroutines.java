package thread.coroutines;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import util.Print;

/**
 * Quasar 基于 Java instrument 技术
 * 添加代理Agent VM: -javaagent:lib\quasar-core-0.7.6.jar
 * <a href="http://docs.paralleluniverse.co/quasar/">quasar 官方文档</a>
 * @author zqw
 * @date 2022/7/28
 */
class SimpleCoroutines {
    public static void main(String[] args) {
        Fiber<Void> fiber = new Fiber<>() {
            @Override
            protected Void run() throws SuspendExecution, InterruptedException {
                Print.grace(Thread.currentThread().getName(), "run");
                return null;
            }
        };
        fiber.start();
    }

}
