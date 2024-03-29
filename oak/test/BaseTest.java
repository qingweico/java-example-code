package oak.test;

import cn.hutool.Hutool;
import cn.hutool.core.collection.EnumerationIter;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import object.entity.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import util.ObjectFactory;
import util.Print;
import util.constants.Constants;

import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 更多测试请参考微基准测试工具jmh
 *
 * @author zqw
 * @date 2022/2/3
 */
@Slf4j
public final class BaseTest {
    @Test
    public void floatNumber() {
        float positive = 0.0F;
        float negative = -0.0F;
        // Float.NaN
        // [0x7F800001, 0x7FFFFFFF]
        // [0xFF800001, 0xFFFFFFFF]
        // Standard NaN: 0x7FC00000
        // positive == negative is true
        System.out.println(positive * negative);
        System.out.println(0x7FC00000);
        // -0.0F
        System.out.println(Float.intBitsToFloat(0x80000000));
        float positiveInfinity = 0x7F800000;
        float negativeInfinity = 0xFF800000;
        System.out.println(positiveInfinity);
        System.out.println(negativeInfinity);
        System.out.println(0x7F800001);
        System.out.println(0x7FFFFFFF);
        System.out.println(Float.NEGATIVE_INFINITY);
        System.out.println(Float.POSITIVE_INFINITY);
    }

    @Test
    public void bitComparable() {
        Integer[] a = new Integer[]{1, 8, 2, 0};
        Arrays.sort(a, (o1, o2) -> Integer.bitCount(o1) > Integer.bitCount(o2) ? o1 - o2 : o2 - o1);
        System.out.println(Arrays.toString(a));
    }

    @Test
    public void outBinary() {
        byte aByte = (byte) 0b00100001;
        System.out.println(aByte);
    }

    @Test
    public void bitOperation() {
        // -1 的原码为1000 0000 0000 0000 0000 0000 0000 0001
        // 补码为1111 1111 1111 1111 1111 1111 1111 1111
        // 左移29为 1110 0000 0000 0000 0000 0000 0000 0000
        // 结果为-2的31次方 + 2的30次方 + 2的29次方: -536870912
        System.out.println(-1 << 29);
        System.out.println(1 << 29);
        System.out.println(2 << 29);
    }

    @Test
    public void flag() {
        flag:
        for (int i = 0; i < Constants.TWO; i++) {
            for (int j = 0; j < Constants.TEN; j++) {
                if (j == 3) {
                    continue flag;
                }
                Print.grace(i, j);
            }
        }
    }

    @Test
    public void isAssignableFrom() {
        // 原始类型 该Class对象和参数类型一致时才返回true
        // 对象类型 父接口或者父类都会返回true
        // true
        System.out.println(int.class.isAssignableFrom(int.class));
        // true
        // 判断参数类型是否是Class类型的相同类型或者子类型,子接口
        System.out.println(Comparable.class.isAssignableFrom(Integer.class));
        System.out.println(System.getProperty("java.class.path"));
    }

    @Test
    public void huTool() {
        Hutool.printAllUtils();
    }

    @Test
    public void classScan() {
        Set<Class<?>> classes = ClassUtil.scanPackage("effective",
                (clazz) -> (!clazz.isInterface()));
        classes.forEach(System.out::println);
    }

    @Test
    public void urlIter() {
        EnumerationIter<URL> resourceIter = ResourceUtil.getResourceIter("effective");
        while (resourceIter.hasNext()) {
            URL url = resourceIter.next();
            System.out.println(url.toString());
        }
        System.out.println(this.getClass().getClassLoader().getResource("effective"));
    }

    @Test
    public void systemClassLoader() {
        System.out.println(ClassLoader.getSystemClassLoader());
    }

    @Test
    public void timeUnit() {
        // 600s 将给定参数 转换为 unit 相对应的单位的秒数
        System.out.println(TimeUnit.MINUTES.toSeconds(10));
    }

    @Test
    public void toStr() {
        User user = ObjectFactory.create(User.class, true);
        String res = ToStringBuilder.reflectionToString(user);
        System.out.println(res);
    }
}
