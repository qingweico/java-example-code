package object.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zqw
 * @date 2025/9/12
 */
@Slf4j
public class RunnableInvocationHandler implements InvocationHandler {
    private final Runnable runnable;

    public RunnableInvocationHandler(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        long start = System.currentTimeMillis();
        Object invoke = method.invoke(runnable, args);
        long end = System.currentTimeMillis();
        log.info("Runnable耗时 {} 毫秒", end - start);
        return invoke;
    }
}
