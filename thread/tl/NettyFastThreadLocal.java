package thread.tl;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

/**
 * FastThreadLocalThread 与 ThreadLocal 的性能对比测试
 * {@link FastThreadLocalThread } 与 {@link FastThreadLocal } 搭配才可以发挥性能优势
 * 如果在普通Thread中使用,则会退化为 JDK {@link ThreadLocal } 的性能
 *
 * @author zqw
 * @date 2024/5/29
 * @see FastThreadLocal
 * @see FastThreadLocalThread
 */
class NettyFastThreadLocal {


    public static void main(String[] args) {
    }
}
