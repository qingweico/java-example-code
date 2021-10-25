package object.proxy;

import object.proxy.aop.ObjectFactory;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @author:qiming
 * @date: 2021/10/22
 */
public class ProxyEx {
    @Test
    public void proxy() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        IOrder order = Aspect.geProxy(Order.class, "object.proxy.TimeUsageAspect");
        order.pay();
        order.show();
    }

    @Test
    public void aop() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        IOrder order = ObjectFactory.newInstance(Order.class);
        order.pay();
        order.show();
    }

}
