package geek.io.nio;

import util.Constants;

import java.nio.ByteBuffer;

/**
 * @author zqw
 * @date 2022/7/2
 * {@code Direct Buffer} 不在JVM 堆上,不受Xmx之类参数的影响
 * 可以使用 VM 参数 {@code -XX:MaxDirectMemorySize=N} 设置堆外内存
 * {@since JDK 1.8} 之后 可以使用 Native Memory Tracking(NMT) 特性来对 Direct Buffer 进行内存诊断
 * VM 参数 {@code -XX:NativeMemoryTracking=[summary | detail]}
 * 注意: 激活 NMT 通常都会导致 JVM 出现 5% ~ 10% 的性能下降
 */
class DirectBuffer {
    public static void main(String[] args) {
        // -XX:MaxDirectMemorySize=100M -XX:NativeMemoryTracking=summary
        ByteBuffer.allocateDirect(Constants.KB * 1024 * 200);
    }
    // JVM 退出时打印内存使用情况
    // -XX:+UnlockDiagnosticVMOptions -XX:+PrintNMTStatistics
}
