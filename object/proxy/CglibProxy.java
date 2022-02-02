package object.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author zqw
 * @date 2021/12/14
 */
public class CglibProxy implements MethodInterceptor {

    /**
     * The object that needs proxy by Cglib
     */
    private Object targetObject;

    public Object createProxyObject(Object targetObject) {
        this.targetObject = targetObject;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetObject.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object obj;
        if("pay".equals(method.getName())) {
            System.out.println("pay...");
        }
        obj = method.invoke(targetObject, args);
        return obj;
    }
}
