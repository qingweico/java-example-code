package object.proxy.aspect;

import cn.qingweico.utils.monad.Try;

import java.lang.reflect.Proxy;
import java.util.Arrays;

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

    /**
     * Generate a proxy object
     *
     * @param cls     被代理对象类型
     * @param aspects 代理对象全限定类名
     * @param <T>     被代理对象Class类型
     * @return T 代理对象
     * @throws Exception Exception
     */
    @SuppressWarnings("unchecked")
    static <T> T getProxy(Class<T> cls, String... aspects) throws Exception {
        var aspectInstances = Arrays.stream(aspects).map(name -> Try.ofFailable(() -> {
            var cl = Class.forName(name);
            return (Aspect) cl.getDeclaredConstructor().newInstance();
        })).filter(Try::isSuccess).toList();

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
