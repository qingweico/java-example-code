package thread.atmoic;

/**
 * --------------- 内存屏障 ---------------
 * JMM 中共有四种内存屏障
 * LoadLoad
 *     |
 *     |
 *     V
 *     Load1;LoadLoad;Load2
 *     在Load2及后续读取操作要读取的数据被访问前,保证Load1要读取的数据被读取完毕
 * StoreStore
 *      |
 *      |
 *      V
 *      Store1;StoreStore;Store2
 *      在Store2及后续写入操作执行前,保证Store1的写入操作对其它处理器可见
 * LoadStore
 *      |
 *      |
 *      V
 *      Load1;LoadStore;Store2
 *      在Store2及后续写入操作被刷出前,保证Load1要读取的数据被读取完毕
 * StoreLoad
 *      |
 *      |
 *      V
 *      Store1;StoreLoad;Load2
 *      在Load2及后续所有读取操作执行前,保证Store1的写入对所有处理器可见
 * --------------- volatile ---------------
 *    ##StoreStore##
 *    ============           ============
 *    |volatile写|           |volatile读 |
 *    ============           ============
 *    ##StoreLoad##          ##LoadLoad##
 *                           ##LoadStore##
 * 内存屏障会限制即时编译器的重排序操作 对于volatile所修饰的变量 所插入的内存屏障不允许
 * volatile字段写操作之前的内存访问被重排序至其之后;也不允许volatile字段读操作之后的
 * 内存访问被重排序至其之前
 * 即时编译器将根据具体的底层体系架构 将这些内存屏障替换成具体的CPU指令 不同的CPU架构
 * 会有不同的指令体现; X86_64中,读读,读写以及写写内存屏障是空操作(no-op),只有写读内存屏障
 * 会被替换成具体指令(HotSpot 中选取的具体指令是 lock add,而非mfence)
 *
 *
 * --------------- 关于 final 字段发布问题 ---------------
 * 即时编译器会在 final 字段的写操作后插入一个写写屏障(x86_64是空操作),以防止某些优化将新建
 * 的对象的发布(即将实例对象写入到一个共享引用中)重排序至 final 字段的写操作之前
 * @author zqw
 * @date 2022/7/16
 */
class MemoryBarrier {
    public static void main(String[] args) {

    }
}
