package geek.exception;


import annotation.Pass;
import org.openjdk.jmh.annotations.*;
import tools.jmh.JmhUtil;

import java.util.concurrent.TimeUnit;

/**
 * /////////////// 异常代码和无异常代码吞吐量比较 ///////////////
 * Benchmark                        Mode  Cnt           Score          Error  Units
 * OutOfBoundsBench.noException    thrpt    5  2643965917.466 ± 11077874.623  ops/s
 * OutOfBoundsBench.withException  thrpt    5      943612.296 ±   125521.635  ops/s
 * @author zqw
 * @date 2023/1/18
 */
@BenchmarkMode(Mode.Throughput)
@Fork(3)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@State(Scope.Thread)
@SuppressWarnings("all")
@Pass
public class OutOfBoundsBench {


    private final String s = "2023/1/18";


    @Benchmark
    public void withException() {
        try {
            s.substring(10);
        } catch (RuntimeException re) {
            // ignore the exception.
        }
    }

    @Benchmark
    public void noException() {
        try {
            s.substring(9);
        } catch (RuntimeException re) {
            // ignore the exception.
        }
    }

    public static void main(String[] args) {
        JmhUtil.startJmh(OutOfBoundsBench.class.getName(), 1);
    }
}
