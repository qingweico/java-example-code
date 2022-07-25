package object.proxy.aop;

import object.proxy.annotation.Aspect;
import object.proxy.aspect.IAspect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.LinkedList;

/**
 * @author zqw
 * @date 2021/10/22
 */
public class ObjectFactory {
    @SuppressWarnings("unchecked")
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
                (proxy, method, args) -> {
                    aspectList.forEach(IAspect::before);
                    var result = method.invoke(inst);
                    aspectList.forEach(IAspect::after);
                    return result;
                });
    }
}
