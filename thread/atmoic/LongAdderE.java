package thread.atmoic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * {@since JDK1.8} Java 提供了一个新的原子类 {@link LongAdder}
 * LongAdder在高并发场景下会比 {@link AtomicInteger} 和 {@link AtomicLong} 性能更好
 * 代价就是消耗更多的内存空间
 * -------------------------------------------------------
 * {@code LongAdder} 的原理就是降低操作共享变量的并发数,也就是将对单一变量的操作压力分散到多个变量上
 * 其内部由一个 base 变量和一个 cell[] 数组组成,当只有一个写线程,没有竞争的情况下,LongAdder会直接使用 base
 * 变量作为原子操作变量,通过 CAS 修改变量;当有多个写线程竞争的情况下,除了占用 base 变量的一个写线程之外,其它
 * 各个线程会将修改的变量写入到自己的cell[]数组中
 * tips: LongAdder 在操作后的变量只是一个近似准确的数值,但是最终返回的是一个准确的数值,所以在一些对
 * 实时性要求比较高的场景下,LongAdder 并不能取代 AtomicInteger 或 AtomicLong
 * @author zqw
 * @date 2022/7/2
 */
class LongAdderE {
    public static void main(String[] args) {
        System.out.println("LongAdder");
    }
}
