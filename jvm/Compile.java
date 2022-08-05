package jvm;

/**
 * Java7 引入了分层编译 {@code -XX:+TieredCompilation} 综合了C1的启动性能优势和C2的峰值性能优势
 * VM: {@code -XX:CICompilerCount} 配置 JIT 并行线程数目
 * JIT 编编译器认开启了 {@code TieredCompilation}的,将其关闭,那么JIT也会变的简单,
 * 相应本地线程也会减少,降低内存的使用
 * ----------------------------- 反编译 -----------------------------
 * javap 会打印所有的非私有的字段和方法
 * [-p] 选项 将打印私有的字段和方法
 * [-v] 尽可能地打印所以的信息
 * [-c] 只查阅方法对应的字节码
 *
 * @author zqw
 * @date 2022/7/9
 */
class Compile {
    public static void main(String[] args) {
        // nothing to do
    }
}
