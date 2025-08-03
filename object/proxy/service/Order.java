package object.proxy.service;

import object.proxy.aspect.TimeUsageAspect;
import object.proxy.annotation.Aspect;
import cn.qingweico.io.Print;

import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2021/10/22
 */
@Aspect(type = TimeUsageAspect.class)
public class Order implements IOrder {
    int state;

    @Override
    public void pay() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        this.state = 1;
    }

    @Override
    public void show() {
        System.out.println("pay success!");
        System.out.println("order status: " + this.state);
    }
}
