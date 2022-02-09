package object.proxy;

import util.monad.Try;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author zqw
 * @date 2021/9/28
 */
public interface Aspect {

    /**
     * before method
     */
    void before();

    /**
     * after method
     */
    void after();

    @SuppressWarnings("unchecked")
    static <T> T getProxy(Class<T> cls, String... aspects) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var aspectInstances = Arrays.stream(aspects).map(name -> Try.ofFailable(() -> {
            var cl = Class.forName(name);
            return (Aspect) cl.getDeclaredConstructor().newInstance();
        })).filter(Try::isSuccess).collect(Collectors.toList());

        var inst = cls.getConstructor().newInstance();

        return (T) Proxy.newProxyInstance(
                cls.getClassLoader(),
                cls.getInterfaces(),
                (proxy, method, args) -> {
                    for (var aspect : aspectInstances) {
                        aspect.get().before();
                    }
                    var result = method.invoke(inst);
                    for (var aspect : aspectInstances) {
                        aspect.get().after();
                    }
                    return result;
                });
    }
}
