package thread.tl;


import cn.qingweico.concurrent.pool.ThreadPoolBuilder;

import java.util.concurrent.ExecutorService;

/**
 * {@link ThreadLocal} 的基本使用
 * 每一个线程绑定一个 {@code ThreadLocal#ThreadLocalMap}
 * key 为 ThreadLocal 实例变量(弱引用), 值为 value
 * thread > ThreadLocalMap > |----Entry---|
 *                           |  tl  |  v  |
 * 子线程获取父线程的值 {@link InheritableThreadLocal}
 * @author zqw
 * @date 2023/1/15
 */
class BaseTl {
    static ThreadLocal<String> tl = ThreadLocal.withInitial(() -> {
        System.out.println("initial tl");
        return Thread.currentThread().getName();
    });
    public static void main(String[] args) {
        ExecutorService single = ThreadPoolBuilder.single(false);
        single.execute(() -> System.out.println(tl.get()));
        tl.set("main");
        // 移除之后会重新调用initial方法
        tl.remove();
        System.out.println(tl.get());
    }
}
