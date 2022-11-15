package tools.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * JMH 官网地址 <a href="http://openjdk.java.net/projects/code-tools/jmh/"></a>
 * 官网案例 <a href="http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/"></a>
 *
 * @author zqw
 * @date 2022/8/18
 */
public class Jmh01Hello {

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
        Options opt = new OptionsBuilder()
                .include(Jmh01Hello.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
