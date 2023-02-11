package tools.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.util.concurrent.TimeUnit;

/**
 * JMH 官网地址 <a href="http://openjdk.java.net/projects/code-tools/jmh/"></a>
 * 官网案例 <a href="http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/"></a>
 *
 * @author zqw
 * @date 2022/8/18
 * {@link BenchmarkMode} {@link Mode} 默认 Throughput : 表示吞吐量, AverageTime : 表示每次执行时间
 * SampleTime表示采样时间, SingleShotTime表示只运行一次, 用于测试冷启动消耗时间, All表示统计前面的所有指标
 * {@link Warmup} 配置预热次数 默认是每次运行1秒,运行10次
 * [iterations = 5, time = 1] 表示运行5次 每次运行时间为1s
 * {@link Measurement} 配置执行次数
 * [iterations = 5, time = 1] 表示每次运行1s, 运行5次, 采用默认1秒即可
 * {@link Threads} 配置同时起多少个线程执行 默认值为 {@link Runtime#getRuntime#availableProcessors()}
 * {@link Fork} 代表启动多个单独的进程分别测试每个方法
 * {@link OutputTimeUnit} 统计结果的时间单元
 */
public class SimpleJmhTest {

    @BenchmarkMode(Mode.AverageTime)
    @Benchmark
    @Fork(1)
    @Threads(2)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Measurement(iterations = 5, time = 1)
    @Warmup(iterations = 5, time = 1)
    public void hello() {

    }

    public static void main(String[] args) throws RunnerException {
        JmhUtil.startJmh(SimpleJmhTest.class.getName(), 1);
    }

}
