package object.proxy.aop;

import object.proxy.annotation.Aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;

/**
 * @author:qiming
 * @date: 2021/10/22
 */
public class ObjectFactory {
    public static <T> T newInstance(Class<T> cls) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var annotations = cls.getAnnotations();
        LinkedList<IAspect> aspectList = new LinkedList<>();
        for (var annotation : annotations) {
            if (annotation instanceof Aspect) {
                var type = ((Aspect) annotation).type();
                var aspect = (IAspect) (type.getConstructor().newInstance());
                aspectList.push(aspect);
            }
        }
        var inst = cls.getConstructor().newInstance();

        return (T) Proxy.newProxyInstance(
                cls.getClassLoader(),
                cls.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        aspectList.forEach(aspect -> aspect.before());
                        var result = method.invoke(inst);
                        aspectList.forEach(aspect -> aspect.after());
                        return result;
                    }
                });
    }
}
