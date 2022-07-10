package jvm;

/**
 * {@since JDK9} 引入了一些实验性的参数 以方便 Docker 和 Java 沟通
 * 针对内存的限制: VM: {@code -XX:+UnlockExperimentalVMOptions(解锁实验参数) -XX:+UseCGroupMemoryLimitForHeap}
 * 只支持 Linux 环境 且顺序严格
 * {@since JDK10+} {@code -XX:+UseCGroupMemoryLimitForHeap} 弃用
 * VM: {@code -XX:ActiveProcessorCount=N} 指定 CPU 核心的数目
 * VM: {@code -XX:-UseContainerSupport} 关闭 Java 容器的支持特性
 *
 * @author zqw
 * @date 2022/7/9
 */
class Container {
    public static void main(String[] args) {
        // nothing to do
        // -XX:ActiveProcessorCount=4
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
