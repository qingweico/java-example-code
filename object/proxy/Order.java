package object.proxy;

import object.proxy.aop.TimeUsageAspect;
import object.proxy.annotation.Aspect;

import java.util.concurrent.TimeUnit;

/**
 * @author:qiming
 * @date: 2021/10/22
 */
@Aspect(type = TimeUsageAspect.class)
public class Order implements IOrder {
    int state;

    @Override
    public void pay() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.state = 1;
    }

    @Override
    public void show() {
        System.out.println("order status: " + this.state);
    }
}
