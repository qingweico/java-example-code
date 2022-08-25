package jvm;

/**
 * @author zqw
 * @date 2021/11/10
 * {@code jstat -gc pid interval} 查看每次 GC 之后 具体每一个分区的内存使用情况
 * {@code jcmd pid VM.flags} 查看垃圾收集器的具体参数设置
 * VM: {@code -XX:ParallelGCThreads} 配置 GC 并行线程数目
 * JDK9 的默认 GC 是 G1;虽然它在较大堆场景表现良好,但是本身就会比传统的 Parallel GC 或者
 * Serial GC 之类复杂太多;所以要么降低其并行线程数目,要么直接切换 GC 类型
 */
@SuppressWarnings("unused")
class LocalVarGc {

    public void localVar1() {
        byte[] buff = new byte[10 * 1024 * 1024];
        System.gc();
    }

    public void localVar2() {
        byte[] buff = new byte[10 * 1024 * 1024];
        buff = null;
        System.gc();
    }

    public void localVar3() {
        {
            byte[] buff = new byte[10 * 1024 * 1024];
        }
        System.gc();
    }

    public void localVar4() {
        {
            byte[] buff = new byte[10 * 1024 * 1024];
        }
        int v = 10;
        System.gc();
    }

    public void localVar5() {
        localVar1();
        System.gc();
    }

    // -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.

    public static void main(String[] args) {
        LocalVarGc localVarGc = new LocalVarGc();
        localVarGc.localVar1();
    }
}
