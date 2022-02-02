package object.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zqw
 * @date 2021/12/14
 */
public class JdkProxy implements InvocationHandler {

    // The object that needs proxy
    private Object targetObject;

    public Object newProxy(Object targetObject) {
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj;
        obj = method.invoke(targetObject, args);
        return obj;
    }
}
