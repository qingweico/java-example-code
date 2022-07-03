package jvm;

/**
 * @author zqw
 * @date 2021/11/10
 * {@code jstat -gc pid interval} 查看每次 GC 之后 具体每一个分区的内存使用情况
 * {@code jcmd pid VM.flags} 查看垃圾收集器的具体参数设置
 */
@SuppressWarnings("unused")
public class LocalVarGc {

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
