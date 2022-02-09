package object.proxy;

import object.proxy.aop.ObjectFactory;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zqw
 * @date 2021/10/22
 */
public class ProxyTest {
    @Test
    public void proxy() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        IOrder order = Aspect.getProxy(Order.class, "object.proxy.TimeUsageAspect");
        order.pay();
        order.show();
    }

    @Test
    public void aop() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        IOrder order = ObjectFactory.newInstance(Order.class);
        order.pay();
        order.show();
    }
    @Test
    public void jdkProxy() {
        JdkProxy jdkProxy = new JdkProxy();
        IOrder order = (IOrder) jdkProxy.newProxy(new Order());
        order.pay();
        order.show();
    }
    @Test
    public void cglibProxy() {
        CglibProxy cglibProxy = new CglibProxy();
        IOrder order = (IOrder) cglibProxy.createProxyObject(new Order());
        order.pay();
        order.show();
    }

}
