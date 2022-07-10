package jvm;

/**
 * VM: {@code -XX:+UseSerialGC}
 * AWS Lambda 中 Java 运行时就是使用的 Serial GC,可以大大降低单个func的启动和运行开销
 * <a href="https://github.com/awsdocs/aws-lambda-developer-guide"/>
 * {@code -XX:+PrintGC}
 * {@code -XX:+PrintGCApplicationStoppedTime}  打印GC时,应用响应时间
 * {@code -XX:+PrintSafepointStatistics} 打印引起SafePoint的操作、线程运行情况
 * {@code -XX:+UseCountedLoopSafepoints}
 *
 * @author zqw
 * @date 2021/11/10
 */
public class SysGc {
    public static void main(String[] args) throws InterruptedException {
        new SysGc();
        System.gc();
        System.runFinalization();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize!");
    }
}
