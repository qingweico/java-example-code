package object.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.aop.support.AopUtils;
import cn.qingweico.io.Print;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * VM Options: --add-opens java.base/java.lang=ALL-UNNAMED
 *
 * @author zqw
 * @date 2024/6/19
 */
class PrintWriterEnhancer {

    public static void main(String[] args) {
        PrintWriter writer = new PrintWriter(System.out);

        Enhancer enhancer = getEnhancer(writer);
        PrintWriter printWriterProxy = (PrintWriter) enhancer.create(new Class[]{Writer.class}, new Object[]{writer});
        printWriterProxy.println(printWriterProxy.getClass().getName());
        // instanceof SpringProxy
        printWriterProxy.println(AopUtils.isCglibProxy(printWriterProxy));
        printWriterProxy.println(Enhancer.isEnhanced(printWriterProxy.getClass()));
    }

    private static @NotNull Enhancer getEnhancer(PrintWriter writer) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PrintWriter.class);
        enhancer.setCallback((MethodInterceptor) (o, method, arg, methodProxy) -> {
            String interceptMethod = "println";
            try {
                if (interceptMethod.equals(method.getName())) {
                    Print.grace("ByCGLIBPrintln", arg[0]);
                }
                Object result = method.invoke(writer, arg);
                // Flush the writer to ensure output is visible
                writer.flush();
                return result;
            } catch (Exception e) {
                writer.println(e.getMessage());
                writer.flush();
            }
            return null;
        });
        return enhancer;
    }
}
