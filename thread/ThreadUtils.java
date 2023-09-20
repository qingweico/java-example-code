package thread;

import util.DateUtil;
import util.Print;

import java.util.StringJoiner;

/**
 * 有关线程的一些工具
 *
 * @author zqw
 * @date 2023/4/22
 */
public final class ThreadUtils {
    public static String getThreadName() {
        return Thread.currentThread().getName();
    }

    public static long getThreadId() {
        return Thread.currentThread().getId();
    }

    public static void gracePrint(String attach) {
        StringJoiner result = new StringJoiner("\t|\t");
        result
                .add(String.valueOf(DateUtil.format()))
                .add(String.valueOf(getThreadName()))
                .add(String.valueOf(getThreadId()))
                .add(attach);

        Print.println(result);
    }

    public static Thread getCurrentThread() {
        return Thread.currentThread();
    }

}
