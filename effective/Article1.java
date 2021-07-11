package effective;

import java.time.Instant;
import java.util.Date;

/**
 * 用静态工厂方法代替构造器
 *
 * @author:qiming
 * @date: 2020/10/25
 * <p>
 * 优点:
 *      1> 有适当的名称的静态工厂会更容易使用,易于阅读
 *         当一个类中需要多个带有相同签名的构造器的,就用静态工厂构造去代替构造器,
 *         并且仔细地选择名称以便突出静态工厂方法之间的区别
 *      2> 不必在每次调用对象的时候创建一个新对象
 *      3> 可以返回原返回类型的任意子类新的对象
 *      4> 返回的对象的类可以随着每次调用而发生变化,取决于静态工厂方法的参数值
 *      5> 方法返回的对象所属的类,在编写该静态工厂方法时可以不存在
 */


// java8以后接口可以包含静态方法, 但是仍要求所有的静态成员必须是公有的
// java9接口中可以有私有的静态方法, 但是静态域和静态成员仍然需要公有的
public class Article1 {
    public static boolean valueOf(boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }

    public static void main(String[] args) {

        Date d = Date.from(Instant.now());
        System.out.println(d);
    }
}
