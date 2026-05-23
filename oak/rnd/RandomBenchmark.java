package oak.rnd;

import cn.qingweico.constants.Symbol;
import cn.qingweico.reflect.ReflectUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2026/5/23
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class RandomBenchmark {

    private Random random;
    private SplittableRandom splittable;
    private SecureRandom secure;

    @Setup
    public void setup() throws NoSuchAlgorithmException {
        random = new Random();
        splittable = new SplittableRandom();
        secure = SecureRandom.getInstanceStrong();
    }

    @Benchmark
    public int testRandom() {
        return random.nextInt();
    }

    @Benchmark
    public int testThreadLocalRandom() {
        return ThreadLocalRandom.current().nextInt();
    }

    @Benchmark
    public int testSplittableRandom() {
        return splittable.nextInt();
    }

    @Benchmark
    public int testSecureRandom() {
        return secure.nextInt();
    }

    public static void main(String[] args) throws RunnerException {
        String currentDir = ReflectUtils.getCallerClass().getPackageName().replace(Symbol.DOT, File.separator) + File.separator;
        Options opt = new OptionsBuilder()
                .include(RandomBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(3)
                .measurementIterations(5)
                .resultFormat(ResultFormatType.TEXT)
                .result(currentDir + "rndBenchmarkRet.txt")
                .build();
        new Runner(opt).run();
    }
}
