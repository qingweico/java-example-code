package misc;

import org.junit.Test;

/**
 * @see Runtime#addShutdownHook(Thread)
 * 注册 JVM 关闭的钩子函数在一下情境中会被调用
 * 1 程序正常退出 待验证
 * 2 使用System.exit()
 * 3 终端使用Ctrl+C触发的中断
 * 4 系统关闭
 * 5 OutOfMemory
 * 6 使用kill pid命令 (kill -9 pid 不会被调用)
 * @author zqw
 * @date 2023/8/10
 */
public class ShutdownHookTest {

    static {
        Thread shutdownHookThread = new Thread(() -> {
            System.out.println("Shutdown Hook");
        });
        Runtime.getRuntime().addShutdownHook(shutdownHookThread);
    }
    @Test
    public void normal() {
        System.out.println("Normal Exit");
    }
    @Test
    public void systemExit() {
        System.out.println("System Exit");
        System.exit(0);
    }
}
