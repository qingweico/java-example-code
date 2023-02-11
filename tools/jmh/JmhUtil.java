package tools.jmh;

import org.apache.http.util.Asserts;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import javax.annotation.Nonnull;

/**
 * @author zqw
 * @date 2023/1/18
 */
public class JmhUtil {
    public static void startJmh(@Nonnull String className, int forks) {
        Asserts.notNull(className, "class name is null!");
        Asserts.check(forks > 0, "number of forks less than 1!");
        Options opt = new OptionsBuilder().include(className).forks(forks).build();
        try {
            new Runner(opt).run();
        } catch (RunnerException e) {
            throw new RuntimeException(e);
        }
    }
}
