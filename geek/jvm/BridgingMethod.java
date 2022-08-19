package geek.jvm;

import object.entity.Customer;
import jvm.OverrideAndOverload;

/**
 * JVM 中的桥接方法
 *
 * @author zqw
 * @date 2022/8/01
 * {@link OverrideAndOverload}
 */
public class BridgingMethod {
    public static void main(String[] args) {
    }
}

class Merchant<T extends Customer> {
    /*父类的方法描述符:[Customer double]*/

    public double actionPrice(T customer) {
        return 0.0d;
    }
}

class VipOnlyMerchant extends Merchant<Vip> {
    /*子类的方法描述符:[Vip double]*/

    /*由于在Java虚拟中重写的定义为方法描述符和方法名都必须一致,上述代码并不满足该要求;
    所以 Java 编译器在字节码中添加了一个桥接方法: 在字节码层面重写了父类中的方法,并调用了子类的方法*/


    // 桥接方法的方法描述符: ACC_SYNTHETIC 表示该方法对于 Java 源码是不可见的
    /*会生成桥接方法的两种情况: 1:泛型的重写2:子类参数类型与父类相同但是返回类型是父类方法返回类型的子类*/


    @Override
    public double actionPrice(Vip customer) {
        return 0.0d;
    }

    public static void main(String[] args) {

    }
}

class Vip extends Customer {

}